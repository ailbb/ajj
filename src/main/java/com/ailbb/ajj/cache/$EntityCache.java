package com.ailbb.ajj.cache;

import com.ailbb.ajj.$;

import java.util.Date;

public class $EntityCache <T> {
    /**
     * 数据
     */
    private T data;

    /**
     * 过期时间
     */
    private long timeOut;

    /**
     * 过期时间
     */
    long expiresTime;

    /**
     * 数据更新时间
     */
    Date lastUpdateTime;

    /**
     * 自动延时接口
     */
    private $AutoDelayRunnable<T> autoDelayRunnable;

    public $EntityCache(T data, long timeOut) {
        this(data, null , timeOut);
    }

    public $EntityCache(T data, $AutoDelayRunnable autoDelayRunnable, long timeOut) {
        this.data = data;
        this.autoDelayRunnable = autoDelayRunnable;
        this.timeOut = timeOut;
        expiresTime = System.currentTimeMillis() + timeOut;
        lastUpdateTime = new Date();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        lastUpdateTime = new Date();
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isExpires() {
        return expiresTime - System.currentTimeMillis() < 0;
    }
    public boolean delayTime() {
        expiresTime = System.currentTimeMillis() + timeOut;

        if(null != autoDelayRunnable) {
            $.async(() -> { // 重新加载数据
                // 如果时间已经过半, 并且自动延期,  则重新加载数据
                setData(autoDelayRunnable.loadData()); // 延期时候，重新加载数据
            });
        }

        return true;
    }

    /**
     * 已经不足一半有效期
     * @return
     */
    public boolean isHalfExpires() {
        boolean halfExpires = (expiresTime - System.currentTimeMillis()) < timeOut/2;
        return halfExpires;
    }

    public $AutoDelayRunnable<T> getAutoDelayRunnable() {
        return autoDelayRunnable;
    }

    public void setAutoDelayRunnable($AutoDelayRunnable<T> autoDelayRunnable) {
        this.autoDelayRunnable = autoDelayRunnable;
        lastUpdateTime = new Date();
    }
}