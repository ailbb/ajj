package com.ailbb.ajj.log;

import static com.ailbb.ajj.$.*;

import com.ailbb.ajj.exception.$Exception;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Logger {

    public boolean exception(Exception... e){
        for(Exception ei : e) ei.printStackTrace();
        return false;
    }

    public boolean error(Object... o){
        for(Object oi : o) exception(new $Exception(String.format("[ERROR]\t%s", oi)));
        return false;
    }

    public boolean warn(Object... o){
        for(Object oi : o) sout(String.format("[WARNING]\t%s", oi));
        return false;
    }

    public boolean info(Object... o){
        for(Object oi : o) sout(String.format("[INFO]\t%s", oi));
        return true;
    }

    public boolean log(Object... o){
        for(Object oi : o) sout(String.format("[LOG]\t%s", oi));
        return true;
    }

    public boolean sout(Object... o){
        for(Object oi : o)
            System.out.println(now("s") + "\t" + oi.toString());
        return true;
    }

}
