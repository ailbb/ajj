package com.ailbb.ajj.http;

import net.sf.json.JSONObject;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 5/9/2018.
 */
public class Ajax {
    private String url;
    private boolean async = false;
    private String type = $Http.$GET;
    private long timeout = $TIMEOUT;
    private JSONObject data;
    private Proxy proxy;

    private Callback callback;

    public Ajax(String url){
        this.setUrl(url);
    }

    public Ajax(String serverName, String requestMapping){
        this.setProxy($PROXY.get(serverName));
        String ip;
        int port;

        if(this.getProxy() == null) {
            warn("Proxy is null! Use default config!");
            ip = getIp();
            port = $Http.$PORT;
        } else {
            ip = this.getProxy().getIp();
            port = this.getProxy().getPort();
        }

        this.setUrl(
                String.format("%s://%s:%s" , $Http.$HTTP, ip, port)
                        + String.format("/%s/%s", serverName, requestMapping).replaceAll("//+", "/")
        );

        this.setData(JSONObject.fromObject(this));
    }

    public String getUrl() {
        return url;
    }

    public Ajax setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public Ajax setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public String getType() {
        return type;
    }

    public Ajax setType(String type) {
        this.type = type;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public Ajax setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public JSONObject getData() {
        return data;
    }

    public Ajax setData(JSONObject data) {
        this.data = data;
        return this;
    }

    public Callback getCallback() {
        return callback;
    }

    public Ajax setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public Ajax setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    /**
     * Thread callback
     */
    public interface Callback {
        void complete(String data);
        void success(String data);
        void error(String data);
    }

    @Override
    public String toString() {
        return "Ajax{" +
                "$url='" + url + '\'' +
                ", async=" + async +
                ", type='" + type + '\'' +
                ", timeout=" + timeout +
                ", data=" + data +
                ", proxy=" + proxy +
                ", callback=" + callback +
                '}';
    }
}
