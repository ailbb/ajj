package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Object {

    public boolean isEmptyOrNull(Object... o){
        if(null == o || o.length == 0) return true;
        for(java.lang.Object oi : o)
            if(null == oi || String.valueOf(oi).trim().length() == 0 || "null".equals(String.valueOf(oi).trim().toLowerCase())) return true;
        return false;
    }

    public String notNull(Object o, String... message) {
        if($.isEmptyOrNull(o)) throw new NullPointerException($.concat(message));

        return String.valueOf(o);
    }
}
