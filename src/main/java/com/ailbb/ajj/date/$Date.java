package com.ailbb.ajj.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Date {

    public String now(String... ns){
        String n = lastDef("s", ns);

        if(n.equals("s")) return format("YYYY-MM-dd HH:mm:ss"); // stand
        if(n.equals("ss")) return format("YYYY-MM-dd HH:mm:ss.S"); // stand millisecond

        if(n.equals("n")) return format("YYYYMMddHHmmss"); // number
        if(n.equals("nd")) return format("YYYYMMdd"); // number millisecond
        if(n.equals("nh")) return format("YYYYMMddHH"); // number millisecond
        if(n.equals("nm")) return format("YYYYMMddHHmm"); // number millisecond
        if(n.equals("ns")) return format("YYYYMMddHHmmss"); // number millisecond
        if(n.equals("nss")) return format("YYYYMMddHHmmssS"); // number millisecond

        return format("YYYY-MM-dd HH:mm:ss");
    }

    public String format(String patten, Date... date){
        return new SimpleDateFormat(patten).format(isEmptyOrNull(date) ? new java.util.Date() : date[date.length-1]);
    }

    public Date parse(String date, String... patten) {
        try {
            return new SimpleDateFormat(lastDef("YYYY-MM-dd HH:mm:ss", patten)).parse(date);
        } catch (Exception e) {
            return null;
        }
    }

}
