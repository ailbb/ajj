package com.ailbb.ajj.unit;

import com.ailbb.ajj.$;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by Wz on 12/24/2019.
 */
public class $Coding {

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /*
     * 判断是否为乱码
     *
     * @param str
     * @return
     */
    public static boolean isMessyCodeUnicode(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
            //从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
            //$.info("--- " + (int) c);
            if ((int) c == 0xfffd) {
                // 存在乱码
                //$.info("存在乱码 " + (int) c);
                return true;
            }
        }
        return false;
    }


    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0 ;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        float result = count / chLength ;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasMessyCode(String str){
        return !java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(str);
    }


    public static String toChinese(String msg){
        if(isMessyCode(msg)){
            try {
                return new String(msg.getBytes("ISO8859-1"), "UTF-8");
            } catch (Exception e) {
            }
        }
        return msg ;
    }

    /*
     * 判断是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean isGBK(String str) {
        char[] chars = str.toCharArray();
        boolean isGBK = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
                        && ints[1] <= 0xFE) {
                    isGBK = true;
                    break;
                }
            }
        }
        return isGBK;
    }

    /*
     * 判断字符串是否为双整型数字
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        if ($.isEmptyOrNull(str)) {
            return false;
        }
        Pattern p = Pattern.compile("-*\\d*.\\d*");
        // Pattern p = Pattern.compile("-*"+"\\d*"+"."+"\\d*");
        return p.matcher(str).matches();
    }

    /*
     * 判断字符串是否为整字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if ($.isEmptyOrNull(str)) {
            return false;
        }
        Pattern p = Pattern.compile("-*\\d*");
        return p.matcher(str).matches();
    }

    /*
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ) {
            return false;
        }
        return true;
    }
}
