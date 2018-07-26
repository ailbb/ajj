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

    public static void init(){
        boolean inited = false;

        info("Ajj init...");

        if(new File(getPath($PROXY_PATH)).exists()) inited = initProxy($PROXY_PATH);

        if(!inited){
            info("Use default file.");
            String p = getRootPath();
            initProxy(rel(p.substring(0, p.lastIndexOf("com")), $PROXY_PATH));
        }
    }

    private static boolean initProxy(final String $PATH) {
        try {
            JSONArray ja = JSONArray.fromObject(readFile($PATH));

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
                        warn(e);
                        break;
                    }
                }
            }
        });

        return true;
    }

}

