package com.ailbb.ajj.http;

import static com.ailbb.ajj.$.*;

import com.ailbb.ajj.entity.$Result;
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
            String p = path.getPath(Proxy.class); // 默认获取类文件目录
            // /D:/Z/Code/java/java-ee/Share/sharepro/code/web-service/sharepro/target/sharepro/WEB-INF/lib/ajj-1.9-SNAPSHOT.jar!/com/ailbb/ajj/http/
            String search = "jar!/";
            int pIndex = p.lastIndexOf(search);
            if(-1 == pIndex) pIndex = p.lastIndexOf(search = "classes/");
            if(-1 != pIndex) p = p.substring(0, pIndex + search.length());
            initProxy(rel(p, $PROXY_PATH));
        }
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

