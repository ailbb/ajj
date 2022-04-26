package com.ailbb.ajj.cache;

import java.util.Map;
import java.util.Set;

public interface $CacheManage {
    /**
     * 存入缓存
     * @param key 缓存的key
     * @param cache 缓存对象
     */
    $EntityCache save(String key, $EntityCache cache);

    /**
     * 存入缓存
     * @param key 缓存的key
     * @param data 缓存数据
     * @param timeOut 缓存时长
     */
    <T> T save(String key, T data, long timeOut);

    /**
     * 获取对应缓存
     * @param key 缓存的key
     * @return 缓存对象
     */
    $EntityCache getCache(String key);

    /**
     * 获取对应缓存
     * @param key 缓存的key
     * @return 缓存数据
     */
    Object getCacheData(String key);

    /**
     * 获取所有缓存
     * @return 所有缓存
     */
    Map<String, $EntityCache> getCacheAll();

    /**
     * 判断是否在缓存中
     * @param key 缓存的key
     * @return 是否有缓存key
     */
    boolean isContains(String key);

    /**
     * 清除所有缓存
     */
    void clearAll();

    /**
     * 清除对应缓存
     * @param key 缓存的key
     */
    void clearByKey(String key);

    /**
     * 缓存是否超时失效
     * @param key 缓存的key
     * @return boolean
     */
    boolean isExpires(String key);

    /**
     * @return 获取所有key
     */
    Set<String> getAllKeys();
}
