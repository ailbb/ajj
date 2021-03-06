package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.math.BigDecimal;

/*
 * Created by Wz on 8/22/2018.
 */
public class $Double {
    public double toDouble(Object o){
        return $.isEmptyOrNull(o) ? 0 : Double.parseDouble($.str(o));
    }

    public double toDouble(Object o, int fixed){
        BigDecimal b = new BigDecimal(toDouble(o));
        return b.setScale(fixed, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
