package com.ailbb.ajj.entity;

import com.ailbb.ajj.$;

/**
 * Created by Wz on 8/21/2018.
 */
public class $JDBCConnConfiguration extends $ConnConfiguration {
    private String driver;
    private String database;
    private String url;

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
        return url;
    }

    public $JDBCConnConfiguration setUrl(String url) {
        this.url = url;
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


}
