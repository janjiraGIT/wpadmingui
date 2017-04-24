package com.wizepass.WpAdminGui.userdata;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

public class RdnTypeParserCn implements RdnTypeParser {

    public static final String ATTRIBUTES = "attributes";
    public static final String SAMACCOUNTNAME = "sAMAccountName";
    public static final String GIVEN_NAME = "givenName";
    public static final String SN = "sn";
    public static final String DNS = "distinguishedName";
    public static final String OBJECT_CLASS = "object_class";

    @Override
    public void parseNodeData(final JSONObject jsonObj, final Map<String, String> itemMap) {
        // add given name surname and dns to item list
        final JSONObject attributes = (JSONObject) jsonObj.get(ATTRIBUTES);
        if (attributes != null) {
            getSaName(itemMap, attributes);
            getGivenName(itemMap, attributes);
            getSn(itemMap, attributes);
            getDns(itemMap, attributes);
//            getObjClass(itemMap, attributes);
        }
    }

//    private void getObjClass(final Map<String, String> itemMap, final JSONObject attributes) {
//        final JSONObject objectClass = (JSONObject) attributes.get(OBJECT_CLASS);
//        if (OBJECT_CLASS != null ) {
//            String objClass = "" ;
//            objClass += objectClass.toString();
//           // itemMap.put(OBJECT_CLASS, objClass);
//        }
//        System.out.println("XXX");
//    }

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

    private void getDns(final Map<String, String> itemMap, final JSONObject attributes) {
        final JSONArray dNs = (JSONArray) attributes.get(DNS);
        String dnsStr = "";
        if (dNs != null) {
            for (int i = 0; i < dNs.size(); i++) {
                dnsStr += dNs.get(i);
            }
            itemMap.put(DNS, dnsStr);
        }
    }
}