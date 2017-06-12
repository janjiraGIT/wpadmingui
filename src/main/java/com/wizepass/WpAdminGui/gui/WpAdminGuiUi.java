package com.wizepass.WpAdminGui.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonException;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;

import org.elasticsearch.common.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonParser;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.wizepass.WpAdminGui.controller.DataController;
import com.wizepass.WpAdminGui.gui.IssueRegistationTokenWindow;
import com.wizepass.WpAdminGui.gui.RegistationTokenTab;
import com.wizepass.WpAdminGui.gui.TokenGui;
import com.wizepass.WpAdminGui.gui.WpAdminGuiUi;
import com.wizepass.WpAdminGui.userdata.TreeTableFactory;
import com.wizepass.WpAdminGui.util.Constants;
import com.wizepass.WpAdminGui.util.JsonUtil;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class WpAdminGuiUi extends UI {
	//test
    final VerticalLayout layoutMain = new VerticalLayout();
    final HorizontalLayout buttonLayout = new HorizontalLayout();
    final HorizontalLayout searchLayout = new HorizontalLayout();
    final VerticalLayout tabUserDbApi = new VerticalLayout();
    final TabSheet tabsheet = new TabSheet();
    final Button buttonCreateRegToken = new Button("Create Issue Registration Tokens");
    final ComboBox customerComboBox = new ComboBox();
    final ComboBox dbComboBox = new ComboBox();
    final TextField searchTextField = new TextField();
    final Button searchButtonOk = new Button("OK");
    private Window window = new Window();
    final DataController controller = new DataController();
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
    private final Logger logger = Logger.getLogger(WpAdminGuiUi.class.getName());
    private String strSearch;
    private TreeTableFactory treeTableFactory;
    JSONObject userObj = null;
    com.vaadin.ui.Table table = null;


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
        if ( Constants.TOKEN.equals(valueAuthHeader)) {
            createUserDbApiTab();
        } else {
            final TokenGui tokenGui = new TokenGui();
            window = tokenGui.createTokenWindow(Constants.TOKEN);
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
        searchButtonOk.setVisible(true);
        customerComboBox.setNullSelectionItemId(false);
        addCustomerInToComboBox();
        final JSONObject db = controller.getAllUserMgad();
        treeTableFactory = createTreeTable(db);
        final Map<String, String> customerDbSelected = doSelected();
        buttonCreateRegToken.setEnabled(false);    
        buttonCreateRegToken.setEnabled(true);
        buttonCreateRegToken.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        searchUsers();
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
            final Window regTokens = issueRegisToken.createRegistationTokenWindow(dnsCount,customerDbSelected,dbSelected,listOfDns);
            addWindow(regTokens);
            System.out.println("list of DNS :" + listOfDns);
        });
        buttonLayout.addComponents(customerComboBox,dbComboBox);
        buttonLayout.setSpacing(true);
        searchLayout.addComponents(searchTextField,searchButtonOk);
        searchLayout.setSpacing(true);
        tabUserDbApi.addComponents(buttonLayout,treeTableFactory.getTreeTable(), buttonCreateRegToken, searchLayout);
        tabUserDbApi.setSpacing(true);
        tabsheet.addTab(tabUserDbApi, "UserDbApi");
    }

    /**
     * @Value of customerselected from CustomerCombobox.
     */
    public Map<String, String> doSelected() {
        customerComboBox.addValueChangeListener(event -> {
            customerSelected = (String) customerComboBox.getValue();
            dbComboBox.removeAllItems();
            dbComboBox.isImmediate();
            System.err.println("Customer Selected : " + customerSelected);
            getDb();
            mapSelected.put("CustomerSelected", customerSelected);
            logger.log(Level.INFO, "Customer selected: " + customerSelected);
        });

        dbComboBox.addValueChangeListener(event -> {
        	tabUserDbApi.removeComponent(treeTableFactory.getTreeTable());
            dbSelected = (String) dbComboBox.getValue();
            mapSelected.put("DbSelected", dbSelected);
            logger.log(Level.INFO, "Db selected: " + dbSelected);
            if (dbSelected != null && dbSelected.equals("labb3")) {
            	final JSONObject dbLabb3 = controller.getAllUserLabb3();
            	treeTableFactory = createTreeTable(dbLabb3);
            	searchUsers();
            	tabUserDbApi.addComponent(treeTableFactory.getTreeTable());
            }else {
            	if (dbSelected != null && dbSelected.equals("mgad")) {
                	final JSONObject dbMgad = controller.getAllUserMgad();
                	treeTableFactory = createTreeTable(dbMgad);
                	searchUsers();
                	tabUserDbApi.addComponent(treeTableFactory.getTreeTable());
                }
            }
        });
        return mapSelected;
    }
    /**
     * The search user method.
     * 1. not in put data in search textbox but click ok, it will remove treetable and crate new table and generate null to table.
     * 2. no data in database, it will remove old table and generate null to table.
     * 3. find data , it will remove old table and generate data to table. 
     * 
     */
    private void searchUsers() {	
    	searchTextField.addValueChangeListener(event -> {
    		strSearch = (String) event.getProperty().getValue();
    		Notification.show(strSearch);	
    	});
    	searchButtonOk.addClickListener(event -> {
    		// // delete old tree table first
    		tabUserDbApi.removeComponent(treeTableFactory.getTreeTable());
    		// get empty JSON Obj
    		JSONObject userObjEmpty = controller.getObjectEmpty();		
    		// crate new table with null JSON Obj
			table = treeTableFactory.createNewTableForSearch(userObjEmpty);
			// add table
			tabUserDbApi.addComponent(table);
    		logger.log(Level.INFO, "Request Log :" + strSearch);
    		try {
    			// send a parameter string to search in Rest api
    			JSONArray userArray = controller.searchUser(strSearch);
    			// no data match
        		if (userArray.isEmpty()){
        			// delete old table
        			tabUserDbApi.removeComponent(table);  
        			Notification.show(strSearch + " could not find ");
        			logger.log(Level.INFO, "Response Array log  :" + userArray.toString());   
        			// get empty JSON Obj
        			userObjEmpty = controller.getObjectEmpty();
        			// crate a new table with null JSON Obj
        			table = treeTableFactory .createNewTableForSearch(userObjEmpty);
        			// add new table 
        			tabUserDbApi.addComponent(table);       
        		// find user 
        		}else if ( userArray != null) {
        			// delete old table
        			tabUserDbApi.removeComponent(table);  
        			Notification.show(strSearch + " Found! ");
        			logger.log(Level.INFO, "Response Array log  :" + userArray.toString());
            		JsonUtil jsonUtil = new JsonUtil();
            		// send array data to method for build JSON obj
            		JsonObject userObj = (JsonObject) jsonUtil.buildJsonObjectFromSearchingUser(userArray);
            		String userStr = userObj.toString();
            		try {
            			// parser string to obj
            			JSONObject userOBJ = (JSONObject) new JSONParser().parse(userStr);  
                		treeTableFactory = new TreeTableFactory();
                		// crate new table with new json obj
                		table = treeTableFactory .createNewTableForSearch(userOBJ);
                		// add table
                		tabUserDbApi.addComponent(table );     			
            		}catch (Exception e) {
            			logger.log(Level.WARNING, "Something went wrong with connection" + e.getStackTrace());
            		}          		
            		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            		Object currentItemId = null;	
            		Object valueOfAname= null;
            		Object valueOfGname= null;
            		Object valueOfSname= null;
						@Override
						public void itemClick(ItemClickEvent event) {
						    
						    valueOfAname = table.getItem(currentItemId).getItemProperty("account_name").getValue().toString();
						    valueOfGname = table.getItem(currentItemId).getItemProperty("account_name").getValue().toString();
						    valueOfSname = table.getItem(currentItemId).getItemProperty("account_name").getValue().toString();
							if (valueOfAname != null){
								logger.log(Level.INFO, "Aname  :" + valueOfAname.toString());  
								buttonCreateRegToken.setEnabled(true);
							}		
						}
					});         		
            		logger.log(Level.INFO, "Response Object log  :" + userObj.toString());      			
        		}      		
    		}catch (JsonException e){
    			logger.log(Level.WARNING, "Something went wrong ", e.getStackTrace());			
    		}   		
    	});
    }
   
    private void getDb() {
        if ( customerSelected.equals("anvi")) {
            logger.log(Level.INFO, "Customer Selected: " + customerSelected.toString());
            final JSONArray dbAnvi = controller.getAnviDb();
            if (dbAnvi != null) {
                for (int i = 0; i < dbAnvi.size(); i++) {
                    dbComboBox.addItem(dbAnvi.get(i));
                    dbComboBox.select(dbAnvi.get(0));
                    dbComboBox.setNullSelectionAllowed(false);
                }
                logger.log(Level.INFO, "DB anvi: " + dbAnvi);
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
        try {
            treeTableFactory = new TreeTableFactory();
            treeTableFactory.createTreeTable(jsonObj);
            treeTableFactory.getTreeTable().setWidth("100%");
            treeTableFactory.getTreeTable().setColumnExpandRatio((Object) "LDAP Tree", 1.0f);
            treeTableFactory.getTreeTable().setColumnExpandRatio( "DNS", 1.0f);
        } catch (Exception e) {
            logger.log(Level.WARNING, " Http response error , please check url again ", e.getStackTrace());
        }
        return treeTableFactory;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WpAdminGuiUi.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
