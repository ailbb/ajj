package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;
import net.sf.json.*;
import net.sf.json.processors.JsDateJsonBeanProcessor;
import net.sf.json.util.PropertyFilter;

import java.util.List;
import java.util.Map;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Json {
    private boolean skipNull = false;
    private JsonConfig jsonConfig;
    private PropertyFilter propertyFilter;

    public String toJsonString(Object object){
        return toJsonObject(object).toString();
    }

    public String toJsonString(List<Object> map){
        return toJsonArray(map).toString();
    }

    public Map<String,Object> toJsonMap(Object object){
        return toJsonObject(object);
    }

    public Object toJsonObject(Object object, Object defaultValue){
        try {
            return toJsonObject(object);
        } catch (Exception e) {
            $.warn(e);
            return defaultValue;
        }
    }

    public Object tryToJsonObject(Object object){
        return toJsonObject(object, object);
    }

    public JSONObject toJsonObject(Object object){
        try {
            return JSONObject.fromObject(object, getJsonConfig());
        } catch (Exception e) {
            try {
                return JSONObject.fromObject(object);
            } catch (Exception e1) {
                $.warn(e);
                throw e1;
            }
        }
    }

    public List<Object> toJsonArray(Object object){
        try {
            return JSONArray.fromObject(object, getJsonConfig());
        } catch (Exception e) {
            try {
                return JSONArray.fromObject(object);
            } catch (Exception e1) {
                $.warn(e);
                throw e1;
            }
        }
    }

    public Object toBean(Object object, Class c){
        return JSONObject.toBean(JSONObject.fromObject(object), c);
    }

    private JsonConfig getJsonConfig(){
        if($.isEmptyOrNull(jsonConfig)) {
            jsonConfig = new JsonConfig();
            jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
        }

        jsonConfig.setJsonPropertyFilter(isSkipNull() ? getPropertyFilter() : null);

        return jsonConfig;
    }

    private PropertyFilter getPropertyFilter(){
        if($.isEmptyOrNull(propertyFilter)) {
            propertyFilter = new PropertyFilter() {
                @Override
                public boolean apply(Object o, String key, Object value) {
                    if($.str(key).equals("") || $.str(value).equals("")) return true;
                    return false;
                }
            };
        }

        return propertyFilter;
    }


    public boolean isSkipNull() {
        return skipNull;
    }

    public $Json setSkipNull(boolean skipNull) {
        this.skipNull = skipNull;
        return this;
    }
}
