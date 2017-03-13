package com.wizepass.wpadmingui.gui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class TokenGui {final Window window = new Window();
final Label textToken = new Label("Token");
final TextField token = new TextField();
final Button okButton = new Button("ok");
final Button cancelButton = new Button("cancel");
final GridLayout grid = new GridLayout(6,8);
final String password = "aaa";
String tokenStr = null;

/**
 * First page.
 **/
public Window createTokenWindow() {
    textToken.setStyleName("bold");
    grid.addComponent(textToken,2,1);
    grid.addComponent(token,3,1);
    grid.addComponent(okButton,3,2);
    grid.addComponent(cancelButton,4,2);
    grid.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
    grid.setComponentAlignment(cancelButton, Alignment.BOTTOM_LEFT);
    grid.setMargin(true);
    grid.setSpacing(true);
    window.setContent(grid);
    token.addValueChangeListener(event -> {
        tokenStr = (String) event.getProperty().getValue();
    });

    okButton.addClickListener(new Button.ClickListener() {
        @Override
        public void buttonClick(final ClickEvent event) {
            if (tokenStr.equals(password)) {
                Notification.show("Token is correct ", Type.HUMANIZED_MESSAGE );
                window.close();
            } else {
                Notification.show("Token failed, try again" , Type.HUMANIZED_MESSAGE);
            }
        }
    });
    cancelButton.addClickListener(new Button.ClickListener() {
        @Override
        public void buttonClick(final ClickEvent event) {
            Notification.show("cancel", Type.HUMANIZED_MESSAGE);
        }
    });
    window.center();
    window.setSizeFull();
    return window;
}
}