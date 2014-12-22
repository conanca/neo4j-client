package com.xinhuanet.commons.neo4j.lang;

import com.alibaba.fastjson.JSON;

import java.util.Map;


public class JsonUtils {

    public static String getVale(String json, String name) {
        if (!Strings.isEmpty(json)) {
            Map map = JSON.parseObject(json, Map.class);
            if (map != null) {
                Object valObj = map.get(name);
                if (valObj != null) {
                    return valObj.toString();
                }
            }
        }
        return null;
    }

}
