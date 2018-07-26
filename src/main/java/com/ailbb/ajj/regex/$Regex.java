package com.ailbb.ajj.regex;

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

}
