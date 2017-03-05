package com.wizepass.wpadmingui.userdata;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TreeTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TreeTableFactory {

    private static final String RDN_TYPE_STR = "rdn_type";
    private static final String RDN_VALUE = "rdn_value";
    private static final String CHILDREN = "children";

    private TreeTable treeTable = new TreeTable();

    // counter for current itemId to add to TreeList
    private int nextItemId = 0;

    // connects a TreeTable Id with a person attributes, used for generating Wizepass cert requests.
    private Map<Integer, Map<String, String>> persons = new HashMap<>();
    private Map<Integer, Map<String, String>> tmpPersons;

    /**
     * Creates a TreeTable of user database data (LDAP).
     */
    public void createTreeTable(final JSONObject jsonObj) throws InstantiationException, IllegalAccessException {
        final TreeTable tmpTreeTable = new TreeTable();
        tmpTreeTable.addContainerProperty("LDAP Tree", CheckBox.class, "");
        tmpTreeTable.addContainerProperty("Given Name", String.class, null);
        tmpTreeTable.addContainerProperty("Surname", String.class, null);

        this.nextItemId = 0;
        this.tmpPersons = new HashMap<>();

        // create root elem
        final Map<String, String> rootAttributes = new LinkedHashMap<String, String>();
        final int rootItemId = this.nextItemId++;

        // get Node type
        final String nodeTypeStr = (String) jsonObj.get(RDN_TYPE_STR);
        final RdnType nodeType = RdnType.valueOf(nodeTypeStr.toUpperCase());
        rootAttributes.put(RDN_TYPE_STR, nodeTypeStr);
        final RdnTypeParser nodeParser = nodeType.getParser();
        nodeParser.parseNodeData(jsonObj, rootAttributes);

        final String nodeTypeValue = (String) jsonObj.get(RDN_VALUE);
        rootAttributes.put(RDN_VALUE, nodeTypeValue);

        // add Items to Treeview
        Object[] rootItems = addNodeAttributes(rootAttributes, rootItemId);
        // ttable.addItem(new Object[]{"Root"}, 0); // only one obj
        tmpTreeTable.addItem(rootItems, rootItemId); // {Checkbox,null,null }, 0 

        // parse children
        final List<CheckBox> rootChildrenCheckBoxes =
                this.parseChildren(tmpTreeTable, jsonObj, rootItemId);

        // First item is always the checkbox!
        final CheckBox rootCheckBox = (CheckBox) rootItems[0];
        rootCheckBox.setImmediate(true);
        rootCheckBox.addValueChangeListener(e -> {
            for (CheckBox child : rootChildrenCheckBoxes) {
                child.setValue(rootCheckBox.getValue());
            }
        });

        // reload of TreeTable done, set TreeTable pointers
        this.persons = tmpPersons;
        this.treeTable = tmpTreeTable;
    }

    public TreeTable getTreeTable() {
        return this.treeTable;
    }

    public Map<Integer, Map<String, String>> getPersons() {
        return this.persons;
    }

    private List<CheckBox> parseChildren(final TreeTable tmpTreeTable, final JSONObject jsonObj,
            final int parentId) throws InstantiationException, IllegalAccessException {
        final List<CheckBox> childrenBoxes = new LinkedList<>();

        if (jsonObj.containsKey(CHILDREN)) {
            final JSONArray childrenJson = (JSONArray) jsonObj.get(CHILDREN);
            for (int i = 0; i < childrenJson.size(); i++) {
                final JSONObject childJson = (JSONObject) childrenJson.get(i);

                // create node Attributes
                final Map<String, String> nodeAttributes = new LinkedHashMap<String, String>();
                final int nodeItemId = this.nextItemId++;

                // get Node type
                final String nodeTypeStr = (String) childJson.get(RDN_TYPE_STR);
                final RdnType nodeType = RdnType.valueOf(nodeTypeStr.toUpperCase());
                final RdnTypeParser nodeParser = nodeType.getParser();
                nodeAttributes.put(RDN_TYPE_STR, nodeTypeStr);
                nodeParser.parseNodeData(childJson, nodeAttributes);

                final String nodeTypeValue = (String) childJson.get(RDN_VALUE);
                nodeAttributes.put(RDN_VALUE, nodeTypeValue);

                // add Items to Treeview
                final Object[] nodeItems = addNodeAttributes(nodeAttributes, nodeItemId);
                tmpTreeTable.addItem(nodeItems, nodeItemId);
                tmpTreeTable.setParent(nodeItemId, parentId);

                final List<CheckBox> childBoxes = this.parseChildren(tmpTreeTable, childJson, nodeItemId);

                final CheckBox nodeCheckBox = (CheckBox) nodeItems[0];
                nodeCheckBox.setImmediate(true);
                if (childBoxes.size() > 0) {
                    nodeCheckBox.addValueChangeListener(e -> {
                        for (CheckBox childCheckBox : childBoxes) {
                            childCheckBox.setValue(nodeCheckBox.getValue());
                        }
                    });
                } else {
                    // This node is an end node in tree.
                    treeTable.setChildrenAllowed(nodeItemId, false);
                }

                // add child check boxes to complete list of all checkboxes for this tree node
                childrenBoxes.add(nodeCheckBox);
                childrenBoxes.addAll(childBoxes);
            }
        }

        return childrenBoxes;
    }

    private Object[] addNodeAttributes(final Map<String, String> nodeAttributes, final int nodeId) {

        // create the item list for adding to TreeTable
        final java.util.List<Object> itemList = new LinkedList<Object>();

        // Add checkbox with value
        if (nodeAttributes.containsKey(RDN_VALUE)) {
        	// CheckBox checkBox = new CheckBox("Check1");
            final CheckBox checkBox = new CheckBox(nodeAttributes.get(RDN_VALUE));
            itemList.add(checkBox);
        } else {
            itemList.add(null);
        }

        // Add Given Name
        if (nodeAttributes.containsKey(RdnTypeParserCn.GIVEN_NAME)) {
            itemList.add(nodeAttributes.get(RdnTypeParserCn.GIVEN_NAME));
        } else {
            itemList.add(null);
        }

        // Add Surname
        if (nodeAttributes.containsKey(RdnTypeParserCn.SN)) {
            itemList.add(nodeAttributes.get(RdnTypeParserCn.SN));
        } else {
            itemList.add(null);
        }

        // add nodeAttributes to persons if the type is correct (ObjectType == person or similar)
// TODO !!! Now its not CORRECT
        final String nodeTypeStr = nodeAttributes.get(RDN_TYPE_STR).toUpperCase();
        if (RdnType.valueOf(nodeTypeStr) == RdnType.CN) {
            tmpPersons.put(nodeId, nodeAttributes);
        }

        return itemList.toArray(new Object[itemList.size()]);
    }
}