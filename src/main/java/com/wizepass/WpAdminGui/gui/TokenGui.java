package com.wizepass.WpAdminGui.gui;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TokenGui {
    final Window window = new Window();
    final Label textToken = new Label("Password");
    final PasswordField pw = new PasswordField();
    final Button okButton = new Button("ok");
    final Button cancelButton = new Button("cancel");
    final GridLayout grid = new GridLayout(6,8);
    private String tokenStr = null;
    private final Logger logger = Logger.getLogger(TokenGui.class.getName());

    /**
     * First page Token.
     **/
    public Window createTokenWindow(final String token) {
        textToken.setStyleName("bold");
        grid.addComponent(textToken,2,1);
        grid.addComponent(pw,3,1);
        grid.addComponent(okButton,3,2);
        grid.addComponent(cancelButton,4,2);
        grid.setComponentAlignment(okButton, Alignment.BOTTOM_RIGHT);
        grid.setComponentAlignment(cancelButton, Alignment.BOTTOM_LEFT);
        grid.setMargin(true);
        grid.setSpacing(true);
        window.setContent(grid);
        pw.setInputPrompt("Enter your token");
        pw.addValueChangeListener(event -> {
            tokenStr = (String) event.getProperty().getValue();
        });
        clickOkButton(token);
        clickCancelButton();
        window.center();
        window.setSizeFull();
        return window;
    }

    private void clickOkButton(final String token) {
        okButton.setClickShortcut(KeyCode.ENTER);
        okButton.setStyleName(ValoTheme.BUTTON_SMALL);
        okButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (tokenStr == null) {
                    Notification.show("Please enter your token!! ", Type.HUMANIZED_MESSAGE );
                    logger.log(Level.INFO, "Please enter your token");
                } else if (tokenStr != null && tokenStr.equals(token)) {
                    Notification.show("Token is correct ", Type.HUMANIZED_MESSAGE );
                    logger.log(Level.INFO, "Token " + tokenStr + " is correct.");
                    window.close();
                } else if (tokenStr != token) {
                    Notification.show("Token failed, Try again" , Type.HUMANIZED_MESSAGE);
                    logger.log(Level.INFO, "Token " + tokenStr + " is not correct, please try again. ");
                }
            }
        });
    }

    private void clickCancelButton() {
        cancelButton.setStyleName(ValoTheme.BUTTON_SMALL);
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if ( tokenStr != null ) {
                    pw.clear();
                    Notification.show("cancelled Token", Type.HUMANIZED_MESSAGE);
                    logger.log(Level.INFO, "Cancelled token");
                } else if ( tokenStr == null) {
                    Notification.show("Noting to cancel", Type.HUMANIZED_MESSAGE);
                    logger.log(Level.INFO, "Noting to cancel");
                }
            }
        });
    }
}