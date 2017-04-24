package com.wizepass.WpAdminGui.response;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestApiResponse {

    private final static Logger logger = Logger.getLogger(RestApiResponse.class.getName());
    private static Reader reader = null;
    private static JSONObject jsonObject = null;
    private static JSONArray jsonArray = null;
    
    public JSONObject loadLdapData(final String url)  {
    	try {		
    		reader = new FileReader(url);
    	}catch (IOException e) {
    		logger.log(Level.WARNING, "Error read file", e.getMessage());
    	} 	
    	final JSONParser jsonParser = new JSONParser();
		try {
			jsonObject = (JSONObject) jsonParser.parse(reader);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error IOException", e.getMessage());
		} catch (ParseException e) {
			logger.log(Level.WARNING, "Error Parse", e.getMessage());
		}
		return jsonObject;  	
    }
    
    public JSONArray getJsonArray(final String url)  {
    	try {		
    		reader = new FileReader(url);
    	}catch (IOException e) {
    		logger.log(Level.WARNING, "Error read file", e.getMessage());
    	} 	
    	final JSONParser jsonParser = new JSONParser();
		try {
			jsonArray = (JSONArray) jsonParser.parse(reader);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error IOException", e.getMessage());
		} catch (ParseException e) {
			logger.log(Level.WARNING, "Error Parse", e.getMessage());
		}
		return jsonArray;  	
    }

}
