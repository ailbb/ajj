package com.ailbb.ajj.unit;

import com.ailbb.ajj.$;

import static com.ailbb.ajj.$.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Unit {

    public static final int SECOND_OF_MINUTE = 60;
    public static final int SECOND_OF_HOUR = 60 * SECOND_OF_MINUTE;
    public static final int SECOND_OF_DAY = 24 * SECOND_OF_HOUR;
    public static final int SECOND_OF_WEEK = 7 * SECOND_OF_DAY;
    public static final long MILLI_OF_WEEK = SECOND_OF_WEEK * 1000;
    public static final long MILLI_OF_DAY = SECOND_OF_DAY * 1000;
    public static final long MILLI_OF_HOUR = SECOND_OF_HOUR * 1000;
    public static final long MILLI_OF_MINUTE = SECOND_OF_MINUTE * 1000;
    public static final long MILLI_OF_FIFTEEN_MINUTE = 15*SECOND_OF_MINUTE*1000;

    public static final long KB = 1024L;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final long TB = 1024 * GB;
    public static final long PB = 1024 * TB;

    // units
    public static final String $BYTE = "BYTE";
    public static final String $KB = "KB";
    public static final String $MB = "MB";
    public static final String $GB = "GB";
    public static final String $TB = "TB";
    public static final String $PB = "PB";
    public static final String $EB = "EB";
    public static final String $ZB = "ZB";
    public static final String $YB = "YB";
    public static final String $BB = "BB";
    public static final String $VCORE = "VCORE";
    public static final String $BAI = "百";
    public static final String $QIAN = "千";
    public static final String $WAN = "万";
    public static final String $SHIWAN = "十万";
    public static final String $BAIWAN = "百万";
    public static final String $QIANWAN = "千万";
    public static final String $YI = "亿";
    public static final String $SHIYI = "十亿";
    public static final String $BAIYI = "百亿";
    public static final String $QIANYI = "千亿";
    public static final String $WANYI = "万亿";

    /*
     * byte to anything
     * @param num double
     * @return 格式化后的数据
     */
    public String convert(double num) {
        return convert(num, $BYTE);
    }

    /*
     * byte to anything
     * @param num double
     * @return 格式化后的数据
     */
    public String CountConvert(double num) {
        return CountConvert(num, null);
    }

    /*
     * byte to anything
     * @param num double
     * @return 格式化后的数据
     */
    public String convert(double num, String unit) {
        if(isEmptyOrNull(unit)) return convert(num);
        unit = unit.toUpperCase();

        if(num >= 1024) {
            if(unit.equals($BYTE)) return convert(num/1024, $KB);
            if(unit.equals($KB)) return convert(num/1024, $MB);
            if(unit.equals($MB)) return convert(num/1024, $GB);
            if(unit.equals($GB)) return convert(num/1024, $TB);
            if(unit.equals($TB)) return convert(num/1024, $PB);
            if(unit.equals($PB)) return convert(num/1024, $EB);
            if(unit.equals($EB)) return convert(num/1024, $ZB);
            if(unit.equals($ZB)) return convert(num/1024, $YB);
            if(unit.equals($YB)) return convert(num/1024, $BB);
        }

        return String.format("%.2f %s", num, unit);
    }

    public String CountConvert(double num, String unit){
        unit = isEmptyOrNull(unit) ? "" : unit.toUpperCase();

        if(num > 10) {
            if(num>100 && $.isEmptyOrNull(unit)) return convert(num/100, $BAI);
            if(unit.equals($BAI)) return convert(num/10, $QIAN);
            if(unit.equals($QIAN)) return convert(num/10, $WAN);
            if(unit.equals($WAN)) return convert(num/10, $SHIWAN);
            if(unit.equals($SHIWAN)) return convert(num/10, $BAIWAN);
            if(unit.equals($BAIWAN)) return convert(num/10, $QIANWAN);
            if(unit.equals($QIANWAN)) return convert(num/10, $YI);
            if(unit.equals($YI)) return convert(num/10, $SHIYI);
            if(unit.equals($SHIYI)) return convert(num/10, $BAIYI);
            if(unit.equals($BAIYI)) return convert(num/10, $QIANYI);
            if(unit.equals($QIANYI)) return convert(num/10, $WANYI);
        }

        return  String.format("%.2f %s", num, unit);
    }

}
