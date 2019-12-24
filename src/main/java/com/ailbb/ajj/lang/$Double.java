package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.math.BigDecimal;

/**
 * Created by Wz on 8/22/2018.
 */
public class $Double {
    public double toDouble(Object o){
        return $.isEmptyOrNull(o) ? 0 : Double.parseDouble($.str(o));
    }

    public double toDouble(Object o, int index){
        double d = Double.parseDouble($.str(o));

        BigDecimal b = new BigDecimal(d);
        return b.setScale(index, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
