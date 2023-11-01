package com.ailbb.ajj.date;

import com.ailbb.ajj.$;

import java.util.Calendar;
import java.util.Date;

public class $DateVariable {

    /*
    ${用途_时间类型_跳分钟数_数量h或m时间单位}形式的变量各部分长度做如下限制，

    避免错误匹配情况：
        用途：1-15字符
        时间类型：1-10字符
        跳分钟数：1-5字符
        数量：1-5字符
        h或m时间单位：1-2字符

    举例：	以开始时间为2019-12-31 18:00:00的变量示例说明；

    $(date) 	当前运行时间
        示例结果：$(date)  =>  Tue Dec 31 18:04:23 CST 2019
    ${scheduledTime} 	任务调度时间
        示例结果：${scheduledTime}  =>  Tue Dec 31 18:00:00 CST 2019
    ${jobId} 	运行任务Id
        示例结果：${jobId}  =>  20191221113447251
    ${yesterday} 	替换为昨天的8位日期
        示例结果：${yesterday}  =>  20191230
    ${today} 	替换为今天的8位日期
        示例结果：${today}  =>  20191231
    ${tomorrow} 	替换为明天的8位日期
        示例结果：${tomorrow}  =>  20200101
    ${today-n} 	替换为今天之前n天的8位日期
        示例结果：${today-2}  =>  20191229
    ${monday-n} 	替换为今天之前n周的周一的8位日期
        示例结果：${monday-2}  =>  20191216
    ${first_day-n} 	替换为今天之前n月的1号的8位日期
        示例结果：${first_day-2}  =>  20191001
    ${monday} 	替换为本周周一的8位日期
        示例结果：${monday}  =>  20191230
    ${last_month} 	表示上月的同一天 （如不存在则进1天）
        示例结果：${last_month}  =>  20191201
    ${first_day} 	替换为本月1号的8位日期
        示例结果：${first_day}  =>  20191201
    ${partition_last_m_nu} 	替换为day=XXXXXXXX,minute=XXXX的分区表示钟数,n为时间粒度数,u为时间粒度(m:分钟,h:小时)
        示例结果：${partition_last_15_15m}  =>  day=20201231,minute=1730
    ${where_last_m_nu} 	替换为day=XXXXXXXX and minute>=XXXX 的where条件表达式
        示例结果：${where_last_15_15m}  =>  day=20201231 and minute>=1730 and minute<1745
    ${path_last_m_nu} 	替换为day=XXXX/minute=XXXX/
        示例结果：${path_last_15_15m}  =>  day=20201231/minute=1730/
    ${day_last_m_nu} 	表示用于时间分区天(day)中最近10分钟前的5分始值,如2019-12-31
        示例结果：${day_last_15_15m}  =>  20201231
    ${minute_last_m_nu} 	表示用于时间分区分钟(minute)中最近10分钟前的区起始值,如'18:00'
        示例结果：${minute_last_15_15m}  =>  1730
    ${granularity-nu_fmt} 	表示用于自定义时间分区和格式的值,如'2019-12-31 18:00:00'
        示例结果：
            ${granularity-2M_yyyyMMddHHmm}  =>  201910311800
            ${granularity-2d_yyyyMMddHHmm}  =>  201912291800
            ${granularity-2h_yyyyMMddHHmm}  =>  201912311600
            ${granularity-15m_yyyyMMddHHmm}  =>  201912311800

     */

    public String describe(){
        String date = $.date.now("h")+":00:00";

        return $.sout("    ${用途_时间类型_跳分钟数_数量h或m时间单位}形式的变量各部分长度做如下限制，\n" +
                "\n" +
                "    避免错误匹配情况：\n" +
                "        用途：1-15字符\n" +
                "        时间类型：1-10字符\n" +
                "        跳分钟数：1-5字符\n" +
                "        数量：1-5字符\n" +
                "        h或m时间单位：1-2字符\n" +
                "\n" +
                "    举例：\t以开始时间为"+date+"的变量示例说明；\n" +
                "\n" +
                "    $(date) \t当前运行时间\n" +
                "        示例结果：$(date)  =>  "+variable("date")+"\n" +
                "    ${yesterday} \t替换为昨天的8位日期\n" +
                "        示例结果：${yesterday}  =>  "+variable("yesterday")+"\n" +
                "    ${today} \t替换为今天的8位日期\n" +
                "        示例结果：${today}  =>  "+variable("today")+"\n" +
                "    ${tomorrow} \t替换为明天的8位日期\n" +
                "        示例结果：${tomorrow}  =>  "+variable("tomorrow")+"\n" +
                "    ${today-n} \t替换为今天之前n天的8位日期\n" +
                "        示例结果：${today-2}  =>  "+variable("today-2")+"\n" +
                "    ${monday-n} \t替换为今天之前n周的周一的8位日期\n" +
                "        示例结果：${monday-2}  =>  "+variable("monday-2")+"\n" +
                "    ${first_day-n} \t替换为今天之前n月的1号的8位日期\n" +
                "        示例结果：${first_day-2}  =>  "+variable("first_day")+"\n" +
                "    ${monday} \t替换为本周周一的8位日期\n" +
                "        示例结果：${monday}  =>  "+variable("monday")+"\n" +
                "    ${last_month} \t表示上月的同一天 （如不存在则进1天）\n" +
                "        示例结果：${last_month}  =>  "+variable("last_month")+"\n" +
                "    ${first_day} \t替换为本月1号的8位日期\n" +
                "        示例结果：${first_day}  =>  "+variable("first_day")+"\n" +
                "    ${partition_last_m_nu} \t替换为day=XXXXXXXX,minute=XXXX的分区表示钟数,n为时间粒度数,u为时间粒度(m:分钟,h:小时)\n" +
                "        示例结果：${partition_last_15_15m}  =>  "+variable("partition_last_15_15m")+"\n" +
                "    ${where_last_m_nu} \t替换为day=XXXXXXXX and minute>=XXXX 的where条件表达式\n" +
                "        示例结果：${where_last_15_15m}  =>  "+variable("where_last_15_15m")+"\n" +
                "    ${path_last_m_nu} \t替换为day=XXXX/minute=XXXX/\n" +
                "        示例结果：${path_last_15_15m}  =>  "+variable("path_last_15_15m")+"/\n" +
                "    ${day_last_m_nu} \t表示用于时间分区天(day)中最近10分钟前的5分始值,如2019-12-31\n" +
                "        示例结果：${day_last_15_15m}  =>  "+variable("day_last_15_15m")+"\n" +
                "    ${minute_last_m_nu} \t表示用于时间分区分钟(minute)中最近10分钟前的区起始值,如'18:00'\n" +
                "        示例结果：${minute_last_15_15m}  =>  "+variable("minute_last_15_15m")+"\n" +
                "    ${granularity-nu_fmt} \t表示用于自定义时间分区和格式的值,如'"+date+"'\n" +
                "        示例结果：\n" +
                "            ${granularity-2M_yyyyMMddHHmm}  =>  "+variable("granularity-2M_yyyyMMddHHmm")+"\n" +
                "            ${granularity-2d_yyyyMMddHHmm}  =>  "+variable("granularity-2d_yyyyMMddHHmm")+"\n" +
                "            ${granularity-2h_yyyyMMddHHmm}  =>  "+variable("granularity-2h_yyyyMMddHHmm")+"\n" +
                "            ${granularity-15m_yyyyMMddHHmm}  =>  "+variable("granularity-15m_yyyyMMddHHmm")+""
        );
    }

    public String variable(String name) {

        if($.test("$", name)) name = name.replaceAll("\\$","").replaceAll("\\{","").replaceAll("\\}","");

        $.sout("variable:" + name);
        if (name.indexOf("date", 0) == 0) {
            return new Date().toString();
        } else if (name.indexOf("yesterday", 0) == 0) {
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
        } else {
            $.warn("not support type：" + name);
            return name;
        }
    }

    private Date scheduled_time(int i) {
        return new Date(new Date().getTime() - i);
    }

    private String format_date(String patten, Date date) {
        return $.date.format(patten, date);
    }

    public String partition_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("yyyyMMddHHmm", startTime);
        String day = time1.substring(0, 8);
        String minute = time1.substring(8, 12);
        $.sout("day=" + day + ",minute='" + minute + "'");
        return "day=" + day + ",minute='" + minute + "'";
    }

    public String where_last(int a, int b, String time) {
        Date endTime = scheduled_time(-a * 60 * 1000);
        String endTimeFmt = format_date("yyyyMMddHHmm", endTime);
        if (time.equals("m")) {
            endTime.setMinutes(endTime.getMinutes() - b);
        } else if (time.equals("h")) {
            endTime.setHours(endTime.getHours() - b);
        } else if (time.equals("d")) {
            endTime.setDate(endTime.getDate() - b);
        } else if (time.equals("M")) {
            endTime.setMonth(endTime.getMonth() - b);
        }
        String startTimeFmt = format_date("yyyyMMddHHmm", endTime);
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
        String newTime = format_date("yyyy-MM-dd HH:mm:00", startTime);
        $.sout("unixtime_last:" + newTime);
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
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("yyyyMMddHHmm", startTime);
        String day = time1.substring(0, 8);
        String minute = time1.substring(8, 12);
        $.sout("day=" + day + ",minute='" + minute + "'");
        return "day=" + day + "/minute=" + minute + "/";
    }

    public String day_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("yyyyMMddHHmm", startTime);
        String day = time1.substring(0, 8);
        $.sout("day=" + day);
        return day;
    }

    public String minute_last(int a, int b, String time) {
        Date startTime = scheduled_time(-a * 60 * 1000);
        if (time.equals("m")) {
            startTime.setMinutes(startTime.getMinutes() - b);
        } else if (time.equals("h")) {
            startTime.setHours(startTime.getHours() - b);
        } else if (time.equals("d")) {
            startTime.setDate(startTime.getDate() - b);
        } else if (time.equals("M")) {
            startTime.setMonth(startTime.getMonth() - b);
        }
        String time1 = format_date("yyyyMMddHHmm", startTime);
        String minute = time1.substring(8, 12);
        $.sout("minute=" + minute);
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
        $.sout("startTime=" + lasttime);
        return lasttime;
    }

}
