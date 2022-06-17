package com.ailbb.ajj.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class $CacheManagerImpl implements $CacheManage {
    private static Map<String, $EntityCache> caches = new ConcurrentHashMap<String, $EntityCache>();
    private $CacheListener cacheListener;
    public static long TIME_OUT = 1000*60*60;

    /**
     * 获取数据, 并自动延期
     * @param key 缓存的key
     * @param delayRunnable 自动延迟缓存类
     */
    public synchronized <T> T autoGetSaveData(String key, $AutoDelayRunnable<T> delayRunnable) {
        return autoGetSaveData(key, delayRunnable, TIME_OUT);
    }

    /**
     * 获取数据, 并自动延期
     * @param key 缓存的key
     * @param delayRunnable 自动延迟缓存类
     */
    public synchronized <T> T autoGetSaveData(String key, $AutoDelayRunnable<T> delayRunnable, long TIME_OUT) {
        if(null == getCache(key)) {
            save(key, new $EntityCache(delayRunnable.loadData(), delayRunnable, TIME_OUT));
        }

        return (T)getCacheData(key);
    }

    /**
     * 存入缓存
     * @param key 缓存的key
     * @param cache 缓存对象
     */
    public $EntityCache save(String key, $EntityCache cache) {
        if(null == cacheListener) cacheListener = new $CacheListener(this).startListen();
        caches.put(key, cache);
        return cache;
    }

    /**
     * 存入缓存
     * @param key 缓存的key
     * @param data 缓存数据
     */
    public <T> T save(String key, T data) {
        return save(key, data, TIME_OUT);
    }
    /**
     * 存入缓存
     * @param key 缓存的key
     * @param data 缓存数据
     * @param timeOut 缓存时长
     */
    public <T> T save(String key, T data, long timeOut) {
        timeOut = timeOut > 0 ? timeOut : 0L;
        save(key, new $EntityCache(data, timeOut));
        return data;
    }

    /**
     * 获取对应缓存
     * @param key 缓存的key
     * @return 缓存对象
     */
    public $EntityCache getCache(String key) {
        return this.isContains(key) ? caches.get(key) : null;
    }

    /**
     * 获取对应缓存
     * @param key 缓存的key
     * @return 缓存数据
     */
    public Object getCacheData(String key) {
        if (this.isContains(key)) {
            return caches.get(key).getData();
        }
        return null;
    }

    /**
     * 获取所有缓存
     * @return 所有缓存
     */
    public Map<String, $EntityCache> getCacheAll() {
        return caches;
    }

    /**
     * 判断是否在缓存中
     * @param key 缓存的key
     * @return 是否有缓存key
     */
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public void clearAll() {
        caches.clear();
        cacheListener.stopListen();
    }

    /**
     * 清除对应缓存
     * @param key 缓存的key
     */
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            caches.remove(key);
        }

        if(caches.size() == 0) cacheListener.stopListen();
    }

    /**
     * 缓存是否超时失效
     * @param key 缓存的key
     * @return boolean
     */
    public boolean isExpires(String key) {
        return !caches.containsKey(key) ? true : caches.get(key).isExpires();
    }

    /**
     * 缓存是否超时失效
     * @param key 缓存的key
     * @return boolean
     */
    public boolean isHalfExpires(String key) {
        return !caches.containsKey(key) ? true : caches.get(key).isHalfExpires();
    }

    /**
     * 缓存延期
     * @param key 缓存的key
     * @return boolean
     */
    public boolean delayTime(String key) {
        return !caches.containsKey(key) ? true : caches.get(key).delayTime();
    }

    /**
     * 获取所有key
     * @return
     */
    public Set<String> getAllKeys() {
        return caches.keySet();
    }
}