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

    public String concat(String... str){
        StringBuffer p = new StringBuffer();
        for(String pa : str) p.append(null == pa ? "" : pa);
        return p.toString();
    }

}
