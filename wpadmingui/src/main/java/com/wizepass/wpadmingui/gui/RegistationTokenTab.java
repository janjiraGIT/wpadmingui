package com.wizepass.wpadmingui.gui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

public class RegistationTokenTab {

    /**
     * Registation Token.
     **/
    public void createRegistationTokenTab(final TabSheet tabsheet) {
        final VerticalLayout layoutTab2 = new VerticalLayout();
        final HorizontalLayout layoutForButton = new HorizontalLayout();
        final TreeTable treeTableTab2 = new TreeTable();
        final Button buttonPublish = new Button("Publish Token");
        final Button buttonDelete = new Button("Delete Token");
        treeTableTab2.addContainerProperty("Given Name", String.class, null);
        treeTableTab2.addContainerProperty("Last Name", String.class, null);
        treeTableTab2.setWidth("80%");
        buttonPublish.setWidth("50%");
        buttonDelete.setWidth("50%");
        layoutForButton.addComponents(buttonPublish, buttonDelete);
        layoutForButton.setMargin(true);
        layoutForButton.setWidth("70%");
        layoutTab2.setMargin(true);
        layoutTab2.setSpacing(true);
        layoutTab2.addComponents(treeTableTab2,layoutForButton);
        tabsheet.addTab(layoutTab2, "Registation Token");
    }

}