package com.ailbb.ajj.cache;

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
     * 自动延时接口
     */
    private $AutoDelayRunnable<T> autoDelayRunnable;

    public $EntityCache(T data, long timeOut) {
        this.data = data;
        this.timeOut = timeOut;
        expiresTime = System.currentTimeMillis() + timeOut;
    }

    public $EntityCache(T data, $AutoDelayRunnable autoDelayRunnable, long timeOut) {
        this.data = data;
        this.autoDelayRunnable = autoDelayRunnable;
        this.timeOut = timeOut;
        expiresTime = System.currentTimeMillis() + timeOut;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    /**
     * 已经不足一半有效期
     * @return
     */
    public boolean isHalfExpires() {
        return (expiresTime - System.currentTimeMillis()) < timeOut/2;
    }

    public $AutoDelayRunnable<T> getAutoDelayRunnable() {
        return autoDelayRunnable;
    }

    public void setAutoDelayRunnable($AutoDelayRunnable<T> autoDelayRunnable) {
        this.autoDelayRunnable = autoDelayRunnable;
    }
}