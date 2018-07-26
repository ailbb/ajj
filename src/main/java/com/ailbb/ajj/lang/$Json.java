package com.ailbb.ajj.lang;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Json {
    public String jsonStr(Object object){
        try {
            return JSONObject.fromObject(object).toString();
        } catch (Exception e) {
            return "{}";
        }
    }

    public String jsonStr(List<Object> map){
        try {
            return JSONArray.fromObject(map).toString();
        } catch (Exception e) {
            return "[]";
        }
    }

}
