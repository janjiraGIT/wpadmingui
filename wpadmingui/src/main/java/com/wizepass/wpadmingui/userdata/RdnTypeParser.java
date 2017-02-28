package com.wizepass.wpadmingui.userdata;

import java.util.Map;

import org.json.simple.JSONObject;

public interface RdnTypeParser {
	public void parseNodeData(final JSONObject jsonObj, final Map<String, String> itemMap);
}
