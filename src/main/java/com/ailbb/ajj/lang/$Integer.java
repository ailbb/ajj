package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

/**
 * Created by Wz on 8/22/2018.
 */
public class $Integer {
    public int toInt(Object o){
        return Integer.parseInt($.str(o));
    }
}
