package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Json {
    public String toJsonString(Object object){
        return toJsonObject(object).toString();
    }

    public String toJsonString(List<Object> map){
        return toJsonArray(map).toString();
    }

    public Map<String, Object> toJsonObject(Object object){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());

        try {
            return JSONObject.fromObject(object);
        } catch (Exception e) {
            $.warn(e);
            return JSONObject.fromObject(object, jsonConfig);
        }
    }

    public List<Object> toJsonArray(Object object){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());

        try {
            return JSONArray.fromObject(object);
        } catch (Exception e) {
            $.warn(e);
            return JSONArray.fromObject(object, jsonConfig);
        }
    }

    public Object toBean(Object object, Class c){
        return JSONObject.toBean(JSONObject.fromObject(object), c);
    }

}
