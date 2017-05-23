package com.wizepass.WpAdminGui.gui;


import com.vaadin.server.Page;
import com.vaadin.shared.Position;
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
import com.wizepass.WpAdminGui.controller.DataController;
import com.wizepass.WpAdminGui.response.RestApiResponse;
import com.wizepass.WpAdminGui.util.Constants;
import com.wizepass.WpAdminGui.util.JsonUtil;

import org.apache.http.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;


public class IssueRegistationTokenWindow {
    final Label titleText = new Label("Issue Registation Tokens");
    final Label descriptionText = new Label("Description :");
    final Label validTimeText = new Label("Valid Time :");
    final Label certProfileText = new Label("Cert Profile :" );
    final Label providerText = new Label("Provider :" );
    final Label userSelectText = new Label("User selected :" );
    final Label userSelectedValue = new Label();
    final TextField descriptionValue = new TextField();
    final Button okButton = new Button("ok");
    final Button cancelButton = new Button("cancel");
    final Window window = new Window();
    final ComboBox timeSelectCombobox = new ComboBox();
    final ComboBox profileSelectCombobox = new ComboBox();
    final ComboBox providerCombobox = new ComboBox();
    final GridLayout grid = new GridLayout(5,8);
    private String descriptionValueStr = null;
    private String timeValueStr = null;
    private String certProfileStr = null;
    private String providerStr = null;

    /**
     * crate Registation Token window.
     **/
    public Window createRegistationTokenWindow(final int userSelected, final Map<String,String> mapSelected,
                                               final String dbSelected, final List<String> listOfDns) {
        titleText.setStyleName("bold");
        setGrid();
        userSelectedValue.setValue("" + userSelected);
        addListInToComboBox();
        window.setContent(grid);
        createPopUp(userSelected, mapSelected, dbSelected, listOfDns);
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

    private  void addListInToComboBox() {
        final DataController dataController = new DataController();
        final List<String> timeList = dataController.getTimeList();
        final JSONArray profileJsonArray = dataController.getCertProfile();
        final JSONArray providerJsonArray = dataController.getProvider();
        timeSelectCombobox.addItems(timeList);
        for (int i = 0 ; i < profileJsonArray.size(); i++) {
            profileSelectCombobox.addItem(profileJsonArray.get(i));
        }
        for (int i = 0 ; i < providerJsonArray.size(); i++) {
            providerCombobox.addItem(providerJsonArray.get(i));
        }
    }

    /**
     * create pop up window.
     */
    public void createPopUp(final int userSelected, final Map<String,String> mapSelected, final String dbSelected , final List<String> listOfDns) {
        descriptionValue.addValueChangeListener(event -> {
            descriptionValueStr = (String) event.getProperty().getValue();
        });
        timeSelectCombobox.addValueChangeListener(event -> {
            timeValueStr = (String) event.getProperty().getValue();
        });
        profileSelectCombobox.addValueChangeListener(event -> {
            certProfileStr = (String) event.getProperty().getValue();
        });
        providerCombobox.addValueChangeListener(event -> {
            providerStr = (String) event.getProperty().getValue();
        });
        final String customer = mapSelected.get("CustomerSelected").toString();
        final String userDb = mapSelected.get("DbSelected");
        okButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	final JsonUtil jsonUtil = new JsonUtil();
            	final JsonObject obj = jsonUtil.buildJsonObject(customer, userDb, listOfDns, dbSelected, dbSelected, dbSelected, dbSelected, userSelected);
            	
            	try {
            		final RestApiResponse restApiResponse = new RestApiResponse();
            		final List<String> dataPost = restApiResponse.postData(obj,Constants.URL_ADDRESS+Constants.WP_REST+Constants.REG_TOKENS );
            		final String data = dataPost.get(0);
            		final JSONObject jsonObj = new JsonUtil().createJsonObject(data);
            		final Notification notification = new Notification(
            					
            						 "CustomerId : " + mapSelected.get("CustomerSelected") + "\n\n"
                                     + "UserDbId :   " +  mapSelected.get("DbSelected") + "\n\n"
                                     + "ListOfEndUserId :   " + listOfDns + "\n\n"
                                     + "Description : " + descriptionValueStr + "\n\n"
                                     + "ValidTime :      " + timeValueStr + "\n\n"
                                     + "CertProfileId : " + certProfileStr + "\n\n"
                                     + "ProviderId :   " + providerStr + "\n\n"
                                     + "UserSelected :   " + userSelected + "\n\n",
                                     Type.HUMANIZED_MESSAGE);
            		notification.setDelayMsec(20000);
            		notification.show(Page.getCurrent());
            		notification.setPosition(Position.BOTTOM_LEFT);
            	} catch (IOException | ParseException e) {
                    System.out.println("Fel IO | Parse Exception" + e.getStackTrace());
                } catch (org.json.simple.parser.ParseException e) {
                	e.printStackTrace();
				}  
            }
            	
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                Notification.show("Cancel", Type.TRAY_NOTIFICATION);
                window.close();
            }
        });
    }
}