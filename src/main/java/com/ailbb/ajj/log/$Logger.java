package com.ailbb.ajj.log;

import static com.ailbb.ajj.$.*;

import com.ailbb.ajj.exception.$Exception;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Logger {

    public void exception(Exception... e){
        for(Exception ei : e) ei.printStackTrace();
    }

    public void error(Object... o){
        for(Object oi : o) exception(new $Exception(String.format("[ERROR]\t%s", oi)));
    }

    public void warn(Object... o){
        for(Object oi : o) sout(String.format("[WARNING]\t%s", oi));
    }

    public void info(Object... o){
        for(Object oi : o) sout(String.format("[INFO]\t%s", oi));
    }

    public void log(Object... o){
        for(Object oi : o) sout(String.format("[LOG]\t%s", oi));
    }

    public void sout(Object... o){
        for(Object oi : o)
            System.out.println(String.format(now("s") + "\t%s", oi));
    }

}
