package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Object {

    public boolean isEmptyOrNull(java.lang.Object... o){
        if(null == o || o.length == 0) return true;
        for(java.lang.Object oi : o)
            if(null == oi || oi.toString().trim().length() == 0 || "null".equals(oi.toString().trim().toLowerCase())) return true;
        return false;
    }
}
