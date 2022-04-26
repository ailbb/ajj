package com.ailbb.ajj.cache;

import com.ailbb.ajj.$;

public class $CacheListener{
    private $CacheManagerImpl cacheManagerImpl;
    private boolean flag = true;

    public $CacheListener($CacheManagerImpl cacheManagerImpl) {
        this.cacheManagerImpl = cacheManagerImpl;
    }

    public $CacheListener startListen() {
        new Thread(() -> {
            while (flag) {
                for(String key : cacheManagerImpl.getAllKeys()) {
                    if (cacheManagerImpl.isExpires(key)) {
                        cacheManagerImpl.clearByKey(key);
                        $.info(key + "缓存被清除");
                    } else if (cacheManagerImpl.isHalfExpires(key) && null != cacheManagerImpl.getCache(key).getAutoDelayRunnable()) {
                        // 如果时间已经过半, 并且自动延期,  则重新加载数据
                        new Thread(() -> {
                            $.info(key + "有效期不足, 继续延期.");
                            cacheManagerImpl.autoGetSaveData(key, cacheManagerImpl.getCache(key).getAutoDelayRunnable(), cacheManagerImpl.getCache(key).getTimeOut()); // 加载数据
                        }).start();
                    }
                }
            }
        }).start();

        return this;
    }

    public void stopListen() {
        this.flag = false;
    }
}