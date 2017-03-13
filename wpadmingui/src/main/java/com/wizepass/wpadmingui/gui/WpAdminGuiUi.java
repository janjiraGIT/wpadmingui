package com.wizepass.wpadmingui.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;
import com.wizepass.wpadmingui.userdata.TreeTableFactory;
import com.wizepass.wpadmingui.controller.DataController;
import com.wizepass.wpadmingui.response.RestApiResponse;
import com.wizepass.wpadmingui.userdata.RegistationToken;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
@Theme("WpAdminGuiTheme")
public class WpAdminGuiUi extends UI {
	  	final TabSheet tabsheet = new TabSheet();
	    final VerticalLayout layoutMain = new VerticalLayout();
	    final HorizontalLayout buttonLayout = new HorizontalLayout();
	    final VerticalLayout tabUserDbApi = new VerticalLayout();
	    final Button buttonSearch = new Button();
	    final Button buttonCreateRegToken = new Button("Create Issue Registation Tokens");
	    final TextField textfield = new TextField();
	    final JSONObject alluser = new DataController().getAllUser();
	    final RegistationToken registationToken = new RegistationToken();
	    final IssueRegistationTokenWindow issueRegisToken = new IssueRegistationTokenWindow();
	    final ComboBox customerComboBox = new ComboBox();
	    final ComboBox dbComboBox = new ComboBox();
	    int countName ;
        int countNameSelect = 0;
	    private final Logger logger = Logger.getLogger(WpAdminGuiUi.class.getName());
	    private Window window = new Window();
	    final Label textToken = new Label("Token");
	    final TextField token = new TextField("Enter Token");
	    final Button okButton = new Button("ok");
	    final Button cancelButton = new Button("cancel");
	    final GridLayout grid = new GridLayout(4,4);
    	TokenGui tokenGui = new TokenGui();
 
    @Override
    protected void init(final VaadinRequest vaadinRequest) {
    	window = tokenGui.createTokenWindow();
    	addWindow(window);   
    	createUserDbApiTab();
        registationToken.createRegistationTokenTab(tabsheet);
        layoutMain.addComponent(tabsheet);
        layoutMain.setMargin(true);
        layoutMain.setSpacing(true);
        setContent(layoutMain);
       
    }
    public void createUserDbApiTab() {
        //final TextField tf = searchView(controller);

        buttonCreateRegToken.setEnabled(false);
        final TreeTableFactory treeTableFactory = createTreeTable(alluser);
        buttonCreateRegToken.setEnabled(true);
        buttonCreateRegToken.addClickListener(e -> {
            final Set<Integer> keySet = treeTableFactory.getPersons().keySet();
            System.err.println("Tree:");
          
            for (int i : keySet) {
                final Item item = treeTableFactory.getTreeTable().getItem(i);
                String givenName = (String) item.getItemProperty("Given Name").getValue();  
                
                CheckBox box = (CheckBox) item.getItemProperty("LDAP Tree").getValue();
                if (box.getValue()) {
                    System.err.println("Issue Wizepass for person: " + givenName  );
                    countName ++;
                }             
            }
            countNameSelect = countName;     
            IssueRegistationTokenWindow rgtWindow = new IssueRegistationTokenWindow();
            Window issueRegTokens = rgtWindow.createRegistationTokenWindow(countNameSelect);           
            addWindow(issueRegTokens);        
            
        }); 
        final JSONArray customers = new DataController().getCustomer();
        final JSONArray db = new DataController().getDb();
        customerComboBox.setInputPrompt("Select Customer");
        dbComboBox.setInputPrompt("Select DB");
        for (int i = 0; i < customers.size(); i++){
        	customerComboBox.addItem(customers.get(i));
        }
        for ( int i = 0; i < db.size(); i++){
            dbComboBox.addItem(db.get(i));
        }
        buttonLayout.addComponents(customerComboBox,dbComboBox);
        buttonLayout.setSpacing(true);
        tabUserDbApi.addComponents(buttonLayout,treeTableFactory.getTreeTable(), buttonCreateRegToken);
        tabUserDbApi.setMargin(true);
        tabUserDbApi.setSpacing(true);
        tabsheet.addTab(tabUserDbApi, "UserDbApi");
		
    }
    
//    private TextField searchView(final JSONObject jsonObj) {
//    	 final String objStr = jsonObj.toString();
//         final TextField tf = new TextField("Search :");
//         tf.setWidth("40%");
//         tf.focus();
//         tf.setInputPrompt("Write some text ");
//         tf.addValueChangeListener(new Property.ValueChangeListener() {
//             private static final long serialVersionUID = 5678485350989070188L;
//             @Override
//             public void valueChange(final ValueChangeEvent event) {
//                     String value = (String) event.getProperty().getValue();
//                     List<Object> list = (List<Object>) getDistinguishedName(jsonObj);
//                     String listStr = list.toString();
//                     if ( listStr.contains(value)) {
//                         Notification.show(value, Type.TRAY_NOTIFICATION);
//                         logger.log(Level.INFO, " Get user data from UserDbApi");
//                     } else {
//                         Notification.show("Not found ",value, Type.TRAY_NOTIFICATION);
//                     }
//             }
//         });
//         tf.setImmediate(true);
//         return tf;
//     }

//    private List<Object> getDistinguishedName(final JSONObject jsonObj) {
//        final List<Object> listObj = new LinkedList<Object>();
//        final JSONObject attributes = (JSONObject) jsonObj.get("attributes");
//       // final JSONArray dis = (JSONArray) attributes.get("distinguishedName");
//
//        String disStr = "";
//        if (attributes != null && attributes.get("distinguishedName") != null) {
//            //final JSONArray dis = (JSONArray) attributes.get("distinguishedName");
//            for (int i = 0; i < attributes.size(); i++) {
//                disStr += attributes.get(i);
//            }
//            logger.log(Level.INFO, "Yoooo ");
//            listObj.add(disStr);
//        } else {
//            logger.log(Level.INFO, "Not found ");
//            listObj.add(null);
//        }
//        return listObj;
//    }
    private TreeTableFactory createTreeTable(final JSONObject jsonObj) {
        TreeTableFactory treeTableFactory = null;
        try {
            treeTableFactory = new TreeTableFactory();
            treeTableFactory.createTreeTable(jsonObj);
            treeTableFactory.getTreeTable().setWidth("75%");
            treeTableFactory.getTreeTable().setColumnExpandRatio((Object) "LDAP Tree", 1.0f);
        } catch (Exception e) {
            logger.log(Level.WARNING, " Http response error ", e.getMessage());
        }
        return treeTableFactory;
    }
    
    @WebServlet(urlPatterns = "/*", name = "WpAdminGuiUiServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WpAdminGuiUi.class, productionMode = false)
    public static class WpAdminGuiUiServlet extends VaadinServlet {
    }
}