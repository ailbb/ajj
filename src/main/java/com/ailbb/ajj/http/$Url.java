package com.ailbb.ajj.http;

import static com.ailbb.ajj.$.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Url {

    public String url(String url) {
        if(isEmptyOrNull(url)) return url;

        if(url.startsWith($Http.$HTTP) || url.startsWith($Http.$HTTPS)) {
            url = url.replaceAll("/+|\\+", "/").replaceAll(":/+", "://");
        } else {
            return url(concat($Http.$HTTP, "://", url));
        }

        return url;
    }

    public String rel(String... path){
        String p = "";
        for(String pa : path) {
            pa = (isEmptyOrNull(pa) ? "" : pa).replaceAll("^\\.|$\\.", "").replaceAll("\\\\+|/+", "/"); // 去掉当前目录

            if(test("^\\.", pa)) { // 切割层级
                int level = regex("/\\.\\./", pa).size() + 1; // 需要向上递归层级个数
                List<Integer> ss = indexOfList("/", (p + "/").replaceAll("\\\\+|/+", "/")); // 源目录有多少级

                // 如果源目录级别大于需要递归的层级，则向上递归，否则，为根目录
                p = p.substring(0,
                        ss.size() > level ?
                                ss.get(ss.size() - level - 1) :
                                ss.get(0)
                );

                pa = pa.replaceAll("/\\.\\./", "");
            }

            p = concat(p, "/", pa).replaceAll("\\\\+|/+", "/");
        }

        return p;
    }

    public String parameterStr(Map<String, String[]>... map){
        List<String> li = new ArrayList<String>();
        for(Map<String, String[]> m : map) {
            for(String key : m.keySet()) {
                li.add(concat(key, "=", isEmptyOrNull(m.get(key)) ? "null" : join(Arrays.asList(m.get(key)))));
            }
        }
        return join(li, "&");
    }

}
