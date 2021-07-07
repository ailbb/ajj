package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

/*
 * Created by Wz on 8/22/2018.
 */
public class $Integer {
    public int toInt(Object o){
        String s = $.str(o);
        try {
            return $.isEmptyOrNull(s) ? 0 : Integer.parseInt(s);
        } catch (Exception e) {
            return (int)Float.parseFloat(s);
        }
    }
}
