package com.wizepass.WpAdminGui.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.wizepass.WpAdminGui.controller.DataController;
import com.wizepass.WpAdminGui.response.RestApiResponse;
import com.wizepass.WpAdminGui.userdata.RegistationToken;

public class RegistationTokenTab {
	private final Logger logger = Logger.getLogger(RegistationTokenTab.class.getName());

    /**
     * Registation Token.
     **/
    public void createRegistationTokenTab(final TabSheet tabsheet) {
        final VerticalLayout layoutTab2 = new VerticalLayout();
        final HorizontalLayout layoutForButton = new HorizontalLayout();
        final TreeTable treeTableTabReg = new TreeTable();
        final Button buttonPublish = new Button("Publish Token");
        final Button buttonDelete = new Button("Delete Token");
        treeTableTabReg.addContainerProperty("Registation_code", String.class, null);
        treeTableTabReg.addContainerProperty("Customer_id", String.class, null);
        treeTableTabReg.addContainerProperty("Registation_date", String.class, null);
        treeTableTabReg.addContainerProperty("Description", String.class, null);
        final DataController datacontroller = new DataController();
        try {
            final JSONArray regTokenArray = datacontroller.getRegistrationtokens();   
            layoutTab2.removeComponent(treeTableTabReg);
            createTreeTable(regTokenArray, treeTableTabReg);
            layoutTab2.addComponents(treeTableTabReg,layoutForButton);
        } catch (Exception e) {
        	logger.log(Level.WARNING, "Something went wrong with connection" + e.getStackTrace());
        }
        treeTableTabReg.setWidth("80%");
        buttonPublish.setWidth("50%");
        buttonDelete.setWidth("50%");
        layoutForButton.addComponents(buttonPublish, buttonDelete);
        layoutForButton.setMargin(true);
        layoutForButton.setWidth("70%");
        layoutTab2.setMargin(true);
        layoutTab2.setSpacing(true);
        tabsheet.addTab(layoutTab2, "Registation Token");
    }
    
    /**
     * Data test in tree table.
     **/
    public void createTreeTable(final JSONArray jsonArrayReg , final TreeTable treeTableInIssueCertificate) {
        int row = 0;
        treeTableInIssueCertificate.removeAllItems();
        for (int i = 0; i < jsonArrayReg.size(); i++) {
            final JSONObject jsonObj = (JSONObject) jsonArrayReg.get(i);
            final String regCode = (String) jsonObj.get("registration_code");
            final String customerId = (String) jsonObj.get("customer_id");
            final String regDate = (String) jsonObj.get("date");
            final String description = (String) jsonObj.get("description");
            treeTableInIssueCertificate.addItem(new Object[] {regCode,customerId,regDate,description}, row );
            row++;
        }
    }

}