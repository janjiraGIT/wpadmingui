package com.wizepass.WpAdminGui.userdata;

import java.util.Map;

import org.json.simple.JSONObject;

public class RdnTypeParserNone implements RdnTypeParser{
	public static final String ACCOUNT_NAME = "account_name";
	public static final String GIVEN_NAME = "given_name";
	public static final String SUR_NAME = "sur_name";

	@Override
	public void parseNodeData(JSONObject jsonObj, Map<String, String> itemMap) {
		final JSONObject samn = (JSONObject) jsonObj.get(ACCOUNT_NAME);
		final JSONObject gn = (JSONObject) jsonObj.get(GIVEN_NAME);
		final JSONObject sn = (JSONObject) jsonObj.get(SUR_NAME);
		if ( samn != null ){
			itemMap.put(ACCOUNT_NAME, samn.toJSONString());
			
		}
		if ( gn != null ){
			itemMap.put(GIVEN_NAME, gn.toJSONString());
			
		}
		if ( sn != null ){
			itemMap.put(SUR_NAME, sn.toJSONString());	
		}		
	}
	
}
