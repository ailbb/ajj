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
    public String toJsonString(Object object){
        return JSONObject.fromObject(object).toString();
    }

    public String toJsonString(List<Object> map){
        return JSONArray.fromObject(map).toString();
    }

    public Map<String, Object> toJsonObject(Object object){
        return JSONObject.fromObject(object);
    }

    public List<Object> toJsonArray(Object object){
        return JSONArray.fromObject(object);
    }

    public Object toBean(Object object, Class c){
        return JSONObject.toBean(JSONObject.fromObject(object), c);
    }

}
