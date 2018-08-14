package com.ailbb.ajj.lang;

import static com.ailbb.ajj.$.*;

import java.util.Collection;

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
            if(++i != list.size())  if(!isEmptyOrNull(u)) {
                for (Object ui: u) sb.append(ui);
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
            if(++i % length == 0)
                sb.append(key);
            sb.append(s);
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
        return (null == strs || isEmptyOrNull(strs[0])) ? def : strs[0];
    }

    public String last(String... strs) {
        return lastDef(null, strs);
    }

    public String lastDef(String def, String... strs) {
        return (null == strs || isEmptyOrNull(strs[strs.length-1])) ? def : strs[strs.length-1];
    }

    public String str(Object object){
        return isEmptyOrNull(object) ? "" : object.toString();
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

}
