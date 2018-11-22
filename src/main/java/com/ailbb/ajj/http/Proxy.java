package com.ailbb.ajj.http;

import static com.ailbb.ajj.$.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;

/**
 * Created by Wz on 5/10/2018.
 * Ajax proxy
 */
public class Proxy {
    private String serverName;
    private String ip;
    private int port;

    public String getServerName() {
        return serverName;
    }

    public Proxy setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Proxy setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Proxy setPort(int port) {
        this.port = port;
        return this;
    }

    public static void init(){
        boolean inited = false;
        info("Ajj init...");

        if(new File(getPath($PROXY_PATH)).exists()) inited = initProxy($PROXY_PATH);

        if(!inited){
            info("Use default file.");
            String p = path.getPath(Proxy.class); // 默认获取类文件目录
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
                Proxy proxy = (Proxy) JSONObject.toBean(JSONObject.fromObject(o), Proxy.class);
                $PROXY.put(proxy.getServerName(), proxy);
            }
        } catch (Exception e) {
            warn("File is init error! " + e);
            return false;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(initProxy($PATH)) {
                    try {
                        Thread.sleep($TIMEOUT);
                    } catch (InterruptedException e) {
                        exception(e);
                        break;
                    }
                }
            }
        });

        return true;
    }

}

