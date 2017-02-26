package com.wizepass.wpadmingui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.wizepass.wpadmingui.userdata.HttpClientRequest;
import com.wizepass.wpadmingui.userdata.RdnTypeParser;
import com.wizepass.wpadmingui.userdata.RdnTypeParserDC;
import com.wizepass.wpadmingui.userdata.RdnTypeParserRoot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

@Theme("WpAdminGuiTheme")
public class WpAdminGuiUi extends UI {
    final VerticalLayout layout = new VerticalLayout();
    final Button buttonSearch = new Button();
   final Button buttonCreateRegToken = new Button("Create Registation Token");
 
    @Override
    protected void init(final VaadinRequest vaadinRequest) {

        final TreeTable treeTable = createTreeTable();
        final TextField textFieldSearch = searchView();
        final Label showData = showDataFromSearch(textFieldSearch);
        buttonCreateRegToken.setEnabled(false);
        layout.addComponents( treeTable, buttonCreateRegToken, textFieldSearch, showData);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }
    
    private TextField searchView() {
        final TextField textFieldSearch = new TextField("Search :");
        textFieldSearch.setWidth("30%");
        textFieldSearch.getValue();
        return textFieldSearch;
    }

    @SuppressWarnings("unused")
    private Label showDataFromSearch( final TextField text) {
        final Label label = new Label();
        TextField textfield = searchView();
        label.setValue(textfield.getValue());
        return label;
    }

    private TreeTable createTreeTable() {
    	
        final TreeTable treeTable = new TreeTable();

        treeTable.addContainerProperty("LDAP Tree", CheckBox.class, "");
        treeTable.addContainerProperty("Search Base", String.class, null);
        treeTable.addContainerProperty("Given Name", String.class, null);
        treeTable.addContainerProperty("Surname", String.class, null);
        treeTable.setWidth("70%");
        treeTable.setColumnExpandRatio((Object) "LDAP Tree", 1.0f);

        final RdnTypeParser parser = new RdnTypeParserRoot();
        try {
            final JSONObject jsonObj = HttpClientRequest.loadLdapData();
            // try to do process Json's parent and children  by giving jsonObject from file , treetable and start row -1 
            parser.parse(jsonObj, treeTable, -1);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
        return treeTable;
    }

    @WebServlet(urlPatterns = "/*", name = "WpAdminGuiUiServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WpAdminGuiUi.class, productionMode = false)
    public static class WpAdminGuiUiServlet extends VaadinServlet {
    }
}