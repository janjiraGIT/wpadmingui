package com.mobilityguard.wizepass.wpadmingui.userdata;

import org.json.simple.JSONObject;

import java.util.List;

public class RdnTypePaserCn extends RdnTypeParser {

	  private static final String ATTRIBUTES = "attributes";
	    private static final String DN = "dn";
	    private static final String GIVEN_NAME = "givenName";
	    private static final String SN = "sn";

    @Override
    public void parseNodeData(final JSONObject jsonObj, final List<Object> itemList) {
        // add given name and surname to item list
    	 final JSONObject attributes = (JSONObject) jsonObj.get(ATTRIBUTES);
         final String dn = (String) attributes.get(DN);
         final String givenName = (String) attributes.get(GIVEN_NAME);
         final String sn = (String) attributes.get(SN);
         itemList.add(dn);
         itemList.add(givenName);
         itemList.add(sn);
    }

}