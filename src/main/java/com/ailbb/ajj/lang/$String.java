package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import static com.ailbb.ajj.$.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Wz on 6/20/2018.
 */
public class $String {

    public String join(Collection list, Object... u){
        if(null == list) return null;

        int i=0;
        StringBuffer sb = new StringBuffer();

        for(Object l : list) {
            if(null != l) sb.append(l);
            if(++i != list.size())  {
                if(!isEmptyOrNull(u)) {
                    for (Object ui: u) sb.append(ui);
                } else sb.append(",");
            }

        }

        return sb.toString();
    }

    public String join(Object[] list, Object... u){
        if(null == list) return null;

        int i=0;
        StringBuffer sb = new StringBuffer();

        for(Object l : list) {
            if(null != l) sb.append(l);
            if(++i != list.length)  if(!isEmptyOrNull(u)) {
                for (Object ui: u) sb.append(ui);
            }
        }

        return sb.toString();
    }

    public String join(String key, String str, int length) {
        StringBuffer sb = new StringBuffer();
        int i=0;

        for(String s : str.split("")) {
            sb.append(s);
            if(++i % length == 0 && i != str.length())
                sb.append(key);
        }

        return sb.toString();
    }

    public String join(String key, String str) {
        return join(key, str, 1);
    }

    public String first(String... strs) {
        return lastDef(null, strs);
    }

    public String firstDef(String def, String... strs) {
        return (isEmptyOrNull(strs) || isEmptyOrNull(strs[0])) ? def : strs[0];
    }

    public String last(String... strs) {
        return lastDef(null, strs);
    }

    public String last(Object... strs) {
        return lastDef(null, strs);
    }

    public String lastDef(String def, String... strs) {
        return (isEmptyOrNull(strs) || isEmptyOrNull(strs[strs.length-1])) ? def : strs[strs.length-1];
    }

    public String lastDef(Object def, Object... strs) {
        return (isEmptyOrNull(strs) || isEmptyOrNull(strs[strs.length-1])) ? $.str(def) : $.str(strs[strs.length-1]);
    }

    public String str(Object object){
        return isEmptyOrNull(object) ? "" : String.valueOf(object);
    }

    public String concat(Object... objects){
        StringBuffer p = new StringBuffer();
        for(Object o : objects) p.append(isEmptyOrNull(o) ? "" : String.valueOf(o));
        return p.toString();
    }

    public String trim(String str){
        if(null == str) return null;

        return str.replaceAll("^\\s+|\\s+$", "");
    }

    public String fillBrefore(Object str, int length, String fill) {
        return fill(str, length, fill, false);
    }

    public String fillAfter(Object str, int length, String fill) {
        return fill(str, length, fill, true);
    }

    public String fill(Object str, int length, String fill) {
        return fill(str, length, fill, false);
    }

    public String fill(Object str, int length, String fill, boolean isAfter) {
        String s = $.str(str);
        while((s = isAfter ? (s + fill) : fill + s).length() != length);
        return s;
    }

    /**
     * 文本简化
     * @param data 数据文本
     * @return 100字内的缩写
     */
    public String simple(Object data) {
        String str = $.str(data);
        return str.length() > 100 ? (str.substring(0, 100) + "......") : str;
    }
}
