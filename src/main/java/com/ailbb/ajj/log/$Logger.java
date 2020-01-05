package com.ailbb.ajj.log;

import static com.ailbb.ajj.$.*;

import com.ailbb.ajj.$;
import com.ailbb.ajj.exception.$Exception;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Logger {

    public Exception exception(Exception... e){
        for(Exception ei : e) ei.printStackTrace();
        return isEmptyOrNull(e) ? null : e[e.length-1];
    }

    public String error(Object... o){
        for(Object oi : o) exception(new $Exception(String.format("[ERROR]\t%s", oi)));
        return $.lastStr(o);
    }

    public String warn(Object... o){
        for(Object oi : o) sout(String.format("[WARNING]\t%s", oi));
        return $.lastStr(o);
    }

    public String info(Object... o){
        for(Object oi : o) sout(String.format("[INFO]\t%s", oi));
        return $.lastStr(o);
    }

    public String log(Object... o){
        for(Object oi : o) sout(String.format("[LOG]\t%s", oi));
        return $.lastStr(o);
    }

    public String sout(Object... o){
        for(Object oi : o)
            System.out.println(now("s") + "\t" + oi.toString());

        return $.lastStr(o);
    }

}
