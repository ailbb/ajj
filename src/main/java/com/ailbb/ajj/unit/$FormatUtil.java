package com.ailbb.ajj.unit;

import com.ailbb.ajj.$;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;


public class $FormatUtil {
    public static String timesToDay(Long l) {
        if (l == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            long seconds = 1L;
            long minutes = 60L * seconds;
            long hours = 60L * minutes;
            long days = 24L * hours;
            if (l / days >= 1L) {
                sb.append((int)(l / days) + "天");
            }

            if (l % days / hours >= 1L) {
                sb.append((int)(l % days / hours) + "小时");
            }

            if (l % days % hours / minutes >= 1L) {
                sb.append((int)(l % days % hours / minutes) + "分钟");
            }

            if (l % days % hours % minutes / seconds >= 1L) {
                sb.append((int)(l % days % hours % minutes / seconds) + "秒");
            }

            return sb.toString();
        }
    }

    public static String secondsToDays(Long l) {
        if (l == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            long seconds = 1L;
            long minutes = 60L * seconds;
            long hours = 60L * minutes;
            long days = 24L * hours;
            if (l / days >= 1L) {
                sb.append((int)(l / days) + "天");
            }

            return sb.toString();
        }
    }

    public static String getString(String str, int len) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String kbToM(String str) {
        if (StringUtils.isEmpty(str)) {
            return "0KB";
        } else {
            double result = 0.0;
            double mod = 1024.0;

            try {
                double strDouble = Double.valueOf(str);
                if (strDouble > 1024.0) {
                    result = strDouble / mod;
                    return formatDouble(result, 2) + "MB";
                }
            } catch (Exception var7) {
                $.error("kb转为M错误：", var7);
                return str + "KB";
            }

            return str + "KB";
        }
    }

    public static String mToG(String str) {
        if (StringUtils.isEmpty(str)) {
            return "0M";
        } else {
            double result = 0.0;
            double mod = 1024.0;

            try {
                double strDouble = Double.valueOf(str);
                if (strDouble > 1024.0) {
                    result = strDouble / mod;
                    return formatDouble(result, 2) + "G";
                }
            } catch (Exception var7) {
                $.error("m转为g错误：", var7);
                return str + "M";
            }

            return str + "M";
        }
    }

    public static String gToT(String str) {
        if (StringUtils.isEmpty(str)) {
            return "0G";
        } else {
            double result = 0.0;
            double mod = 1024.0;

            try {
                double strDouble = Double.valueOf(str);
                if (strDouble > 1024.0) {
                    result = strDouble / mod;
                    return formatDouble(result, 1) + "T";
                }
            } catch (Exception var7) {
                $.error("G转为T错误：", var7);
                return str + "G";
            }

            return formatDouble(Double.valueOf(str), 1) + "G";
        }
    }

    public static String bytesFormatUnit(String str, String snmpUnit) {
        String ftmp="";
        String htmp="";
        if (!StringUtils.isEmpty(str) && !"0.0".equals(str)&& !"0".equals(str)) {
            try {
                if (str.contains(".")){
                    ftmp=StringUtils.substringAfter(str,".");
                    htmp=StringUtils.substringBefore(str,".");
                }
                else{
                    htmp=str;
                }
                long bytes = Long.valueOf(htmp);
                if (!"byte".equals(snmpUnit)) {
                    bytes *= 1024L;
                }

                int k = 1024;
                String[] sizes = new String[]{"b", "Kb", "M", "G", "T", "P", "E", "Z", "Y"};
                int i = (int)Math.floor(Math.log((double)bytes) / Math.log((double)k));
                if (i > sizes.length - 1) {
                    i = sizes.length - 1;
                }
                if (!ftmp.equals("")){
                    return (int)((double)bytes / Math.pow((double)k, (double)i)) +"."+ ftmp +" "+ sizes[i];
                }
                else{
                    return formatDouble((double)bytes / Math.pow((double)k, (double)i), 2) +" "+ sizes[i];
                }
            } catch (Exception var7) {
                $.error("bytesFormatUnit错误", var7);
                return str + "b";
            }
        } else {
            return "0b";
        }
    }

    public static double bytesFormatUnitdouble(String str, String snmpUnit) {
        if (!StringUtils.isEmpty(str) && !"0".equals(str)) {
            try {
                long bytes = Long.valueOf(str);
                if (!"byte".equals(snmpUnit)) {
                    bytes *= 1024L;
                }

                int k = 1024;
                String[] sizes = new String[]{"b", "Kb", "M", "G", "T", "P", "E", "Z", "Y"};
                int i = (int)Math.floor(Math.log((double)bytes) / Math.log((double)k));
                if (i > sizes.length - 1) {
                    i = sizes.length - 1;
                }

                return formatDouble((double)bytes / Math.pow((double)k, (double)i), 2);
            } catch (Exception var7) {
                $.error("bytesFormatUnit错误", var7);
                return 0.0 ;
            }
        } else {
            return 0.0;
        }
    }

    public static String byteToM(String str, String snmpUnit) {
        if (StringUtils.isEmpty(str)) {
            return "0B";
        } else {
            double result = 0.0;
            double mod = 1024.0;

            try {
                double strDouble = Double.valueOf(str);
                if (!"byte".equals(snmpUnit)) {
                    strDouble *= 1024.0;
                }

                result = strDouble / mod / mod;
                return formatDouble(result, 2) + "MB";
            } catch (Exception var8) {
                $.error("kb转为M错误：", var8);
                return str + "KB";
            }
        }
    }

    public static double formatDouble(Double str, int num) {
        if (str == null) {
            return 0.0;
        } else {
            BigDecimal b = new BigDecimal(str);
            double myNum3 = b.setScale(num, 4).doubleValue();
            return myNum3;
        }
    }

    public static String haveSqlDanger(String sql, String sqlInKeys) {
        if (StringUtils.isEmpty(sql)) {
            return "";
        } else {
            sql = sql.toLowerCase();
            sqlInKeys = sqlInKeys.toLowerCase();
            String[] sqlinkeys = sqlInKeys.split(",");
            String[] var3 = sqlinkeys;
            int var4 = sqlinkeys.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String sqlinkey = var3[var5];
                if (sql.indexOf(sqlinkey) > -1) {
                    return sqlinkey;
                }
            }

            return "";
        }
    }

    public static String haveBlockDanger(String shell, String linuxBlock, String winBlock) {
        if (StringUtils.isEmpty(shell)) {
            return "";
        } else if (StringUtils.isEmpty(linuxBlock) && StringUtils.isEmpty(winBlock)) {
            return "";
        } else {
            shell = shell.toLowerCase();
            String[] blocks = linuxBlock.split(",");
            String[] var4 = blocks;
            int var5 = blocks.length;

            int var6;
            String blockStr;
            for(var6 = 0; var6 < var5; ++var6) {
                blockStr = var4[var6];
                if (shell.indexOf(blockStr) > -1) {
                    return blockStr;
                }
            }

            blocks = winBlock.split(",");
            var4 = blocks;
            var5 = blocks.length;

            for(var6 = 0; var6 < var5; ++var6) {
                blockStr = var4[var6];
                if (shell.indexOf(blockStr) > -1) {
                    return blockStr;
                }
            }

            return "";
        }
    }

    //不允许double 用科学计算法转string
    public static String formatDouble2(Object object){
        NumberFormat nf= NumberFormat.getInstance();
        Object ob=object;
        $FormatUtil.formatDouble((double)ob,2);
        nf.setGroupingUsed(false);
        return nf.format(object);
    }
}
