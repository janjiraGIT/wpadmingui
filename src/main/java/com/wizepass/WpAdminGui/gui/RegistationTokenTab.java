package com.wizepass.WpAdminGui.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
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
    @SuppressWarnings("deprecation")
	public void createRegistationTokenTab(final TabSheet tabsheet) {
        final VerticalLayout layoutTab2 = new VerticalLayout();
        final HorizontalLayout layoutForButton = new HorizontalLayout();
        final Table table = new Table();
        table.setSelectable(true);
        table.setImmediate(true);
        final Button buttonPublish = new Button("Publish Token");
        final Button buttonDelete = new Button("Delete Token");
        table.addContainerProperty("Registation_code", String.class, null);
        table.addContainerProperty("Customer_id", String.class, null);
        table.addContainerProperty("Registation_date", String.class, null);
        table.addContainerProperty("Description", String.class, null);
        final DataController datacontroller = new DataController();
        try {
            final JSONArray regTokenArray = datacontroller.getRegistrationtokens();   
            layoutTab2.removeComponent(table);
            createTreeTable(regTokenArray, table);
            layoutTab2.addComponents(table,layoutForButton);
        } catch (Exception e) {
        	logger.log(Level.WARNING, "Something went wrong with connection" + e.getStackTrace());
        }
        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				Object text = table.getValue();		
				logger.log(Level.INFO,  text.toString());
				
			}
		});           
        table.setWidth("80%");
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
    public void createTreeTable(final JSONArray jsonArrayReg , final Table treeTableInIssueCertificate) {
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