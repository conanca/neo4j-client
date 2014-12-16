package com.xinhuanet.commons.neo4j.lang;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by conanca on 14-12-16.
 */
public class JsonUtils {
    public static <T> T getVale(String json,Class <T> clazz,String name){
        if (!Strings.isEmpty(json)) {
            Map map = JSON.parseObject(json, Map.class);
            if(map!=null){
                Object valObj= map.get(name);
                if(valObj!=null){
                    String valJson = valObj.toString();
                    if(!Strings.isEmpty(valJson)){
                        if(valJson instanceof CharSequence){
                            return (T)valJson;
                        }else{
                            return JSON.parseObject(valJson,clazz) ;
                        }
                    }
                }
            }
        }
        return null;
    }

}
