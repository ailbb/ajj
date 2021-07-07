package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

/*
 * Created by Wz on 8/22/2018.
 */
public class $Longer {
    public long toLong(Object o){
        String s = $.str(o);
        try {
            return $.isEmptyOrNull(s) ? 0 : Long.parseLong(s);
        } catch (Exception e) {
            return (long)Float.parseFloat(s);
        }
    }
}
