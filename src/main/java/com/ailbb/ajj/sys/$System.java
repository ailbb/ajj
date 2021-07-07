package com.ailbb.ajj.sys;

/*
 * Created by Wz on 6/20/2018.
 */
public class $System {
    public String system(){
        return System.getProperty("os.name").toLowerCase().startsWith("windows") ? "windows" : "linux";
    }
}
