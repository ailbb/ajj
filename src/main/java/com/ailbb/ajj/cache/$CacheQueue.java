package com.ailbb.ajj.cache;

import com.ailbb.ajj.$;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class $CacheQueue {
    private static Map<String, $EntityCache> caches = new ConcurrentHashMap<String, $EntityCache>();
    private Jedis redis;
    private String addr;
    private int port;
    private String password;

    $CacheQueue(){ autoRegistRedis(); }

    /**
     * 通过配置文件自动配置redis信息
     * @return
     */
    public boolean autoRegistRedis(){
        File yml = $.getFile("application.yml"); // 尝试获取配置文件
        Properties p = null;

        if(yml.exists()) {
            p = $.file.yml.getProperties(yml.getAbsolutePath());
        } else {
            File properties = $.getFile("application.properties"); // 尝试获取属性文件
            if(properties.exists()) {
                p = $.file.properties.getProperties(properties.getAbsolutePath());
            }
        }

        // 如果获取到配置信息，尝试解析redis相关内容
        if(null != p) {
            if(!Boolean.valueOf(p.getProperty("spring.data.redis.enable", "true"))) return false;

            $.info("[Cache] Get Redis config Success! Try regist redis. If you not enable cache to redis, please set [spring.data.redis.enable=false]");

            this.addr = p.getProperty("spring.data.redis.host");
            this.port = Integer.valueOf(p.getProperty("spring.data.redis.port", "6379"));
            this.password = p.getProperty("spring.data.redis.password");

            return registRedis(addr, port, password);
        }

        return false;
    }

    public String getCurrModel(){
        if(testRedis()) return "redis";
        return "local";
    }

    public synchronized boolean registRedis(Jedis redis){
        this.redis = redis;
        return true;
    }

    public synchronized boolean registRedis(String addr, int port){
        // 连接Redis服务器
        return registRedis(addr, port, null);
    }

    public synchronized boolean registRedis(String addr, int port, String password){
        this.addr = addr;
        this.port = port;
        this.password = password;

        return connectRedis();
    }

    public synchronized boolean connectRedis(){
        // 连接Redis服务器
        try {
            if ($.isEmptyOrNull(addr)) {
                return false;
            } else {
                redis = new Jedis(addr, port);
                // 设置密码
                if(!$.isEmptyOrNull(password)) redis.auth(password);
            }

            redis.connect();

            $.info("[Cache] Regist Redis cache success!["+redis.dbSize()+"]");
            return true;
        } catch (Exception e){
//            redis = null;
            $.warn("[Cache] Regist Redis cache ERROR! Use default local Cache!"+e);
        }

        return false;
    }

    public synchronized boolean reConnectRedis(){
        if(null == redis) return false;

        // 连接Redis服务器
        if(redis.isConnected()) redis.close();

        return connectRedis();
    }

    public boolean testRedis(){
        if(null == redis) return false;

        try {
            redis.dbSize();
            return true;
        } catch (JedisConnectionException e) {
            try {
                $.warn("[Cache] Redis is Not connect! Try reConnect! ["+e+"]");
                return reConnectRedis();
            } catch (Exception e1){
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public synchronized Jedis getRedis() {
        return redis;
    }

    public synchronized void close(){
        clear();

        // 关闭连接
        if(testRedis()) redis.close();
    }


    public synchronized void put(String key, $EntityCache value) {
        caches.put(key, value);

        if(testRedis()) { // 如果redis不为空，则将数据存入redis
            try {
                Object o = value.getData();

                if(String.class.equals(o.getClass())){
                    getRedis().set(key, (String)o);
                } else {
                    return;
                }

                value.setData(null); // 设置数据为空
            } catch (Exception e){ // 数据不支持redis，则不做任何动作
            }
        }
    }


    public synchronized  $EntityCache get(String key) {
        if(testRedis()) {
            String o = getRedis().get(key);
            if(null != o && null != caches.get(key)) {
                caches.get(key).setData(o);
            }
        }

        return caches.get(key);
    }


    public synchronized boolean clear() {
        keySet().forEach(key-> remove(key));

        return true;
    }


    public synchronized long size() {
        return caches.size();
    }


    public synchronized Set<String> keySet() {
        return caches.keySet();
    }


    public synchronized void remove(String key) {
        caches.remove(key);

        if(testRedis()) {
            getRedis().del(key);
        }
    }

    public synchronized boolean containsKey(String key) {
        return caches.containsKey(key);
    }
}
