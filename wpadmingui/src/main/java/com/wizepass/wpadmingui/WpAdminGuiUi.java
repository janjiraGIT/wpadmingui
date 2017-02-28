package com.wizepass.wpadmingui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.wizepass.wpadmingui.userdata.HttpClientRequest;
import com.wizepass.wpadmingui.userdata.TreeTableFactory;

import org.json.simple.JSONObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

@Theme("WpAdminGuiTheme")
public class WpAdminGuiUi extends UI {
    final VerticalLayout layout = new VerticalLayout();
    final Button buttonSearch = new Button();
    final Button buttonCreateRegToken = new Button("Create Registation Token");
    final TextField textfield = new TextField();
    final JSONObject jsonObj = new com.wizepass.wpadmingui.userdata.HttpClientRequest().loadLdapData();
    private final Logger logger = Logger.getLogger(WpAdminGuiUi.class.getName());
 
    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final TextField tf = searchView(jsonObj);
        buttonCreateRegToken.setEnabled(false);

        final TreeTableFactory treeTableFactory = createTreeTable(jsonObj);

        buttonCreateRegToken.setEnabled(true);
        buttonCreateRegToken.addClickListener(e -> {
            final Set<Integer> keySet = treeTableFactory.getPersons().keySet();
            System.err.println("Tree:");
            for (int i : keySet) {
                final Item item = treeTableFactory.getTreeTable().getItem(i);
                CheckBox box = (CheckBox) item.getItemProperty("LDAP Tree").getValue();
                String givenName = (String) item.getItemProperty("Given Name").getValue();
                System.err.print(box.getValue());
                System.err.println(" " + givenName);
            }
        });
        layout.addComponents(treeTableFactory.getTreeTable(), buttonCreateRegToken, tf);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }
    
    private TextField searchView(final JSONObject jsonObj) {
    	 final String objStr = jsonObj.toString();
         final TextField tf = new TextField("Search :");
         tf.setWidth("40%");
         tf.focus();
         tf.setInputPrompt("Write some text ");
         tf.addValueChangeListener(new Property.ValueChangeListener() {
             private static final long serialVersionUID = 5678485350989070188L;
             @Override
             public void valueChange(final ValueChangeEvent event) {
                     String value = (String) event.getProperty().getValue();
                     List<Object> list = (List<Object>) getDistinguishedName(jsonObj);
                     String listStr = list.toString();
                     if ( listStr.contains(value)) {
                         Notification.show(value, Type.TRAY_NOTIFICATION);
                         logger.log(Level.INFO, " Get user data from UserDbApi");
                     } else {
                         Notification.show("Not found ",value, Type.TRAY_NOTIFICATION);
                     }
             }
         });
         tf.setImmediate(true);
         return tf;
     }

    private List<Object> getDistinguishedName(final JSONObject jsonObj) {
        final List<Object> listObj = new LinkedList<Object>();
        final JSONObject attributes = (JSONObject) jsonObj.get("attributes");
       // final JSONArray dis = (JSONArray) attributes.get("distinguishedName");

        String disStr = "";
        if (attributes != null && attributes.get("distinguishedName") != null) {
            //final JSONArray dis = (JSONArray) attributes.get("distinguishedName");
            for (int i = 0; i < attributes.size(); i++) {
                disStr += attributes.get(i);
            }
            logger.log(Level.INFO, "Yoooo ");
            listObj.add(disStr);
        } else {
            logger.log(Level.INFO, "Not found ");
            listObj.add(null);
        }
        return listObj;
    }
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