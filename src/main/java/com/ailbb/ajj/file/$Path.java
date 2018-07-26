package com.ailbb.ajj.file;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Path {

    public String getPath(String path){
        String pr = null;
        if(system().equals("windows")) {
            if(test("^[A-Za-z]:", path) || test("/[A-Za-z]:", path)  ) pr = rel("/", path);
        } else if(system().equals("linux")) {
            if(path.startsWith("/")) pr = rel(path);
        } else {
            pr = path;
        }

        return isEmptyOrNull(pr) ? rel(Thread.currentThread().getContextClassLoader().getResource("").getPath(), path) : pr;
    }

    public String getRootPath(){
        return $Path.class.getResource("").getPath().replaceFirst("file:", "");
    }

}
