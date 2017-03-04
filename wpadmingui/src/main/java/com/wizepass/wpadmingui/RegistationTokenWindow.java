package com.wizepass.wpadmingui;


import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;
public class RegistationTokenWindow {
	final Label label = new Label("Issue Registation Tokens");
    final TextField textField = new TextField("Description : ");
    final Window window = new Window();
    final VerticalLayout layout = new VerticalLayout();
    final List<String> timeList = new ArrayList<>();
    final List<String> profileList = new ArrayList<>();
    final ComboBox selectTime = new ComboBox("Valid Time :");
    final ComboBox selectProfile = new ComboBox("Cert profile :");

    /**
     * crate window.
     **/
    public Window createWindow() {
        layout.setMargin(true);
        layout.setSpacing(true);
        final List<String> timeList = createTimeList();
        final List<String> profileList = createProfile();
        selectTime.addItems(timeList);
        selectProfile.addItem(profileList);
        layout.addComponents(label,textField,selectTime,selectProfile);
        window.setContent(layout);
        window.center();
        window.setHeight("500px");
        window.setWidth("700px");
        window.setPosition(150, 70);
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
        profileList.add("profile 1");
        profileList.add("profile 2");
        profileList.add("profile 3");
        profileList.add("profile 4");
        return profileList;
    }
}
