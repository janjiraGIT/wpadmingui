package com.wizepass.wpadmingui.controller;

import com.wizepass.wpadmingui.response.RestApiResponse;
import com.wizepass.wpadmingui.util.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DataController {
	private static final Logger log = LoggerFactory.getLogger(DataController.class);
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
            jsonObj  = httpClient.loadLdapData(Constants.ALL_USERS_LABB3);
        }   catch (Exception e) {
            e.getMessage();
            log.error("DataController class , getAllUserLabb3 method is not correct. Check url again");
        }
        return jsonObj;
    }
    
    /**
     * list of all users in Mgad.
     */
    // TODO : list is empty.
    public JSONObject getAllUserMgad() {
        try {
            jsonObj  = httpClient.loadLdapData(Constants.ALL_USERS_MGAD);
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
            customers = httpClient.getJsonArray(Constants.CUSTOMERS);
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
            dbMgab = httpClient.getJsonArray(Constants.DB_MGAB);
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
            dbGskolan = httpClient.getJsonArray(Constants.DB_GSKOLAN);
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
            provider = httpClient.getJsonArray(Constants.PROVIDER);
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
            certProfile = httpClient.getJsonArray(Constants.CERT_PROFILE);
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