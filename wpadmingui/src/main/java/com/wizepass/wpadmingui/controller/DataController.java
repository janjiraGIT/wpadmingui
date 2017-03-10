package com.wizepass.wpadmingui.controller;

import com.wizepass.wpadmingui.response.RestApiResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataController {
    private static final String ALL_USERS = "./user.json";
    private static final String CUSTOMERS = "./customer.json";
    private static final String DB = "./userdb.json";
    private static final String PROVIDER = "./provider.json";
    private static final String CERT_PROFILE = "./certprofile.json";
    private final RestApiResponse httpClient = new RestApiResponse();
    private final List<String> timeList = new ArrayList<>();

    /**
     * list of all users.
     */
    public JSONObject getAllUser() {
        final JSONObject jsonObj = httpClient.loadLdapData(ALL_USERS);
        return jsonObj;
    }

    /**
     * list of customers.
     */
    public JSONArray getCustomer() {
        final JSONArray customers = httpClient.getJsonArray(CUSTOMERS);
        return customers;
    }

    /**
     * list of db.
     */
    public JSONArray getDb() {
        final JSONArray db = httpClient.getJsonArray(DB);
        return db;
    }

    /**
     * list of provider.
     */
    public JSONArray getProvider() {
        final JSONArray provider = httpClient.getJsonArray(PROVIDER);
        return provider;
    }

    /**
     * time list.
     */
    public List<String> getTimeList() {
        timeList.add("10m");//minutes
        timeList.add("1h");//hours
        timeList.add("1d");//day
        timeList.add("1w");//week
        timeList.add("1M");//Month
        return timeList;
    }

    /**
     * profile list.
     */
    public JSONArray getCertProfile() {
        final JSONArray certProfile = httpClient.getJsonArray(CERT_PROFILE);
        return certProfile;
    }

}