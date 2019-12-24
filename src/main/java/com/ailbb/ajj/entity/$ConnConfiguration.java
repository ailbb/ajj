package com.ailbb.ajj.entity;

import com.ailbb.ajj.$;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wz on 8/21/2018.
 */
public class $ConnConfiguration {
    private String ip;
    private List<String> ips = new ArrayList<>();
    private int port;
    private String username;
    private String password;
    private String url;
    private int timeOut = 1000 * 5 * 60;

    public int getTimeOut() {
        return timeOut;
    }

    public $ConnConfiguration setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public $ConnConfiguration setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public $ConnConfiguration setIp(String ip) {
        this.ip = $.string.trim(ip);
        return this;
    }

    public $ConnConfiguration setIp(List<String> ips) {
        this.ips.clear();
        for(String ip: ips) {
            this.ips.add($.string.trim(ip));
        }
        return this;
    }

    public $ConnConfiguration setIp(String[] ips) {
        this.ips.clear();
        for(String ip: ips) {
            this.ips.add($.string.trim(ip));
        }
        return this;
    }

    public List<String> getIps() {
        return ips;
    }

    public $ConnConfiguration setIps(List<String> ips) {
        this.ips = ips;
        return this;
    }

    public int getPort() {
        return port;
    }

    public $ConnConfiguration setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public $ConnConfiguration setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public $ConnConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

}
