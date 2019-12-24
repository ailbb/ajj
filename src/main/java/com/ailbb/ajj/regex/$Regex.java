package com.ailbb.ajj.regex;

import com.ailbb.ajj.$;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Regex {

    public List<String> regex(String pattern, String... str) {
        List<String> list = new ArrayList<String>();

        for(String s: str) {
            Pattern r = Pattern.compile(pattern);

            // 现在创建 matcher 对象
            Matcher m = r.matcher(s);

            if (m.find( ))
                list.add(m.group());
        }

        return list;
    }

    public boolean test(String pattern, String... str) {
        return regex(pattern, str).size() != 0;
    }

    public boolean match(String pattern, String... str) {
        for(String s: str) {
            boolean isMatch = Pattern.matches(pattern, s);
            if(isMatch) return isMatch;
        }

        return false;
    }

    /**
     * 拾取文本
     * @param before 前置文本
     * @param pattern 匹配表达式
     * @param end 后置文本
     * @param text 需要匹配的文本
     * @return 表达式的内容
     */
    public String pickup(String before, String pattern, String end, String text){
        StringBuffer sb = new StringBuffer();
        if(!$.isEmptyOrNull(before)) sb.append(before);
        if(!$.isEmptyOrNull(pattern)) sb.append(pattern);
        if(!$.isEmptyOrNull(end)) sb.append(end);

        List<String> list = regex(sb.toString(), text);

        if(list.size() == 0) return null;

        String v = list.get(0);

        if(!$.isEmptyOrNull(before))  v = v.replaceAll("^" + before, "");
        if(!$.isEmptyOrNull(end)) v = v.replaceAll(before + "$", "");

        return v;
    }

    /**
     * 拾取文本
     * @param pattern 正则表达式
     * @param text 文本对象
     * @return 拾取的文本
     */
    public String pickup(String pattern, String text){
        List<String> list = regex(pattern, text);

        if(list.size() == 0) return null;

        return list.get(0);
    }

    public boolean isIp(String str){
        if($.isEmptyOrNull(str)) return false;
        boolean b1 = str.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
        return b1;
    }
}
