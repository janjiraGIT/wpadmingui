package com.wizepass.wpadmingui.userdata;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TreeTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.LinkedList;
import java.util.List;

public abstract class RdnTypeParser {

    private static final String RDN_TYPE = "rdn_type";
    private static final String RDN_VALUE = "rdn_value";
    private static final String CHILDREN = "children";

    public abstract void parseNodeData(final JSONObject jsonObj, final java.util.List<Object> itemList);

    // counter for current itemId to add to TreeList
    private static int currentItemId = 0;
    

    /**
     * Parses the User data tree structure.
     */
    public List<CheckBox> parse(final JSONObject jsonObj, final TreeTable treeTable, final int parentItemId) {

        try {

            // create the item list for adding to TreeTable
            final java.util.List<Object> itemList = new LinkedList<Object>();

            // get Node type
            final String nodeTypeStr = (String) jsonObj.get(RDN_TYPE);
            // change to Uppercase because need to mach with Enum class
            final RdnType nodeType = RdnType.valueOf(nodeTypeStr.toUpperCase());
            final RdnTypeParser nodeParser = nodeType.getParser();
            // create the TreeTable checkbox from RDN_VALUE
            final String nodeTypeValue = (String) jsonObj.get(RDN_VALUE);
            final CheckBox checkBox = new CheckBox(nodeTypeValue);
            // Every node have difference item and need to add checkBox in list 
            itemList.add(checkBox);         
            // parse node specific data, this is depending on RdType
            // sending parameter jsonObj and itemList to Class RdnTypeParser(DC,CN,OU)
            nodeParser.parseNodeData(jsonObj, itemList);
            // add node to TreeTable
            final int thisItemId = currentItemId++;
            treeTable.addItem(itemList.toArray(new Object[itemList.size()]), thisItemId);
            // add item in Object array 
            final Object[] itemArray = itemList.toArray(new Object[itemList.size()]);
            // add item of DC or CN or OU in tree table which position 
            treeTable.addItem(itemArray, thisItemId);
            // 
            if (parentItemId != -1) { // if not the first , when parentItemId incress 
                treeTable.setParent(thisItemId, parentItemId); // set children and parent 
            }
            final List<CheckBox> childrenBoxes = new LinkedList<>();
            // parse all children
            if (jsonObj.containsKey(CHILDREN)) {
                final JSONArray childrenJson = (JSONArray) jsonObj.get(CHILDREN);
                for (int i = 0; i < childrenJson.size(); i++) {
                    final JSONObject child = (JSONObject) childrenJson.get(i);
                    // recursive call of parse
                    final List<CheckBox> childBoxes = parse(child, treeTable, thisItemId);
                    childrenBoxes.addAll(childBoxes);
                }
                final Button testButton = new Button("test");
                checkBox.addValueChangeListener(e -> {
                    for (CheckBox child : childrenBoxes) {
                        child.setValue(checkBox.getValue());
                        testButton.setEnabled(true);             
                    }
                });
            }
            childrenBoxes.add(checkBox);
            return childrenBoxes;    
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("Exception: " + e);
        }
		return null;
    }
}