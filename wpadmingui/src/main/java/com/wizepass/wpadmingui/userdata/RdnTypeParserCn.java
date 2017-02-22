package com.wizepass.wpadmingui.userdata;

import org.json.simple.JSONObject;

import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class RdnTypeParserCn extends RdnTypeParser {

    private static final String ATTRIBUTES = "attributes";
    private static final String SAMACCOUNTNAME = "sAMAccountName";
    private static final String GIVEN_NAME = "givenName";
    private static final String SN = "sn";

    @Override
    public void parseNodeData(final JSONObject jsonObj, final List<Object> itemList) {
        // add given name and surname to item list
        final JSONObject attributes = (JSONObject) jsonObj.get(ATTRIBUTES);
        if ( attributes == null){
        	itemList.add(null);
            itemList.add(null);
            itemList.add(null); 
        }
        getSaname(itemList, attributes);      
        getGivenName(itemList, attributes);
        getSn(itemList, attributes);
    }

	private void getSn(final List<Object> itemList, final JSONObject attributes) {
		final JSONArray sn = (JSONArray) attributes.get(SN);
        if (sn == null){
        	itemList.add(null);
        } else 
        for (int i = 0; i < sn.size(); i++){
        	String snStr = "";
        	 snStr += (String) sn.get(i);
        	itemList.add(snStr);
        }
	}

	private void getGivenName(final List<Object> itemList, final JSONObject attributes) {
		final JSONArray givenName = (JSONArray) attributes.get(GIVEN_NAME);
        if ( givenName == null){
        	itemList.add(null);	
        }else
        for (int i = 0; i < givenName.size(); i++){
        	String givenNameStr = "";
        	 givenNameStr += (String) givenName.get(i);
        	itemList.add(givenNameStr);
        }
	}

	private void getSaname(final List<Object> itemList, final JSONObject attributes) {
		final JSONArray saname = (JSONArray) attributes.get(SAMACCOUNTNAME);
        if (saname == null){
        	itemList.add(null);
        }else 
        for (int i = 0; i < saname.size(); i++){
        	String sanameStr = "";
        	sanameStr += saname.get(i);
        	itemList.add(sanameStr);
        }
	}
}