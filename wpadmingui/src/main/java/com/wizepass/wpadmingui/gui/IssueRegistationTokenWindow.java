package com.wizepass.wpadmingui.gui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
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
    List<String> timeList = new ArrayList<>();
    List<String> profileList = new ArrayList<>();
    final ComboBox timeSelectCombobox = new ComboBox();
    final ComboBox profileSelectCombobox = new ComboBox();
    final ComboBox providerCombobox = new ComboBox();
	final GridLayout grid = new GridLayout(5,8);

	public Window createRegistationTokenWindow(final int userSelected) {

    	titleText.setStyleName("bold");
    	setGrid();
    	addListInToComboBox();
    	userSelectedValue.setValue(""+userSelected);
        window.setContent(grid);
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
        final JSONArray profileList = dataController.getCertProfile();
        final JSONArray providerList = dataController.getProvider();
        timeSelectCombobox.addItems(timeList);
        //TODO .... fel
        profileSelectCombobox.addItems(profileList.get(0));
        providerCombobox.addItems(providerList.get(0),providerList.get(1));
    }

}
