package com.wizepass.WpAdminGui.util;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;


import org.apache.http.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wizepass.WpAdminGui.gui.RegistationTokenTab;

public class JsonUtil {
    public static final String DESCRIPTION = "description";
    public static final String REGISTRATION_CODE = "registration_code";
    public static final String DATE = "date";
    public static final String PROVIDER_ID = "provider_id";
    public static final String END_USER_DNS = "end_user_dns";
    public static final String USER_DB_ID = "userdb_id";
    public static final String RT_VALID_DURATION = "rt_valid_duration";
    public static final String CERT_PROFILE_ID = "cert_profile_id";
    private static final String CUSTOMER_ID = "customer_id";
    private String gvname;
    private String surname;
    private String aname;
    private JsonObject jsonObj= null;
    private final Logger logger = Logger.getLogger(JsonUtil.class.getName());

    /**
     * Build Json body for send a request to add registration tokens.
     **/
	public JsonObject  buildJsonObject(final String randomId, final String date,final String customer, final String userDb,final List<String> listOfDns,
												            final String descriptionValueStr, final String timeValueStr,
												            final String certProfileStr, final String providerStr, final int userSelected ) {
				//final JsonArrayBuilder jarray = buildJsonArrayFromList(listOfDns);
				final JsonObject jsonObj = Json.createObjectBuilder()
														.add(REGISTRATION_CODE, randomId)
														.add(DATE, date)
												        .add(DESCRIPTION, descriptionValueStr)
												        .add(CUSTOMER_ID, customer)
												        .add(PROVIDER_ID, providerStr)
												       // .add(END_USER_DNS, jarray)
												        .add(USER_DB_ID, userDb)
												        .add(RT_VALID_DURATION, timeValueStr)
												      //  .add(ENROLMENT_TYPE, "admin")
												        .add(CERT_PROFILE_ID,certProfileStr)
												        .build();
				return jsonObj;	
	}
	
    /**
     * Build JSON Object from json array.
     **/
	public JsonObject  buildJsonObjectFromSearchingUser(final JSONArray jsonArray ) {
		for (int i = 0; i < jsonArray.size(); i++) {
			final JSONObject jsonObj = (JSONObject) jsonArray.get(i);			
			final JSONObject attributes = (JSONObject) jsonObj.get("attributes");
			aname = (String) attributes.get("account_name");
			gvname = (String)attributes.get("given_name");
			surname =(String)attributes.get("sur_name");
		}	
			jsonObj = (JsonObject)Json.createObjectBuilder()
					.add("account_name", aname)
					.add("given_name",gvname )
					.add("sur_name",surname)
					.build();
		
		return jsonObj;	
	}
	/**
     * crate JsonArry from List.
     **/
    public JsonArrayBuilder buildJsonArrayFromList(final List<String> listOfDns) {
        final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (int i = 0 ; i < listOfDns.size(); i ++) {
            jsonArrayBuilder.add(listOfDns.get(i));
        }
        return jsonArrayBuilder;
    }
    
    /**
     * return Json in pretty and print.
     **/
    public String createJsonPrettyPrint(final JsonObject jsonObj) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonObj.toString());
        String prettyJsonStr = gson.toJson(je);
        return prettyJsonStr;
    }

    /**
     * Convert from String to Json Object.
     * @throws org.json.simple.parser.ParseException 
     */
    public JSONObject createJsonObject(final String str) throws ParseException, org.json.simple.parser.ParseException {
        final JSONParser parser = new JSONParser();
        final JSONObject jsonObj = (JSONObject) parser.parse(str);
        return jsonObj;
    }

    /**
     * Convert from List to String.
     */
    public String parseListToJsonObj(final List<String> list) {
        final Gson gs = new Gson();
        String listStr = gs.toJson(list);
        return listStr;
    }

}
