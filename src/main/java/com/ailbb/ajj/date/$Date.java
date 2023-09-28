package com.ailbb.ajj.date;

import com.ailbb.ajj.$;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ailbb.ajj.$.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Date {
    public $Cron cron = new $Cron();
    public  $DateVariable dateVariable = new $DateVariable();
    public $Timeclock timeclock = new $Timeclock();

    public String now(String... ns){
        String n = lastDef("s", ns);

        if(n.equals("y")) return format("yyyy"); // stand
        if(n.equals("M")) return format("yyyy-MM"); // stand
        if(n.equals("d")) return format("yyyy-MM-dd"); // stand
        if(n.equals("h")) return format("yyyy-MM-dd HH"); // stand
        if(n.equals("m")) return format("yyyy-MM-dd HH:mm"); // stand
        if(n.equals("s")) return format("yyyy-MM-dd HH:mm:ss"); // stand
        if(n.equals("ss")) return format("yyyy-MM-dd HH:mm:ss.S"); // stand millisecond

        if(n.equals("n")) return format("yyyyMMddHHmmss"); // number
        if(n.equals("ny")) return format("yyyy"); // number millisecond
        if(n.equals("nM")) return format("yyyyMM"); // number millisecond
        if(n.equals("nd")) return format("yyyyMMdd"); // number millisecond
        if(n.equals("nh")) return format("yyyyMMddHH"); // number millisecond
        if(n.equals("nm")) return format("yyyyMMddHHmm"); // number millisecond
        if(n.equals("ns")) return format("yyyyMMddHHmmss"); // number millisecond
        if(n.equals("nss")) return format("yyyyMMddHHmmssS"); // number millisecond

        return format("yyyy-MM-dd HH:mm:ss");
    }

    public String variable(String name){
        return dateVariable.variable(name);
    }

    public String format(String patten, Date... date){
        return new SimpleDateFormat(patten).format(isEmptyOrNull(date) ? new Date() : date[date.length-1]);
    }

    public String format(Date... date){
        return format("yyyy-MM-dd HH:mm:ss", date);
    }

    public Date parse(String date, String... patten) throws ParseException {
        return new SimpleDateFormat(lastDef("yyyy-MM-dd HH:mm:ss", patten)).parse(date);
    }

    /*
     * 前移后移计算时间
     * @param date date
     * @param num how long
     * @param types types
     * @return date
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

    public Date date(String date, long num, String... types) throws ParseException {
        return date(parse(date), num, types);
    }

    public Date date(long num, String... types){
        return date(new Date(), num, types);
    }

    public Calendar mondayOfCalendar(){
        return mondayOfCalendar(0);
    }

    public Calendar mondayOfCalendar(int index){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        //  cal.getFirstDayOfWeek()根据前面的设置 来动态的改变此值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + index);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal;
    }

    public Date Monday(){
        return mondayOfCalendar().getTime();
    }

    public Date Tuesday(){
        return mondayOfCalendar(1).getTime();
    }

    public Date Wednesday(){
        return mondayOfCalendar(2).getTime();
    }

    public Date Thursday(){
        return mondayOfCalendar(3).getTime();
    }

    public Date Friday(){
        return mondayOfCalendar(4).getTime();
    }

    public Date Saturday(){
        return mondayOfCalendar(5).getTime();
    }

    public Date Sunday(){
        return mondayOfCalendar(6).getTime();
    }

    /**
     * 计时器 | 获取当前时间与上一次时间的偏移量
     * @return 偏移量时间
     */
    public long timeclock() {
        return timeclock.timeclock();
    }

    /**
     * 计时器
     * @param tag 0:启动 >0:指定位置 -1获取当前时间与第一次记录的时间偏移量
     * @return 偏移量时间
     */
    public long timeclock(int tag) {
        return timeclock.timeclock(tag);
    }

    public List<Long> getTimeclockCache() {
        return timeclock.getTimeclockCache();
    }
}
