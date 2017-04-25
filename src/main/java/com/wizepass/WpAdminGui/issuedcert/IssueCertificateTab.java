package com.wizepass.WpAdminGui.issuedcert;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeTable;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.wizepass.WpAdminGui.controller.DataController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class IssueCertificateTab {
    final VerticalLayout layoutTab3 = new VerticalLayout();
    final HorizontalLayout layoutForButton = new HorizontalLayout();
    final Button buttonOk = new Button("ok");
    final Button buttonRemove = new Button("remove");
    final DataController dataController = new DataController();

    /**
     * @param tabsheet.
     **/
    public void createIssueCertTab(final TabSheet tabsheet) {
        final TreeTable treeTableInIssueCertificate = new TreeTable();
        treeTableInIssueCertificate.addContainerProperty("Data ", String.class, null);
        treeTableInIssueCertificate.addStyleName(ValoTheme.TREETABLE_NO_STRIPES);
        final JSONArray jsonArrayTest = dataController.getRegistrationtokens();
        createTreeTable(jsonArrayTest, treeTableInIssueCertificate);
        treeTableInIssueCertificate.setWidth("80%");
        buttonOk.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttonRemove.addStyleName(ValoTheme.BUTTON_DANGER);
        this.clickButton();
        layoutForButton.addComponents(buttonOk, buttonRemove);
        layoutForButton.setMargin(true);
        layoutForButton.setWidth("70%");
        layoutTab3.setMargin(true);
        layoutTab3.setSpacing(true);
        layoutTab3.addComponents(treeTableInIssueCertificate, layoutForButton);
        tabsheet.addTab(layoutTab3, "Issued Certificates");
    }

    /**
     * Data test in tree table.
     **/
    public void createTreeTable(final JSONArray jsonArrayTest , final TreeTable treetable) {
        int row = 0;
        for (int i = 0; i < jsonArrayTest.size(); i++) {
            final JSONObject jsonObj = (JSONObject) jsonArrayTest.get(i);
            final String regCode = (String) jsonObj.get("registration_code");
            treetable.addItem(new Object[] {regCode}, row );
            row++;
        }
    }

    /**
     *  click on ok or delete button.
     */
    public void clickButton() {
        buttonOk.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                final Notification popupOk = new Notification("Ok click", Type.WARNING_MESSAGE);
                popupOk.setDelayMsec(20000);
                popupOk.setPosition(Position.BOTTOM_LEFT);
                popupOk.show(Page.getCurrent());
            }
        });
        buttonRemove.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                final Notification popupRemove = new Notification("Remove click", Type.WARNING_MESSAGE);
                popupRemove.setDelayMsec(20000);
                popupRemove.setPosition(Position.BOTTOM_LEFT);
                popupRemove.show(Page.getCurrent());
            }
        });
    }
}
