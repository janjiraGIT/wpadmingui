package com.wizepass.wpadmingui.userdata;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpClientRequest {

    private final static Logger logger = Logger.getLogger(HttpClientRequest.class.getName());
    private static Reader reader = null;
    public static JSONObject loadLdapData()  {
    	try {		
    		reader = new FileReader("./test3.json");
    	}catch (IOException e) {
    		logger.log(Level.WARNING, "Error read test3.json", e.getMessage());
    	} 	
    	final JSONParser jsonParser = new JSONParser();
    	JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(reader);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error IOException", e.getMessage());
		} catch (ParseException e) {
			logger.log(Level.WARNING, "Error Parse", e.getMessage());
		}
		return jsonObject;  	
    }

}
