package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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



    public static double avg(Collection<Integer> keySet) {

        if($.isEmptyOrNull(keySet)) return 0;

        AtomicInteger value = new AtomicInteger();
        keySet.stream().forEach(v->{ value.addAndGet(v); });
        return value.get() / keySet.size();
    }


    public int min(Collection<Integer> keySet) {
        if($.isEmptyOrNull(keySet)) return 0;

        return keySet.stream().min((a,b)->{ return a>b ? 1:-1; }).get();
    }

    public int max(Collection<Integer> keySet) {
        if($.isEmptyOrNull(keySet)) return 0;

        return keySet.stream().max((a,b)->{ return a>b ? 1:-1; }).get();
    }

    public static double avg(Integer... keySet) {
        return avg(Arrays.stream(keySet).collect(Collectors.toSet()));
    }


    public int min(Integer... keySet) {
        return min(Arrays.stream(keySet).collect(Collectors.toSet()));
    }

    public int max(Integer... keySet) {
        return max(Arrays.stream(keySet).collect(Collectors.toSet()));
    }

}
