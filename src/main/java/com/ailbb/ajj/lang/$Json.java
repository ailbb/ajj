package com.ailbb.ajj.lang;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Json {
    public String parseJsonString(Object object){
        try {
            return JSONObject.fromObject(object).toString();
        } catch (Exception e) {
            return "{}";
        }
    }

    public String parseJsonString(List<Object> map){
        try {
            return JSONArray.fromObject(map).toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    public Map<String, Object> parseJsonObject(Object object){
        try {
            return JSONObject.fromObject(object);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public List<Object> parseJsonArray(Object object){
        try {
            return JSONArray.fromObject(object);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
