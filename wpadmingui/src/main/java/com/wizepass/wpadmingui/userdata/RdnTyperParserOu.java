package com.wizepass.wpadmingui.userdata;

import org.json.simple.JSONObject;

import java.util.List;

public class RdnTyperParserOu extends RdnTypeParser {

    @Override
    public void parseNodeData(final JSONObject jsonObj, final List<Object> itemList) {
        itemList.add(null);
        itemList.add(null);
        itemList.add(null);
    }

}