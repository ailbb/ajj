package com.ailbb.ajj.date;

import com.ailbb.ajj.$;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Date {

    public String now(String... ns){
        String n = lastDef("s", ns);

        if(n.equals("y")) return format("YYYY"); // stand
        if(n.equals("M")) return format("YYYY-MM"); // stand
        if(n.equals("d")) return format("YYYY-MM-dd"); // stand
        if(n.equals("h")) return format("YYYY-MM-dd HH"); // stand
        if(n.equals("m")) return format("YYYY-MM-dd HH:mm"); // stand
        if(n.equals("s")) return format("YYYY-MM-dd HH:mm:ss"); // stand
        if(n.equals("ss")) return format("YYYY-MM-dd HH:mm:ss.S"); // stand millisecond

        if(n.equals("n")) return format("YYYYMMddHHmmss"); // number
        if(n.equals("ny")) return format("YYYY"); // number millisecond
        if(n.equals("nM")) return format("YYYYMM"); // number millisecond
        if(n.equals("nd")) return format("YYYYMMdd"); // number millisecond
        if(n.equals("nh")) return format("YYYYMMddHH"); // number millisecond
        if(n.equals("nm")) return format("YYYYMMddHHmm"); // number millisecond
        if(n.equals("ns")) return format("YYYYMMddHHmmss"); // number millisecond
        if(n.equals("nss")) return format("YYYYMMddHHmmssS"); // number millisecond

        return format("YYYY-MM-dd HH:mm:ss");
    }

    public String format(String patten, Date... date){
        return new SimpleDateFormat(patten).format(isEmptyOrNull(date) ? new Date() : date[date.length-1]);
    }

    public Date parse(String date, String... patten) {
        try {
            return new SimpleDateFormat(lastDef("YYYY-MM-dd HH:mm:ss", patten)).parse(date);
        } catch (Exception e) {
            $.exception(e);
            return null;
        }
    }

    /**
     * 前移后移计算时间
     * @param date
     * @param num
     * @return
     */
    public Date date(Date date, long num, String... types){
        if(null == date) date = new Date();

        String type = $.lastDef("ss", types);
        if(type.equals("y")) num = num * 1000 * 60 * 60 * 24 * 365;
        if(type.equals("M")) num = num * 1000 * 60 * 60 * 24 * 30;
        if(type.equals("d")) num = num * 1000 * 60 * 60 * 24;
        if(type.equals("h")) num = num * 1000 * 60 * 60;
        if(type.equals("m")) num = num * 1000 * 60;
        if(type.equals("s")) num = num * 1000;
        if(type.equals("ss")) num = num;

        date.setTime(date.getTime() + num);
        return date;
    }

    public Date date(String date, long num, String... types){
        return date(parse(date), num, types);
    }

    public Date date(long num, String... types){
        return date(new Date(), num, types);
    }

}
