package com.wizepass.WpAdminGui.controller;


import com.wizepass.WpAdminGui.response.RestApiResponse;
import com.wizepass.WpAdminGui.util.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataController {
	//public static final Logger log = (Logger) LoggerFactory.getLogger(DataController.class);
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
            e.getMessage();
           // log.error("DataController class , getAllUserLabb3 method is not correct. Check url again");
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
            e.getMessage();
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
            customers = restResponse.getJsonArray(Constants.CUSTOMERS);
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
            dbMgab = restResponse.getJsonArray(Constants.DB_MGAB);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getMgabDb method is not correct. Check url again. ");
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
            provider = restResponse.getJsonArray(Constants.PROVIDER);
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
            certProfile = restResponse.getJsonArray(Constants.CERT_PROFILE);
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
    /**
     * list of Users.
     */
    public JSONArray getRegistrationtokens() {
        try {
        	regTokens = restResponse.getJsonArray(Constants.REG_TOKEN);
        } catch (Exception e) {
            e.getMessage();
            System.err.println("WARNING: DataController class , getCertProfile method is not correct. Check url again. ");
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
            e.getMessage();
            System.err.println("WARNING: DataController class , getCertProfile method is not correct. Check url again. ");
        }
        return mgUsersPara;
    }
    
    public JSONObject searchUser(final String name){
    	//JSONObject userSearch = null;
    	try {
    		userSearch = restResponse.findUser(Constants.USER_SEARCH, name);
    	}catch (Exception e){
    		System.err.println("WARNING: DataController class , searchUser method is not correct. Check url again. ");
    	}
		return userSearch;
    	
    }
    

}