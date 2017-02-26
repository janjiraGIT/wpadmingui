package com.wizepass.wpadmingui.userdata;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpClientRequest {
    public static JSONObject loadLdapData() throws IOException, ParseException {
    	// http://10.104.14.7:8888/UserDbApi1/rest/users
    	final Reader reader = new FileReader("./test3.json");
    	final JSONParser jsonParser = new JSONParser();
    	final JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
		return jsonObject;  	
    }

}
