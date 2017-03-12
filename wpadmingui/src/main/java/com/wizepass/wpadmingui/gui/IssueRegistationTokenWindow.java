package com.wizepass.wpadmingui.gui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.wizepass.wpadmingui.controller.DataController;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
public class IssueRegistationTokenWindow {
	
	final Label titleText = new Label("Issue Registation Tokens");
	final Label descriptionText = new Label("Description :");
	final Label validTimeText = new Label("Valid Time :");
	final Label certProfileText = new Label("Cert Profile :" );
	final Label providerText = new Label("Provider : ");
	final Label userSelectText = new Label("User selected :" );
	final Label userSelectedValue = new Label();
	final TextField descriptionValue = new TextField();
	final Button okButton = new Button("OK");
	final Button cancelButton = new Button("Cancel");
    final Window window = new Window();
    final ComboBox timeSelectCombobox = new ComboBox();
    final ComboBox profileSelectCombobox = new ComboBox();
    final ComboBox providerCombobox = new ComboBox();
	final GridLayout grid = new GridLayout(5,8);
	JSONArray profileJsonArray = null;
	JSONArray providerJsonArray = null;

	public Window createRegistationTokenWindow(final int userSelected) {

    	titleText.setStyleName("bold");
    	setGrid();
    	addListInToComboBox();
    	userSelectedValue.setValue(""+userSelected);
        window.setContent(grid);
        okButton.addClickListener(new Button.ClickListener() {	
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Click Ok", Type.TRAY_NOTIFICATION);	
			}
		});
        cancelButton.addClickListener(new Button.ClickListener() {	
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Click Cancel", Type.TRAY_NOTIFICATION);	
				window.close();
			}
		});
        window.center();
        window.setHeight("400px");
        window.setWidth("500px");
        window.setPosition(350, 300);
        return window;
    }
	private void setGrid() {
		grid.addComponent(titleText, 2,0);
    	grid.addComponent(descriptionText,1,2);
    	grid.addComponent(descriptionValue,2,2);
    	grid.addComponent(validTimeText,1,3);
    	grid.addComponent(timeSelectCombobox,2,3);
    	grid.addComponent(certProfileText,1,4);
    	grid.addComponent(profileSelectCombobox,2,4);
    	grid.addComponent(providerText,1,5);
    	grid.addComponent(providerCombobox,2,5);
    	grid.addComponent(userSelectText,1,6);
    	grid.addComponent(userSelectedValue,2,6);
    	grid.addComponent(okButton,2,7);
    	grid.addComponent(cancelButton,3,7);
        grid.setComponentAlignment(okButton, Alignment.TOP_RIGHT);
        grid.setComponentAlignment(cancelButton, Alignment.TOP_LEFT);
    	grid.setMargin(true);
    	grid.setSpacing(true);
	}
    private void addListInToComboBox() {
        final DataController dataController = new DataController();
        final List<String> timeList = dataController.getTimeList();
        profileJsonArray = dataController.getCertProfile();
        providerJsonArray = dataController.getProvider();
        timeSelectCombobox.addItems(timeList);
        for (int i = 0; i<profileJsonArray.size();i++){
    		 profileSelectCombobox.addItems(profileJsonArray.get(i));	
    	}
        for (int i = 0; i<providerJsonArray.size();i++){
   		 	providerCombobox.addItems(providerJsonArray.get(i));	
        }
    }
    


}
