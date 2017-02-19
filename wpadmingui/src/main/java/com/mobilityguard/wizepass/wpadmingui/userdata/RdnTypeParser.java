package com.mobilityguard.wizepass.wpadmingui.userdata;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TreeTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
     * Change this to call the UserData API instead of loading file.
     * @return JSONObject
     */
    public JSONObject loadLdapData() throws IOException, ParseException {
        final Reader reader = new FileReader("./test2.json");
        final JSONParser jsonParser = new JSONParser();
        final JSONObject jsonObj = (JSONObject) jsonParser.parse(reader);
        return jsonObj;
    }
    
    // need to change from read file to read from get Rest url instade // 
    public JSONObject readURL() throws IOException, ParseException {
    	// http://10.104.14.7:8888/UserDbApi1/rest/users
    	final String restURL = ("http://validate.jsontest.com/?json=%7B%22key%22:%22value%22%7D");
    	final URL url = new URL(restURL);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    	final JSONParser jsonP = new JSONParser();
    	final JSONObject jsonO = (JSONObject) jsonP.parse(reader);
		return jsonO;
    	
    }

    /**
     * Parses the User data tree structure.
     */
    public void parse(final JSONObject jsonObj, final TreeTable treeTable, final int parentItemId) {

        try {

            // create the item list for adding to TreeTable
            final java.util.List<Object> itemList = new LinkedList<Object>();

            // get Node type
            final String nodeTypeStr = (String) jsonObj.get(RDN_TYPE);
            final RdnType nodeType = RdnType.valueOf(nodeTypeStr.toUpperCase());
            final RdnTypeParser nodeParser = nodeType.getParser();
            // create the TreeTable checkbox from RDN_VALUE
            final String nodeTypeValue = (String) jsonObj.get(RDN_VALUE);
            final CheckBox checkBox = new CheckBox(nodeTypeValue);
            itemList.add(checkBox);         
            // parse node specifc data, this is depending on RdType
            nodeParser.parseNodeData(jsonObj, itemList);
            // add node to TreeTable
            final int thisItemId = currentItemId++;
            ArrayList<Integer> xx = new ArrayList<Integer>();
            xx.add(currentItemId);
            System.out.println(xx);
            
            if ( thisItemId == 0 ){
            	// if checkbox thisItemId == 0 , checkbox > 0 will check 
            	// if checkbox thisItemId == 1 , checkbox > 1 will check
            	// if checkbox thisItemId == 2 , checkbox > 2 will check       
            	checkBox.setValue(true);
            }
            treeTable.addItem(itemList.toArray(new Object[itemList.size()]), thisItemId);
            final Object[] itemArray = itemList.toArray(new Object[itemList.size()]);
            treeTable.addItem(itemArray, thisItemId);
            if (parentItemId != -1) {
                treeTable.setParent(thisItemId, parentItemId);
            }
            // parse all children
            if (jsonObj.containsKey(CHILDREN)) {
                final JSONArray childrenJson = (JSONArray) jsonObj.get(CHILDREN);
                for (int i = 0; i < childrenJson.size(); i++) {
                    final JSONObject child = (JSONObject) childrenJson.get(i);
                    // recursive call of parse
                    this.parse(child, treeTable, thisItemId);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
    }
}