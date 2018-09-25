package com.ailbb.ajj.http;

import com.ailbb.ajj.$;
import com.sun.corba.se.spi.ior.ObjectKey;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

import static com.ailbb.ajj.$.*;

import java.net.URL;
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

    public String getParameterStr(Map<String, String[]>... map){
        List<String> li = new ArrayList<String>();
        for(Map<String, String[]> m : map) {
            for(String key : m.keySet()) {
                li.add(concat(key, "=", isEmptyOrNull(m.get(key)) ? "null" : join(Arrays.asList(m.get(key)))));
            }
        }
        return join(li, "&");
    }

    public String getParameterStr(Object... objects){
        List<String> li = new ArrayList<String>();
        for(Object o : objects) {
            Map<String, Object> ok = JSONObject.fromObject(o);
            for(String key : ok.keySet()) {
                li.add(concat(key, "=", isEmptyOrNull(ok.get(key)) ? "null" : ok.get(key)));
            }
        }
        return join(li, "&");
    }

    public String getParameterStr(HttpServletRequest request){
        return getParameterStr(request.getParameterMap());
    }

    /**
     * 获取根节点
     * @param url 解析的url
     * @return 返回根路径
     */
    public String base(URL url) {
        return concat(url.getHost(), ":" ,url.getPort());
    }
}
