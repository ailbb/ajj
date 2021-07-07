package com.ailbb.ajj.file;

import com.ailbb.ajj.$;

import java.util.HashMap;
import java.util.Map;

public class $DelayCloseable implements Runnable {
    static Map<String, $DelayCloseable> closeCache = new HashMap<>();

    private AutoCloseable autoCloseable;
    private int dealyTimeOut;

    public static void doDelayCloseable(AutoCloseable closeable, long dealyTimeOut, String flagKey){
        if(null == closeCache.get(flagKey)) // 如果没有缓存信息，则初始化一个
            closeCache.put(flagKey, new $DelayCloseable(closeable, dealyTimeOut));
        else // 否则重复延时，则刷新延时时间
            closeCache.get(flagKey).resetDealyTimeOut(dealyTimeOut);
    }

    /*
     * 延时关闭流信息
     * @param closeable
     * @param dealyTimeOut
     */
    private $DelayCloseable(AutoCloseable closeable, long dealyTimeOut) {
        this.autoCloseable = closeable;
        this.dealyTimeOut = Math.round(dealyTimeOut/100);
        this.startDelayCloseable();
    }


    private void startDelayCloseable(){
        new Thread(this).start();
    }

    public $DelayCloseable resetDealyTimeOut(long dealyTimeOut){
        this.dealyTimeOut = Math.round(dealyTimeOut/100);
        return this;
    }

    @Override
    public void run() {
        try {
            while (true){
                if(dealyTimeOut--<0) {
                    $.file.closeStearm(this.autoCloseable);
                    break;
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            $.error("失败！",e);
            $.file.closeStearm(this.autoCloseable);
        }
    }
}
