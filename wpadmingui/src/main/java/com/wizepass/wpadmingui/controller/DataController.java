package com.wizepass.wpadmingui.controller;

import com.wizepass.wpadmingui.response.RestApiResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataController {
    private static final String ALL_USERS_LABB3 = "./all_users_labb3.json";
    private static final String ALL_USERS_MGAD = "./all_user_mgad.json"; // there is no json 
    private static final String CUSTOMERS = "./customer.json";
    private static final String DB_GSKOLAN = "./db_gskolan.json";
    private static final String DB_MGAB = "./db_mgab.json";
    private static final String PROVIDER = "./provider.json";
    private static final String CERT_PROFILE = "./cert_profile.json";
    private final RestApiResponse httpClient = new RestApiResponse();
    private final List<String> timeList = new ArrayList<>();
    private JSONObject jsonObj;
    private JSONArray customers;
    private JSONArray dbMgab;
    private JSONArray provider;
    private JSONArray certProfile;
    private JSONArray dbGskolan;
    

    /**
     * list of all users.
     */
    public JSONObject getAllUserLabb3() {
    	try {
            jsonObj  = httpClient.loadLdapData(ALL_USERS_LABB3);
        }   catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getAllUserLabb3 method is not correct. Check url again ");
        }
        return jsonObj;
    }
    
    /**
     * list of all users in Mgad.
     */
    // TODO : list is empty.
    public JSONObject getAllUserMgad() {
        try {
            jsonObj  = httpClient.loadLdapData(ALL_USERS_MGAD);
        }   catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getAllUserMgad method is not correct. Check url again ");
        }
        return jsonObj;
    }

    /**
     * list of Customers.
     */
    public JSONArray getCustomers() {
        try {
            customers = httpClient.getJsonArray(CUSTOMERS);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getCustomer method is not correct. Check url again. ");
        }
        return customers;
    }

    /**
     * list of Mgab db.
     */
    public JSONArray getMgabDb() {
        try {
            dbMgab = httpClient.getJsonArray(DB_MGAB);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getMgabDb method is not correct. Check url again. ");
        }
        return dbMgab;
    }

    /**
     * list of Granskolan db.
     */
    public JSONArray getGranskolanDb() {
        try {
            dbGskolan = httpClient.getJsonArray(DB_GSKOLAN);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getGskolan method is not correct. Check url again. ");
        }
        return dbGskolan;
    }

    /**
     * list of Provider mgab.
     */
    public JSONArray getProvider() {
        try {
            provider = httpClient.getJsonArray(PROVIDER);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getProvider method is not correct. Check url again. ");
        }
        return provider;
    }

    /**
     * list of Cert profile mgab.
     */
    public JSONArray getCertProfile() {
        try {
            certProfile = httpClient.getJsonArray(CERT_PROFILE);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getCertProfile method is not correct. Check url again. ");
        }
        return certProfile;
    }

    /**
     * time list.
     */
    public List<String> getTimeList() {
        timeList.add("P10m");//minutes
        timeList.add("P1h");//hours
        timeList.add("P1D");//day
        timeList.add("P1W");//week
        timeList.add("P1M");//Month
        return timeList;
    }

}