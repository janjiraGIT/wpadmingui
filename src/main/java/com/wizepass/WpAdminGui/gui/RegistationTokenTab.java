package com.wizepass.WpAdminGui.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.wizepass.WpAdminGui.controller.DataController;
import com.wizepass.WpAdminGui.response.RestApiResponse;
import com.wizepass.WpAdminGui.userdata.RegistationToken;

public class RegistationTokenTab {
	private final Logger logger = Logger.getLogger(RegistationTokenTab.class.getName());
    final Button buttonPublish = new Button("Publish Token");
    final Button buttonDelete = new Button("Delete Token");
    final Table table = new Table();
    String valueStr = null;
    Object currentItemId = null;

    /**
     * Registation Token.
     **/
    @SuppressWarnings("deprecation")
	public void createRegistationTokenTab(final TabSheet tabsheet) {
        final VerticalLayout layoutTab2 = new VerticalLayout();
        final HorizontalLayout layoutForButton = new HorizontalLayout();

        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("Registration_code", String.class, null);
        table.addContainerProperty("Date_registration", String.class,null);
        table.addContainerProperty("Customer_id", String.class, null);
        table.addContainerProperty("Registration_date", String.class, null);
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
        selectRawInTable(table);   
        table.setWidth("80%");
        buttonPublish.setWidth("50%");
        buttonDelete.setWidth("50%");
        layoutForButton.addComponents(buttonPublish, buttonDelete);
        layoutForButton.setMargin(true);
        layoutForButton.setWidth("70%");
        layoutTab2.setMargin(true);
        layoutTab2.setSpacing(true);
        tabsheet.addTab(layoutTab2, "Registration Token");
    }

	private void clickPublish(final Button buttonPublish, final String valueOfSelect) {
		buttonPublish.setClickShortcut(KeyCode.ENTER);
        buttonPublish.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (valueOfSelect == null){
					Notification.show("no record selected ", Type.HUMANIZED_MESSAGE);
				}else {
					Notification.show("Seleted " + valueOfSelect.toString() , Type.HUMANIZED_MESSAGE);
				}
				
			}
		});
	}

	public void selectRawInTable(final Table table) {
		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				currentItemId = event.getItemId();	
				Object valueOfReg = table.getItem(currentItemId).getItemProperty("Registration_code").getValue();
				valueStr = valueOfReg.toString();
				logger.log(Level.INFO, valueStr);	
				buttonPublish.setClickShortcut(KeyCode.ENTER);
		        buttonPublish.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						if (valueStr != null){
							Notification.show("Publish registration_code : " + valueStr.toString() , Type.HUMANIZED_MESSAGE);
						}else {
							Notification.show("No record selected ", Type.HUMANIZED_MESSAGE);	
						}				
					}
				});
			}			
		});
		
				buttonDelete.addClickListener(new Button.ClickListener() {
			
				@Override
				public void buttonClick(ClickEvent event) {
					if (valueStr !=null){
						Notification.show("Deleted registration_code : " + valueStr.toString() , Type.HUMANIZED_MESSAGE);
						table.removeItem(currentItemId);
					}else {
						Notification.show("No record selected for deleted ", Type.HUMANIZED_MESSAGE);	
					}					
				}
			});
	}
    
    /**
     * Data test in tree table.
     **/
    public void createTreeTable(final JSONArray jsonArrayReg , final Table table) {
        int row = 0;
        for (int i = 0; i < jsonArrayReg.size(); i++) {
            final JSONObject jsonObj = (JSONObject) jsonArrayReg.get(i);
            final String regCode = (String) jsonObj.get("registration_code");
            final String date = (String) jsonObj.get("date");
            final String customerId = (String) jsonObj.get("customer_id");
            final String regDate = (String) jsonObj.get("rt_valid_duration");
            final String description = (String) jsonObj.get("description");
            table.addItem(new Object[] {regCode,date,customerId,regDate,description}, row );       
            row++;
        }
    }

}