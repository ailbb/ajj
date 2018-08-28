package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

/**
 * Created by Wz on 8/22/2018.
 */
public class $Integer {
    public int toInt(Object o){
        try {
            return Integer.parseInt($.str(o));
        } catch (Exception e) {
            return 0;
        }
    }
}
