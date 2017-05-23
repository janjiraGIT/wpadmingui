package com.wizepass.WpAdminGui.response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import elemental.json.JsonObject;

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
    
    public JSONArray loadDataJsonArrayWithParametrs(final String url, final String parameter)  {
    	try {
    		// TODO: not correct to pass 2 parameters because url is json file 
    		reader = new FileReader(url+parameter);
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
    
    /**
     * Post json body.
     */
    public List<String> postData(final javax.json.JsonObject obj, final String postUrl) throws ClientProtocolException, IOException, ParseException {
        final HttpPost httpPost = new HttpPost(postUrl);
        final String jsonDataStr = obj.toString();
        final HttpClient client = HttpClientBuilder.create().build();
        final List<String> list = new ArrayList<String>();
        StringEntity entity = new StringEntity(jsonDataStr, "UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
        	HttpResponse response = client.execute(httpPost);
        	 int responseCode = response.getStatusLine().getStatusCode();
             System.out.println("Add registrationtoken response: " + responseCode);
             final BufferedReader reader = new BufferedReader(
                     new InputStreamReader(response.getEntity().getContent(),  "UTF-8"));
             String line = "";
             while ((line = reader.readLine()) != null) {
                 list.add(line);
             }
        }catch (Exception e){
        	e.getStackTrace();
        	System.out.println("Could not get any response.There was an error connecting to http://localhost:8081/WpRest/users/registrationtokens");
        }
		return list;       
    }

}
