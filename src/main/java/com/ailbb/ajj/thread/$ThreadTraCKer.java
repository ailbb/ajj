package com.ailbb.ajj.thread;

import com.ailbb.ajj.$;

/*
 * Created by Wz on 6/20/2018.
 */
public class $ThreadTraCKer implements Runnable {
    boolean start = false;
    Thread thread;
    Runnable runnable;
    $ThreadTraCKerRunner startRunner;
    $ThreadTraCKerRunner callbackRunner;

    public $ThreadTraCKer(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        // 在这里使用 name 字段
        this.start = true;
        this.thread = Thread.currentThread();

        $.debugOut(thread.getId()+"已经启动！");

        if(null != startRunner) startRunner.run(this);
        this.runnable.run();
        if(null != callbackRunner) callbackRunner.run(this);
        $.debugOut(thread.getId()+"已经停止！");
    }

    public Thread getThread() throws NullPointerException {
        if(start) return thread;

        throw new IllegalThreadStateException("Thread is Not Start.");
    }

    public void threadStartRun($ThreadTraCKerRunner callback) throws NullPointerException {
        this.startRunner = callback;
    }

    public void threadCallBackRun($ThreadTraCKerRunner callback) throws NullPointerException {
        this.callbackRunner = callback;
    }

    public boolean isStart() {
        return start;
    }

}