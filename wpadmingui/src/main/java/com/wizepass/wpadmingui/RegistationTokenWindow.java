package com.wizepass.wpadmingui;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;
public class RegistationTokenWindow {
	
	final Label irtText = new Label("Issue Registation Tokens");
	final Label descriptionText = new Label("Description :");
	final Label validTimeText = new Label("Valid Time :");
	final Label certProfileText = new Label("Cert Profile :" );
	final Label userSelectText = new Label("User selected :" );
	final Label userSelectedValue = new Label();
	final TextField descriptionValue = new TextField();
    final Window window = new Window();

    List<String> timeList = new ArrayList<>();
    List<String> profileList = new ArrayList<>();
    final ComboBox timeSelectCombobox = new ComboBox();
    final ComboBox profileSelectCombobox = new ComboBox();

	public Window createWindow(int countSelect) {
    	final GridLayout grid = new GridLayout(4,6);
    	irtText.setStyleName("bold");
    	grid.addComponent(irtText, 2,0);
    	grid.addComponent(descriptionText,1,2);
    	grid.addComponent(validTimeText,1,3);
    	grid.addComponent(certProfileText,1,4);
    	grid.addComponent(userSelectText,1,5);  	
    	timeList = createTimeList();    
    	profileList = createProfile();  
    	// add list in combobox
    	timeSelectCombobox.addItems(timeList);
    	profileSelectCombobox.addItems(profileList);
    	// add combobox in grid 
    	grid.addComponent(descriptionValue,2,2);
    	grid.addComponent(timeSelectCombobox,2,3);
    	grid.addComponent(profileSelectCombobox,2,4);  	
        userSelectedValue.setValue(""+countSelect);
        grid.addComponent(userSelectedValue,2,5);
    	grid.setMargin(true);
    	grid.setSpacing(true);
        window.setContent(grid);
        window.center();
        window.setHeight("300px");
        window.setWidth("500px");
        window.setPosition(400, 300);
        return window;
    }
    private List<String> createTimeList() {
        timeList.add("10 Minutes");
        timeList.add("1 hour");
        timeList.add("1 day");
        timeList.add("1 week");
        return timeList;
    }
    private List<String> createProfile() {
        profileList.add("profile1");
        profileList.add("profile2");
        profileList.add("profile3");
        profileList.add("profile4");
        return profileList;
    }

}
