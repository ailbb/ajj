package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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


    public static double avg(Collection<Long> keySet) {

        if($.isEmptyOrNull(keySet)) return 0;

        AtomicLong value = new AtomicLong();
        keySet.stream().forEach(v->{ value.addAndGet(v); });
        return value.get() / keySet.size();
    }


    public long min(Collection<Long> keySet) {
        if($.isEmptyOrNull(keySet)) return 0;

        return keySet.stream().min((a,b)->{ return a>b ? 1:-1; }).get();
    }

    public long max(Collection<Long> keySet) {
        if($.isEmptyOrNull(keySet)) return 0;

        return keySet.stream().max((a,b)->{ return a>b ? 1:-1; }).get();
    }


    public static double avg(Long... keySet) {
        return avg(Arrays.stream(keySet).collect(Collectors.toSet()));
    }


    public long min(Long... keySet) {
        return min(Arrays.stream(keySet).collect(Collectors.toSet()));
    }

    public long max(Long... keySet) {
        return max(Arrays.stream(keySet).collect(Collectors.toSet()));
    }

}
