package com.ailbb.ajj.date;

import com.ailbb.ajj.$;
import com.sun.istack.internal.NotNull;

import java.util.Date;

public class $DateVariable {

    public String variable(String name) {
        $.sout("variable:" + name);
        if (name.indexOf("yesterday", 0) == 0) {
            return today_before(1, "yyyyMMdd");
        } else if (name.indexOf("today-", 0) == 0) {
            String time = name.replace("today-", "");
            return today_before($.integer.toInt(time), "yyyyMMdd");
        } else if (name.indexOf("today", 0) == 0) {
            return today_before(0, "yyyyMMdd");
        } else if (name.indexOf("tomorrow", 0) == 0) {
            return today_before(-1, "yyyyMMdd");
        } else if (name.indexOf("monday-", 0) == 0) {
            String time = name.replace("monday-", "");
            return monday($.integer.toInt(time), "yyyyMMdd");
        } else if (name.indexOf("first_day-", 0) == 0) {
            String time = name.replace("first_day-", "");
            return first_day($.integer.toInt(time), "yyyyMMdd");
        } else if (name.indexOf("monday", 0) == 0) {
            return monday(0, "yyyyMMdd");
        } else if (name.indexOf("last_month", 0) == 0) {
            return last_month("yyyyMMdd");
        } else if (name.indexOf("first_day", 0) == 0) {
            return first_day(0, "yyyyMMdd");
        } else if (name.indexOf("partition_last", 0) == 0) {
            String time = name.substring(name.length() - 1, name.length());
            String[] arr = name.substring(0, name.length() - 1).split("_");
            return partition_last($.integer.toInt(arr[2]), $.integer.toInt(arr[3]), time);
        } else if (name.indexOf("where_last", 0) == 0) {
            String time = name.substring(name.length() - 1, name.length());
            String[] arr = name.substring(0, name.length() - 1).split("_");
            return where_last($.integer.toInt(arr[2]), $.integer.toInt(arr[3]), time);
        } else if (name.indexOf("path_last", 0) == 0) {
            String time = name.substring(name.length() - 1, name.length());
            String[] arr = name.substring(0, name.length() - 1).split("_");
            return path_last($.integer.toInt(arr[2]), $.integer.toInt(arr[3]), time);
        } else if (name.indexOf("day_last", 0) == 0) {
            String time = name.substring(name.length() - 1, name.length());
            String[] arr = name.substring(0, name.length() - 1).split("_");
            return day_last($.integer.toInt(arr[2]), $.integer.toInt(arr[3]), time);
        } else if (name.indexOf("minute_last", 0) == 0) {
            String time = name.substring(name.length() - 1, name.length());
            String[] arr = name.substring(0, name.length() - 1).split("_");
            return minute_last($.integer.toInt(arr[2]), $.integer.toInt(arr[3]), time);
        } else if (name.indexOf("unixtime_last", 0) == 0) {
            String time = name.substring(name.length() - 1, name.length());
            String[] arr = name.substring(0, name.length() - 1).split("_");
            return unixtime_last($.integer.toInt(arr[2]), $.integer.toInt(arr[3]), time);
        } else if (name.indexOf("granularity", 0) == 0) {
            String[] arr = name.replace("granularity-", "").split("_");
            String formate = "yyyyMMddHHmm";
            if (arr.length == 2)
                formate = arr[1];
            String time = arr[0].substring(arr[0].length() - 1, arr[0].length());
            String a = arr[0].replace(time, "");
            return granularity($.integer.toInt(a), time, formate);
        }
        
        return name;
    }

    private Date scheduled_time(int i) {
        return new Date(new Date().getTime() - i);
    }

    private String format_date(String patten, Date date) {
        return $.date.format(patten, date);
    }

    public String partition_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        Date lastTime = null;
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("YYYYMMddHHmm", lastTime);
        String day = time1.substring(0, 8);
        String minute = time1.substring(8, 12);
        System.out.println("day=" + day + ",minute='" + minute + "'");
        return "day=" + day + ",minute='" + minute + "'";
    }

    public String where_last(int a, int b, String time) {
        Date endTime = scheduled_time(-a * 60 * 1000);
        String endTimeFmt = format_date("YYYYMMddHHmm", endTime);
        Date startTime = null;
        if (time.equals("m")) {
            endTime.setMinutes(endTime.getMinutes() - b);
        } else if (time.equals("h")) {
            endTime.setHours(endTime.getHours() - b);
        } else if (time.equals("d")) {
            endTime.setDate(endTime.getDate() - b);
        } else if (time.equals("M")) {
            endTime.setMonth(endTime.getMonth() - b);
        }
        String startTimeFmt = format_date("YYYYMMddHHmm", startTime);
        int endDay = $.integer.toInt(endTimeFmt.substring(0, 8));
        int startDay = $.integer.toInt(startTimeFmt.substring(0, 8));
        String endMinute = endTimeFmt.substring(8, 12);
        String startMinute = startTimeFmt.substring(8, 12);
        // 默认 startDay - endDay = 0 ：day=20200329 and minute>="2300" and minute<"0000"
        String fmt = "day=" + endDay + " and minute>='" + startMinute + "‘ and minute<'" + endMinute + "'";

        if (endDay - startDay > 1) {
            // startDay - endDay > 1 ：((day=20200320 and minute>="2300") or (day>20200320 and day<20200328 ) or (day=20200328 and minute<"0000"))
            fmt = "((day=" + startDay + " and minute>='" + startMinute + "‘) or (day>" + startDay + " and day<" + endDay + " ) or (day=" + endDay + " and minute<'" + endMinute + "‘))";
        } else if (endDay - startDay == 1) {
            // startDay - endDay = 1 ：((day=20200328 and minute>="2200") or (day=20200329 and minute<"0100"))
            fmt = "((day=" + startDay + " and minute>='" + startMinute + "‘) or (day=" + endDay + " and minute<'" + endMinute + "‘))";
        }
        if (endMinute == "0000") {
            fmt = "day=" + startDay + " and minute>='" + startMinute + "'";
        }

        return fmt;
    }

    public String unixtime_last(int a, int b, String time) {
        Date startTime = scheduled_time(0);
        Date lastTime = null;
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        int minutes = 8 * 60 - a;
        startTime.setMinutes(startTime.getMinutes() + minutes);
        String newTime = format_date("YYYY-MM-dd HH:mm:00", lastTime);
        System.out.println("unixtime_last:" + newTime);
        return "'" + newTime + "'";
    }

    public String today_before(int a, String formate) {
        Date startTime = scheduled_time(0);
        int b = $.integer.toInt(a);
        startTime.setDate(startTime.getDate() - b);
        return format_date(formate, startTime);
    }

    public String monday(int a, String formate) {
        Date startTime = scheduled_time(0);
        int day = startTime.getDay();
        int oneDayLong = 24 * 60 * 60 * 1000;
        startTime.setDate(startTime.getDate() - (day - 1) - 7 * a);
        return format_date(formate, startTime);
    }

    public String first_day(int a, String formate) {
        Date startTime = scheduled_time(0);
        startTime = new Date(startTime.getYear(), startTime.getMonth() - a, 1);
        return format_date(formate, startTime);
    }

    public String last_month(String formate) {
        Date startTime = scheduled_time(0);
        startTime.setMonth(startTime.getMonth() - 1);
        return format_date(formate, startTime);
    }

    public String path_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        Date lastTime = new Date();
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("YYYYMMddHHmm", lastTime);
        String day = time1.substring(0, 8);
        String minute = time1.substring(8, 12);
        System.out.println("day=" + day + ",minute='" + minute + "'");
        return "day=" + day + "/minute=" + minute + "/";
    }

    public String day_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        Date lastTime = new Date();
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("YYYYMMddHHmm", lastTime);
        String day = time1.substring(0, 8);
        System.out.println("day=" + day);
        return day;
    }

    public String minute_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        Date lastTime = new Date();
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("YYYYMMddHHmm", lastTime);
        String minute = time1.substring(8, 12);
        System.out.println("minute=" + minute);
        return minute;
    }

    public String granularity(int a, String time, String formate) {
        Date startTime = scheduled_time(0);
        String lasttime = "";
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - a);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - a);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - a);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - a);
        }
        lasttime = format_date(formate, startTime);
        System.out.println("startTime=" + lasttime);
        return lasttime;
    }

}
