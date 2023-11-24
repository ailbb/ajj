package com.ailbb.ajj.entity;

import com.ailbb.ajj.$;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Wz on 8/2/2018.
 */
public class $Progress {
    int type = 0; // 类型
    boolean start = false; // 当前是否开始
    long starTime = 0; // 启动时间
    boolean end = false; // 当前是否结束
    boolean running = false; // 当前是否正在
    double finish = 0; // 总进度完成度
    long current = 0; // 当前总量
    long total = 0; // 当前总量
    long usedTime = 0; // 用时（毫秒）
    boolean monitored = false;
    List<$Progress> childs = new ArrayList<>();
    double speed = 0;
    $Result result = new $Result(); // 结果集

    public $Progress(){}
    public $Progress(boolean started){ this.setStart(started); }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;

        if(current==0 && total==0) return;
        // 获取到总条数后，才开始计数
        this.setFinish(Double.valueOf(current)/Double.valueOf(total)*100);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    // 是否通过验证
    public boolean pass() {
        return !isRunning() && isEnd();
    }

    public boolean isRunning() {
        return running;
    }

    public $Progress setRunning(boolean running) {
        this.running = running;
        return this;
    }

    public int getType() {
        return type;
    }

    public $Progress setType(int type) {
        this.type = type;
        return this;
    }

    public boolean isStart() {
        return start;
    }

    public $Progress setStart(boolean start) {
        this.setStarTime(System.currentTimeMillis());
        this.setFinish(0);

        this.start = start;
        return this;
    }

    private $Progress startMonitor(){
        if(monitored) return this; // 如果已经开启监听事件，则不继续进行

        monitored = true;

        // 定期更新当前进度条的进度
        $.async(()->{
            while (!isEnd()) {
                long current = 0; // 当前总量
                long total = 0; // 当前总量

                for ($Progress p : this.childs) {
                    current+= p.getCurrent();
                    total += p.getTotal();
                };

                this.setTotal(total);
                this.setCurrent(current);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }

            monitored = false;
        });

        return this;
    }

    public long getStarTime() {
        return starTime;
    }

    public void setStarTime(long starTime) {
        this.starTime = starTime;
    }

    public boolean isEnd() {
        return end;
    }

    public $Progress setEnd(boolean end) {
        this.end = end;
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }

    public double getFinish() {
        return finish;
    }

    public $Progress setFinish(double finish) {
        long t = System.currentTimeMillis();
        long usedTime = t - starTime;

        this.setSpeed(Double.valueOf(finish - this.finish)*total / 100 / Double.valueOf((usedTime - this.usedTime))*1000); // 进度*总数/用时
        this.setRunning(!(finish == 0 || finish == 100));
        this.setEnd(finish==100);
        this.setUsedTime(usedTime);
        this.finish = finish;

        return this;
    }

    public $Result getResult() {
        return result;
    }

    public $Progress setResult($Result result) {
        this.result = result;
        return this;
    }

    public List<$Progress> getChilds() {
        return childs;
    }

    public void setChilds(List<$Progress> childs) {
        this.childs = childs;
    }

    public void addChild($Progress child) {
        this.childs.add(child);
        startMonitor();
    }
}
