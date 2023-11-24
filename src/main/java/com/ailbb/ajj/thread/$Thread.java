package com.ailbb.ajj.thread;

import com.ailbb.ajj.$;

import java.util.*;
import java.util.concurrent.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Thread<T> {
    Map<String, Thread> threadRunQueue;
    ExecutorService runThreadPoolExecutor;

    // 创建一个ScheduledExecutorService实例
    ScheduledExecutorService scheduledExecutor;

    int runThreadPoolSize; // 运行线程池大小
    int scheduledPoolSize; // 调度线程池大小
    TimeUnit unit = TimeUnit.MILLISECONDS; // 时间单位

    public $Thread(){
        this(Runtime.getRuntime().availableProcessors()*3,Runtime.getRuntime().availableProcessors()*3);
    }

    public $Thread(int runThreadPoolSize, int scheduledPoolSize){
        this.runThreadPoolSize = runThreadPoolSize;
        this.scheduledPoolSize = scheduledPoolSize;
        threadRunQueue = new HashMap<>();

        // 创建线程池
        runThreadPoolExecutor = Executors.newFixedThreadPool(runThreadPoolSize);
        scheduledExecutor = Executors.newScheduledThreadPool(scheduledPoolSize);
    }

    public static $Thread getInstance(){
        return new $Thread();
    }

    public static $Thread getInstance(int fixedThreadPoolSize, int scheduledThreadPoolSize){
        return new $Thread(fixedThreadPoolSize, scheduledThreadPoolSize);
    }

    $Thread shutdown(){
        this.runThreadPoolExecutor.shutdown();
        this.scheduledExecutor.shutdown();
        return this;
    }

    public Map<String, Thread> getRunThreadQueue(){
        for(String key : threadRunQueue.keySet()){
            if(!threadRunQueue.get(key).isAlive()) threadRunQueue.remove(key);
        }

        return threadRunQueue;
    }

    public int getRunThreadQueueActiveCount(){
        return getRunThreadQueue().size();
    }

    public int getRunPoolActiveCount(){
        return ((ThreadPoolExecutor)(this.runThreadPoolExecutor)).getActiveCount();
    }

    public int getScheduledPoolActiveCount(){
        return ((ThreadPoolExecutor)(this.scheduledExecutor)).getActiveCount();
    }

    public $ThreadTraCKer async(Runnable... rs){
        return async(false, rs);
    }

    public $ThreadTraCKer async(boolean daemon, Runnable... rs){
        // 否则执行执行
        $ThreadTraCKer runnerTraCKer = null;

        for(Runnable r : rs) {
            runnerTraCKer = new $ThreadTraCKer(r);
            if (daemon) {
                asyncThread(daemon, runnerTraCKer);
            } else {
                this.runThreadPoolExecutor.submit(runnerTraCKer);
            }
        }

        return runnerTraCKer;
    }

    public $ThreadTraCKer async(long delayTimeout, Runnable... rs){
        return async(false, delayTimeout, 0, rs);
    }

    public $ThreadTraCKer async(long delayTimeout, long intervalTime, Runnable... rs){
        return async(false, delayTimeout, intervalTime, rs);
    }
    public $ThreadTraCKer async(boolean daemon, long delayTimeout, Runnable... rs){
        return async(daemon, delayTimeout, 0, rs);
    }

    public $ThreadTraCKer async(boolean daemon, long delayTimeout, long intervalTime, Runnable... rs){

        // 如果是延时任务，交给延期任务执行
        if(intervalTime > 0) {
            return asyncIntervalRun(intervalTime, delayTimeout, rs);
        }

        // 如果是延时任务，交给延期任务执行
        else if(delayTimeout > 0) {
            return asyncTimeoutRun(delayTimeout, rs);
        }

        // 如果是非守护线程（默认执行），交给线程池去执行
        else return async(daemon, rs);
    }


    public Thread asyncThread(Runnable... rs){
        return asyncThread(false, rs);
    }

    public Thread asyncThread(boolean daemon, Runnable... rs){
        // 否则执行执行
        Thread thread = null;
        Map<String, Thread> _threadRunQueue = getRunThreadQueue();

        for(Runnable r : rs) {
            thread = new Thread(r);
            thread.setDaemon(daemon);
            thread.setName("ASYNC-"+thread.getId()+"-"+$.uuidStrNone(false));
            _threadRunQueue.put(thread.getName(), thread);
            thread.start();
        }


        return thread;
    }

    /**
     * 让一个任务延期执行
     * @param delayTimeout
     * @param rs
     */
    public $ThreadTraCKer asyncTimeoutRun(long delayTimeout, Runnable... rs){
        // 否则执行执行
        $ThreadTraCKer runnerTraCKer = null;
        // 使用schedule方法来安排在给定的延迟后执行命令
        // 这里将任务安排在5秒后执行
        for(Runnable r : rs) {
            runnerTraCKer = new $ThreadTraCKer(r);

            scheduledExecutor.schedule(runnerTraCKer, delayTimeout, unit);
        }

        return runnerTraCKer;
    }

    /**
     * 让一个任务延期执行
     * @param intervalTime 定期时间
     * @param rs 执行列表
     */
    public void asyncIntervalRun(long intervalTime, Runnable... rs){
        asyncIntervalRun(intervalTime, 0, rs);
    }

    /**
     * 让一个任务定时执行（重复）
     * @param intervalTime 定期执行时间
     * @param delayTimeout 延期执行
     * @param rs 执行列表
     */
    public $ThreadTraCKer asyncIntervalRun(long intervalTime, long delayTimeout, Runnable... rs){
        // 否则执行执行
        $ThreadTraCKer runnerTraCKer = null;

        // 使用schedule方法来安排在给定的延迟后执行命令
        // 这里将任务安排在5秒后执行
        for(Runnable r : rs) {
            runnerTraCKer = new $ThreadTraCKer(r);

            scheduledExecutor.scheduleAtFixedRate(runnerTraCKer, delayTimeout, intervalTime, unit);
        }

        return runnerTraCKer;
    }

    /*
     * 异步执行，且返回集合结果
     * @param rs
     * @return
     */
    Map<Integer, T> asyncAndReturnMap($Runnable<T>... rs) throws Exception {
        return asyncAndReturnMap(3*60*1000, rs);
    }

    /*
     * 异步执行，且返回集合结果
     * @param rs
     * @return
     */
    Map<Integer, T> asyncAndReturnMap(long timeOut, $Runnable<T>... rs) throws Exception {
        Map<Integer, T> result = new TreeMap<>();

        for(int i=rs.length; i-->0;){
            $Runnable<T> r = rs[i];
            final int j = i;

            $.async(new Runnable() {
                @Override
                public void run() {
                    result.put(j, r.run());
                }
            });
        }

        long t = System.currentTimeMillis(); // 当前时间

        while (result.size() != rs.length) {
            Thread.sleep(1000);
            $.sout("已完成任务数：" + result.size() + "!=" + " 总队列：" +rs.length);
            if(System.currentTimeMillis() - t > timeOut) throw new TimeoutException("等待连接异常！");
        };

        return result;
    }

    /*
     * 异步执行，且返回数组结果
     * @param rs
     * @return
     */
    List<T> asyncAndReturnList($Runnable<T>... rs) throws Exception {
        Map<Integer, T> result = asyncAndReturnMap(rs);
        return $.list.toList(result.values());
    }

    /*
     * 异步执行，且返回结果
     * @param rs
     * @return
     */
    List<T> asyncAndReturn($Runnable<T>... rs) throws Exception {
        return asyncAndReturnList(rs);
    }


    public void asyncInterval(long intervalTime, Runnable... rs){
        interval(0, intervalTime, rs);
    }

    public void asyncTimeout(long delayTimeout, Runnable... rs){
        async(delayTimeout, rs);
    }

    public void timeout(long delayTimeout, Runnable... rs){
        async(delayTimeout, rs);
    }

    public void interval(long intervalTime, Runnable... rs){
        interval(0, intervalTime, rs);
    }

    public void interval(long delayTimeout, long intervalTime, Runnable... rs){
        async(delayTimeout, intervalTime, rs);
    }

    public ExecutorService getRunThreadPoolExecutor() {
        return runThreadPoolExecutor;
    }

    public void setRunThreadPoolExecutor(ExecutorService runThreadPoolExecutor) {
        this.runThreadPoolExecutor = runThreadPoolExecutor;
    }

    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    public void setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
        this.scheduledExecutor = scheduledExecutor;
    }

    public int getRunThreadPoolSize() {
        return runThreadPoolSize;
    }

    public void setRunThreadPoolSize(int runThreadPoolSize) {
        this.runThreadPoolSize = runThreadPoolSize;
    }

    public int getScheduledPoolSize() {
        return scheduledPoolSize;
    }

    public void setScheduledPoolSize(int scheduledPoolSize) {
        this.scheduledPoolSize = scheduledPoolSize;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
}
