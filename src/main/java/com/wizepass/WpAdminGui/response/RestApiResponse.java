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

import javax.json.JsonObject;

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

public class RestApiResponse {

    private final Logger logger = Logger.getLogger(RestApiResponse.class.getName());
    private static Reader reader = null;
    private static JSONObject jsonObject = null;
    private static JSONArray jsonArray = null;
    private static HttpClient client = HttpClientBuilder.create().build();
    
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
    
//  public static  String URL_ADDRESS = "http://localhost:8081";
//  public static String WP_REST = "/WpRest/users/";
//  public static final String REG_TOKENS= "registrationtokens";
    
    public JSONArray getJsonArrayRegTokens(final String url_address, final String wp_rest, final String reg_token)  {
    	try {
            final HttpGet requestGet = new HttpGet(url_address + wp_rest+reg_token);
            final HttpResponse response = client.execute(requestGet);
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            final JSONParser jsonParser = new JSONParser();
            jsonArray  = (JSONArray) jsonParser.parse(reader);
        } catch (IOException e) {
            logger.log(Level.WARNING,"loadDataJsonArrayWithParamers method, IOException, couldn't read the file ", e.getMessage());
        } catch (ParseException e) {
        	 logger.log(Level.WARNING,"loadDataJsonArrayWithParamers method, ParseException, couldn't read the file ", e.getMessage());
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
    public List<String> postData(final JsonObject obj, final String postUrl) throws ClientProtocolException, IOException, ParseException {
        
    	final HttpPost httpPost = new HttpPost(postUrl);
        final String jsonDataStr = obj.toString();
        final List<String> list = new ArrayList<String>();
        StringEntity entity = new StringEntity(jsonDataStr, "UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
        	HttpResponse response = client.execute(httpPost);
        	 int responseCode = response.getStatusLine().getStatusCode();
             logger.log(Level.INFO, "Add registrationtoken response", responseCode);
             final BufferedReader reader = new BufferedReader(
                     new InputStreamReader(response.getEntity().getContent(),  "UTF-8"));
             String line = "";
             while ((line = reader.readLine()) != null) {
                 list.add(line);
                 logger.log(Level.INFO, line);
             }
        }catch (Exception e){
        	e.getStackTrace();
        	logger.log(Level.WARNING, "Could not get any response.There was an error connecting to http://localhost:8081/WpRest/users/registrationtokens",e.getStackTrace());
        }
		return list;       
    }
    
    public JSONArray findUser(final String url, final String user) throws ClientProtocolException, IOException{
    	JSONArray jsonArray = null;
    	try {
            final HttpGet requestGet = new HttpGet(url + user);
            final HttpResponse response = client.execute(requestGet);
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            final JSONParser jsonParser = new JSONParser();
            
            jsonArray  = (JSONArray) jsonParser.parse(reader);
        } catch (IOException e) {
            logger.log(Level.WARNING,"Couldn't read the file, check Rest api server again ", e.getStackTrace());
        } catch (ParseException e) {
        	 logger.log(Level.WARNING,"ParseException error ", e.getStackTrace());
        }
        return jsonArray;
    }
 }
