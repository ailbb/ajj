package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import static com.ailbb.ajj.$.isEmptyOrNull;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Object {
    public boolean isBaseType(Object... o){
        if(isEmptyOrNull(o)) return true;

        for(java.lang.Object oi : o){
            if(oi instanceof java.lang.Integer) return true;
            if(oi instanceof java.lang.Byte) return true;
            if(oi instanceof java.lang.Long) return true;
            if(oi instanceof java.lang.Double) return true;
            if(oi instanceof java.lang.Float) return true;
            if(oi instanceof java.lang.Character) return true;
            if(oi instanceof java.lang.Short) return true;
            if(oi instanceof java.lang.Boolean) return true;
            if(oi instanceof java.lang.Number) return true;
            if(oi instanceof java.lang.String) return true;
            if(oi instanceof java.lang.StringBuffer) return true;
        }

        return false;
    }

    public boolean isEmptyOrNull(Object... o){
        if(null == o || o.length == 0) return true;
        for(java.lang.Object oi : o) {
            if(null == oi || oi.equals(null)) return true;

            if($.str(oi).replaceAll("\\[|\\]|\\{|\\}", "").equals("")) return true;
        }

        return false;
    }

    public <T> T  last(T... strs) {
        return lastDef(null, strs);
    }

    public <T> T  lastDef(T def, T... strs) {
        return (isEmptyOrNull(strs) || isEmptyOrNull(strs[strs.length-1])) ? def : strs[strs.length-1];
    }

    public String notNull(Object o, String... message) {
        if($.isEmptyOrNull(o)) throw new NullPointerException($.concat(message));

        return String.valueOf(o);
    }


}
