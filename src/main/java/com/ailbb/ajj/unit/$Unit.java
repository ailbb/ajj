package com.ailbb.ajj.unit;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Unit {

    // units
    public static final String $BYTE = "BYTE";
    public static final String $KB = "KB";
    public static final String $MB = "MB";
    public static final String $GB = "GB";
    public static final String $TB = "TB";
    public static final String $PB = "PB";
    public static final String $EB = "EB";
    public static final String $ZB = "ZB";
    public static final String $YB = "$YB";
    public static final String $BB = "BB";

    /**
     * byte to anything
     * @param num double
     * @return 格式化后的数据
     */
    public String convert(double num) {
        return convert(num, $BYTE);
    }

    /**
     * byte to anything
     * @param num double
     * @return 格式化后的数据
     */
    public String convert(double num, String unit) {
        if(isEmptyOrNull(unit)) return convert(num);

        if(num >= 1024) {
            unit = unit.toUpperCase();
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

}
