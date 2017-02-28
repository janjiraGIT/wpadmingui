package com.wizepass.wpadmingui.userdata;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

public class RdnTypeParserCn implements RdnTypeParser {

	public static final String ATTRIBUTES = "attributes";
    public static final String SAMACCOUNTNAME = "sAMAccountName";
    public static final String GIVEN_NAME = "givenName";
    public static final String SN = "sn";

	@Override
	public void parseNodeData(JSONObject jsonObj, Map<String, String> itemMap) {
		  // add given name and surname to item list
        final JSONObject attributes = (JSONObject) jsonObj.get(ATTRIBUTES);
        if (attributes != null) {
            getSaName(itemMap, attributes);
            getGivenName(itemMap, attributes);
            getSn(itemMap, attributes);
        	}  
        }
        private void getSn(final Map<String, String> itemMap, final JSONObject attributes) {
            final JSONArray sn = (JSONArray) attributes.get(SN);
            String snStr = "";
            if (sn != null) {
                for (int i = 0; i < sn.size(); i++) {
                    snStr += sn.get(i);
                }
                itemMap.put(SN, snStr);
            }
        }
        private void getGivenName(final Map<String, String> itemMap, final JSONObject attributes) {
            final JSONArray givenName = (JSONArray) attributes.get(GIVEN_NAME);
            String givenStr = "";
            if (givenName != null) {
                for (int i = 0; i < givenName.size(); i++) {
                    givenStr += givenName.get(i);
                }
                itemMap.put(GIVEN_NAME, givenStr);
            }
        }
        private void getSaName(final Map<String, String> itemMap, final JSONObject attributes) {
            final JSONArray saName = (JSONArray) attributes.get(SAMACCOUNTNAME);
            String saNameStr = "";
            if (saName != null) {
                for (int i = 0; i < saName.size(); i++) {
                    saNameStr += saName.get(i);
                }
                itemMap.put(SAMACCOUNTNAME, saNameStr);
            }
        }
    }