package com.wizepass.WpAdminGui.controller;


import com.wizepass.WpAdminGui.response.RestApiResponse;
import com.wizepass.WpAdminGui.util.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataController {
	private final Logger logger = Logger.getLogger(DataController.class.getName());
    private final RestApiResponse restResponse = new RestApiResponse();
    private final List<String> timeList = new ArrayList<>();
    private JSONObject jsonObj;
    private JSONArray customers;
    private JSONArray dbMgab;
    private JSONArray provider;
    private JSONArray certProfile;
    private JSONArray dbGskolan;
    private JSONArray regTokens;
    private JSONArray mgUsersPara;
	private JSONObject userSearch = null;
    

    /**
     * list of all users.
     */
    public JSONObject getAllUserLabb3() {
    	try {
            jsonObj  = restResponse.loadLdapData(Constants.ALL_USERS_LABB3);
        }   catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
            
        }
        return jsonObj;
    }
    
    /**
     * list of all users.
     */
    public JSONObject getTest() {
    	try {
            jsonObj  = restResponse.loadLdapData(Constants.TEST);
        }   catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return jsonObj;
    }
    
    /**
     * list of all users in Mgad.
     */
    // TODO : list is empty.
    public JSONObject getAllUserMgad() {
        try {
            jsonObj  = restResponse.loadLdapData(Constants.ALL_USERS_MGAD);
        }   catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return jsonObj;
    }

    /**
     * list of Customers.
     */
    public JSONArray getCustomers() {
        try {
            customers = restResponse.getJsonArray(Constants.CUSTOMERS);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return customers;
    }

    /**
     * list of Mgab db.
     */
    public JSONArray getMgabDb() {
        try {
            dbMgab = restResponse.getJsonArray(Constants.DB_MGAB);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return dbMgab;
    }

    /**
     * list of Granskolan db.
     */
    public JSONArray getAnviDb() {
        try {
            dbGskolan = restResponse.getJsonArray(Constants.DB_ANVI);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return dbGskolan;
    }

    /**
     * list of Provider mgab.
     */
    public JSONArray getProvider() {
        try {
            provider = restResponse.getJsonArray(Constants.PROVIDER);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return provider;
    }

    /**
     * list of Cert profile mgab.
     */
    public JSONArray getCertProfile() {
        try {
            certProfile = restResponse.getJsonArray(Constants.CERT_PROFILE);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
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
    /**
     * list of Users.
     */
    public JSONArray getRegistrationtokens() {
        try {
        	regTokens = restResponse.getJsonArrayRegTokens(Constants.URL_ADDRESS, Constants.WP_REST,Constants.REG_TOKENS);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return regTokens;
    }


    /**
     * list of Users.
     */
    public JSONArray getMgUsers(final String parameter) {
        try {
        	mgUsersPara = restResponse.getJsonArray(Constants.USER_MG);
        } catch (Exception e) {
        	logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
        }
        return mgUsersPara;
    }
    
    public JSONObject searchUser(final String name){
    	//JSONObject userSearch = null;
    	try {
    		userSearch = restResponse.findUser(Constants.USER_SEARCH, name);
    	}catch (Exception e){
    		logger.log(Level.WARNING,"Something went wrong check url again", e.getStackTrace());
    	}
		return userSearch;
    	
    }
    

}