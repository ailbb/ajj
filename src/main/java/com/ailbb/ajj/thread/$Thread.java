package com.ailbb.ajj.thread;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Thread {
    public void async(Runnable r){
        new Thread(r).start();
    }
}
