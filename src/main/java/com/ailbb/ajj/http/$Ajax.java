package com.ailbb.ajj.http;

import com.ailbb.ajj.entity.$Result;
import net.sf.json.JSONObject;

import java.net.UnknownHostException;
import java.util.Map;

import static com.ailbb.ajj.$.*;

/*
 * Created by Wz on 5/9/2018.
 */
public class $Ajax {
    private String url;
    private boolean async = false;
    private String type = $Http.$GET;
    private int timeout = $TIMEOUT;
    private JSONObject params;
    private Object data;
    private $Proxy proxy;
    private Map<String, String> property;

    private Callback callback;

    public $Ajax(String url){
        this.setUrl(url);
    }

    public $Ajax(String url, JSONObject params){
        this.setUrl(url).setParams(params);
    }

    public $Ajax(String serverName, String requestMapping){
        this(serverName, requestMapping, null);
    }

    public $Ajax(String serverName, String requestMapping, JSONObject params){
        this.setProxy($PROXY.get(serverName)).setParams(params);
        String ip;
        int port;

        if(this.getProxy() == null) {
            warn("Proxy is null! Use default config!");
            try {
                ip = getIp();
            } catch (UnknownHostException e) {
                ip = "127.0.0.1";
            }
            port = $Http.$PORT;
        } else {
            ip = this.getProxy().getIp();
            port = this.getProxy().getPort();
        }

        this.setUrl(
                String.format("%s://%s:%s" , $Http.$HTTP, ip, port)
                        + String.format("/%s/%s", serverName, requestMapping).replaceAll("//+", "/")
        );

        this.setParams(JSONObject.fromObject(this));
    }

    public Map<String, String> getProperty() {
        return property;
    }

    public $Ajax setProperty(Map<String, String> property) {
        this.property = property;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public $Ajax setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public $Ajax setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public String getType() {
        return type;
    }

    public $Ajax setType(String type) {
        this.type = type;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public $Ajax setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public JSONObject getParams() {
        return params;
    }

    public $Ajax setParams(JSONObject params) {
        this.params = params;
        return this;
    }

    public Object getData() {
        return data;
    }

    public $Ajax setData(Object data) {
        this.data = data;
        return this;
    }

    public Callback getCallback() {
        return callback;
    }

    public $Ajax setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public $Proxy getProxy() {
        return proxy;
    }

    public $Ajax setProxy($Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    /*
     * Thread callback
     */
    public interface Callback {
        void complete($Result result);
        void success($Result result);
        void error($Result result);
    }

    @Override
    public String toString() {
        return "Ajax{" +
                "$url='" + url + '\'' +
                ", async=" + async +
                ", type='" + type + '\'' +
                ", timeout=" + timeout +
                ", params=" + params +
                ", data=" + data +
                ", proxy=" + proxy +
                ", callback=" + callback +
                '}';
    }
}
