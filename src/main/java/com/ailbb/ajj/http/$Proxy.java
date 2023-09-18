package com.ailbb.ajj.http;

import static com.ailbb.ajj.$.*;

import com.ailbb.ajj.$;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;

/*
 * Created by Wz on 5/10/2018.
 * Ajax proxy
 */
public class $Proxy {
    private String serverName;
    private String ip;
    private int port;

    public static void init(){
        boolean inited = false;
        info("AJJ init...");

        double javaVersion = $.toDouble(System.getProperty("java.specification.version"));
        double classVersion = $.toDouble(System.getProperty("java.class.version"));

        if(javaVersion < 11) $.warn("Your java version is ["+javaVersion+"].  Please upgrade to the new version as soon as possible. At the least >Java 11");

        if(classVersion < 55) $.warn("Your class version is ["+classVersion+"]. Please upgrade to the new version as soon as possible. At the least >Class 55.0");

        if(new File(getPath($PROXY_PATH)).exists()) inited = initProxy($PROXY_PATH);

        if(!inited){
            info("Use default file.");
            String p = path.getPath($Proxy.class); // 默认获取类文件目录
            String search = "jar!/";
            int pIndex = p.lastIndexOf(search);
            if(-1 == pIndex) pIndex = p.lastIndexOf(search = "classes/");
            if(-1 != pIndex) p = p.substring(0, pIndex + search.length());
            initProxy(rel(p, $PROXY_PATH));
        }

        tomcat.init(); // 初始化tomcat内容
    }

    private static boolean initProxy(final String $PATH) {
        try {
            JSONArray ja = JSONArray.fromObject(readFile($PATH).getData());

            $PROXY.clear();

            for(Object o : ja) {
                $Proxy proxy = ($Proxy) JSONObject.toBean(JSONObject.fromObject(o), $Proxy.class);
                $PROXY.put(proxy.getServerName(), proxy);
            }
        } catch (Exception e) {
            warn("File is init error! " + e);
            return false;
        }

        return true;
    }


    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

