package com.wizepass.wpadmingui.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.wizepass.wpadmingui.controller.DataController;
import com.wizepass.wpadmingui.userdata.TreeTableFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

@Theme("WpAdminGuiTheme")
public class WpAdminGuiUi extends UI {
	//test
    final VerticalLayout layoutMain = new VerticalLayout();
    final HorizontalLayout buttonLayout = new HorizontalLayout();
    final VerticalLayout tabUserDbApi = new VerticalLayout();
    final TabSheet tabsheet = new TabSheet();
    final Button buttonCreateRegToken = new Button("Create Issue Registation Tokens");
    final ComboBox customerComboBox = new ComboBox();
    final ComboBox dbComboBox = new ComboBox();
    private Window window = new Window();
    final DataController controller = new DataController();
    final JSONObject jsonObj = controller.getAllUserLabb3();
    final JSONArray customers = controller.getCustomers();
    final JSONArray dbMgab = controller.getMgabDb();
    final RegistationTokenTab registationToken = new RegistationTokenTab();
    final IssueRegistationTokenWindow issueRegisToken = new IssueRegistationTokenWindow();
    final TokenGui tokenGui = new TokenGui();
    private int dnsCount = 0;
    private int personCount = 0;
    private String customerSelected = null;
    private Map<String, String> mapSelected = new HashMap<String, String>();
    private List<String> listOfDns = new ArrayList<>();
    private String dbSelected = null;
    private String valueAuthHeader;
    final String token = "Bearer 123";
    private final Logger logger = Logger.getLogger(WpAdminGuiUi.class.getName());

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        valueAuthHeader = getResponseHeaders(vaadinRequest);
        checkToken(valueAuthHeader);
        registationToken.createRegistationTokenTab(tabsheet);
        layoutMain.addComponents(tabsheet);
        layoutMain.setMargin(true);
        layoutMain.setSpacing(true);
        setContent(layoutMain);
    }

    // no header or authorization is not correct.
    private void checkToken(final String valueAuthHeader) {
        if ( token.equals(valueAuthHeader)) {
            createUserDbApiTab();
        } else {
            final TokenGui tokenGui = new TokenGui();
            window = tokenGui.createTokenWindow(token);
            addWindow(window);
            createUserDbApiTab();
        }
    }

    // check authorization header.
    private String getResponseHeaders(final VaadinRequest vaadinRequest) {
        final Enumeration<String> auth = vaadinRequest.getHeaders("Authorization");
        while (auth.hasMoreElements()) {
            valueAuthHeader = auth.nextElement();
            logger.log(Level.INFO, "HEADER:" + valueAuthHeader);
        }
        return valueAuthHeader;
    }

    // create user db api.
    private void createUserDbApiTab() {
        buttonCreateRegToken.setEnabled(false);
        final TreeTableFactory treeTableFactory = createTreeTable(jsonObj);
        buttonCreateRegToken.setEnabled(true);
        customerComboBox.setNullSelectionItemId(false);
        addCustomerInToComboBox();
        mapSelected = getValueSelected();
        buttonCreateRegToken.addClickListener(e -> {
            final Set<Integer> keySet = treeTableFactory.getPersons().keySet();
            System.err.println("Tree:" );
            for (int i : keySet) {
                final Item item = treeTableFactory.getTreeTable().getItem(i);
                String givenName = (String) item.getItemProperty("Given Name").getValue();
                String dns = (String) item.getItemProperty("DNS").getValue();
                CheckBox box = (CheckBox) item.getItemProperty("LDAP Tree").getValue();
                if (box.getValue()) {
                    System.err.println("Issue Wizepass for person: " + givenName + " " + dns );
                    if (dns != null) {
                        dnsCount++;
                        listOfDns.add(dns);
                    }
                    if ( givenName != null ) {
                        personCount++;
                    }
                    System.err.println("Person count : " + personCount);
                    System.err.println("DNS count : " + dnsCount);
                }
            }
            final Window regTokens = issueRegisToken.createRegistationTokenWindow(dnsCount,mapSelected,dbSelected,listOfDns);
            addWindow(regTokens);
            System.out.println("list of DNS :" + listOfDns);
        });
        buttonLayout.addComponents(customerComboBox,dbComboBox);
        buttonLayout.setSpacing(true);
        tabUserDbApi.addComponents(buttonLayout,treeTableFactory.getTreeTable(), buttonCreateRegToken);
        tabUserDbApi.setSpacing(true);
        tabsheet.addTab(tabUserDbApi, "UserDbApi");
    }

    /**
     * @Value of customerselected from CustomerCombobox.
     */
    public Map<String, String> getValueSelected() {
        customerComboBox.addValueChangeListener(event -> {
            customerSelected = (String) customerComboBox.getValue();
            dbComboBox.removeAllItems();
            dbComboBox.isImmediate();
            System.err.println(customerSelected);
            getDb();
            dbSelected = (String) dbComboBox.getValue();
            mapSelected.put("CustomerSelected", customerSelected);
        });
        dbComboBox.addValueChangeListener(event -> {
            dbSelected = (String) dbComboBox.getValue();
            mapSelected.put("DbSelected", dbSelected);
            System.err.println("Db selected " + dbSelected);
        });
        return mapSelected;
    }

    private void getDb() {
        if ( customerSelected.equals("granskolan")) {
            logger.log(Level.INFO, "Customer Selected: " + customerSelected.toString());
            final JSONArray dbSkolan = controller.getGranskolanDb();
            if (dbSkolan != null) {
                for (int i = 0; i < dbSkolan.size(); i++) {
                    dbComboBox.addItem(dbSkolan.get(i));
                    dbComboBox.select(dbSkolan.get(0));
                    dbComboBox.setNullSelectionAllowed(false);
                }
                logger.log(Level.INFO, "DB granskolan: " + dbSkolan);
            }
        } else if ( customerSelected.equals("mgab")) {
            logger.log(Level.INFO, "Customer Selected: " + customerSelected.toString());
            final JSONArray dbMgab = controller.getMgabDb();
            if (dbMgab != null) {
                for (int i = 0; i < dbMgab.size(); i++) {
                    dbComboBox.select(dbMgab.get(0));
                    dbComboBox.addItem(dbMgab.get(i));
                    dbComboBox.setNullSelectionAllowed(false);
                }
                logger.log(Level.INFO, "DB mgab : " + dbMgab);
            }
        }
    }

    /**
     * add customers list in Combo Box.
     **/
    private void addCustomerInToComboBox() {
        if (customers != null) {
            for (int i = 0; i < customers.size(); i++) {
                customerComboBox.addItem(customers.get(i));
                customerComboBox.select(customers.get(0));
            }
        } else {
            customerComboBox.addItem(null);
        }
    }

    private TreeTableFactory createTreeTable(final JSONObject jsonObj) {
        TreeTableFactory treeTableFactory = null;
        // Populate tree from test data
        try {
            treeTableFactory = new TreeTableFactory();
            treeTableFactory.createTreeTable(jsonObj);
            treeTableFactory.getTreeTable().setWidth("100%");
            treeTableFactory.getTreeTable().setColumnExpandRatio((Object) "LDAP Tree", 1.0f);
            treeTableFactory.getTreeTable().setColumnExpandRatio( "DNS", 1.0f);
        } catch (Exception e) {
            logger.log(Level.WARNING, " Http response error , please check url again ", e.getMessage());
        }
        return treeTableFactory;
    }

    @WebServlet(urlPatterns = "/*", name = "WpAdminGuiUiServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WpAdminGuiUi.class, productionMode = false)
    public static class WpAdminGuiUiServlet extends VaadinServlet {
    }
}