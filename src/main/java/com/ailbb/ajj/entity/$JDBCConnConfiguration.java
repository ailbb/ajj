package com.ailbb.ajj.entity;

import com.ailbb.ajj.$;

import java.util.List;

/*
 * Created by Wz on 8/21/2018.
 */
public class $JDBCConnConfiguration extends $ConnConfiguration {
    private String driver;
    private String database;
    private String url;
    private String[] urls = new String[]{};

    public $JDBCConnConfiguration init(String type) {
        if($.isEmptyOrNull(type)) return this;

        type = $.string.trim(type.toLowerCase());

        if(type.equals("mysql")) this.setDriver($.jdbc.mysql.$DRIVER).setPort($.jdbc.mysql.$PORT);

        return this;
    }

    public $JDBCConnConfiguration init(String ipOrUrl, String username,String password) {
        init(ipOrUrl,username,password,null);
        return this;
    }

    public $JDBCConnConfiguration init(String ipOrUrl, String username,String password, String type) {
        if(ipOrUrl.startsWith("jdbc"))
            this.setUrl(ipOrUrl);
        else
            this.setIp(ipOrUrl);

        this.setUsername(username).setPassword(password);
        this.init("mysql");
        return this;
    }

    public String getUrl() {
        return url = $.isEmptyOrNull(url) && urls.length>0 ? $.random(urls) : url;
    }

    public String getUrl(List<String> filters) {
        if(null != filters && filters.size() < urls.length && -1 != filters.indexOf(getUrl())) {
            return setUrl(null).getUrl(filters); // 当过滤器里面有值 且 随机队列没满，则重置url后，重新获取随机url
        }

        return getUrl();
    }

    public $JDBCConnConfiguration setUrl(String url) {
        this.url = url;
        return this;
    }

    public String[] getUrls() {
        return urls;
    }

    public $JDBCConnConfiguration setUrl(String urls, String split) {
        if(!$.isEmptyOrNull(urls)) setUrls(urls.split(split));
        return this;
    }

    public $JDBCConnConfiguration setUrls(String[] urls) {
        this.urls = urls;
        return this;
    }

    public String getDriver() {
        return driver;
    }

    public $JDBCConnConfiguration setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public $JDBCConnConfiguration setDatabase(String database) {
        this.database = database;
        return this;
    }


    @Override
    public String toString() {
        return "$JDBCConnConfiguration{" +
                "driver='" + driver + '\'' +
                ", database='" + database + '\'' +
                ", url='" + url + '\'' +
                "} | " + super.toString();

    }
}
