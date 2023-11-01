package com.ailbb.ajj.cache;

import com.ailbb.ajj.$;

public class $CacheListener{
    private $CacheManagerImpl cacheManagerImpl;
    private boolean flag = true;

    public $CacheListener($CacheManagerImpl cacheManagerImpl) {
        this.cacheManagerImpl = cacheManagerImpl;
    }

    public $CacheListener startListen() {
        $.async(() -> {
            while (flag) {
                for(String key : cacheManagerImpl.getAllKeys()) {
                    if (cacheManagerImpl.isExpires(key)) {
                        cacheManagerImpl.clearByKey(key);
                        $.info("[CACHE]{"+key + "}缓存过期，被清除。");
                    } else if (cacheManagerImpl.isHalfExpires(key) && null != cacheManagerImpl.getCache(key).getAutoDelayRunnable()) {
                        // 重新加载数据
                        $.debugOut("[CACHE]{"+key + "}有效期不足, 继续延期.");
                        cacheManagerImpl.delayTime(key); // 缓存延期
                    }
                }
            }
        });

        return this;
    }

    public void stopListen() {
        this.flag = false;
    }
}