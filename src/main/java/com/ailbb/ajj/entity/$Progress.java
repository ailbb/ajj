package com.ailbb.ajj.entity;

/*
 * Created by Wz on 8/2/2018.
 */
public class $Progress {
    int type = 0; // 类型
    boolean start = false; // 是否开始
    boolean end = false; // 是否结束
    boolean running = false; // 是否正在
    int finish = 0; // 完成度
    $Result result = new $Result(); // 结果集

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
        this.start = start;
        return this.setFinish(0);
    }

    public boolean isEnd() {
        return end;
    }

    public $Progress setEnd(boolean end) {
        this.end = end;
        return this.setFinish(100);
    }

    public int getFinish() {
        return finish;
    }

    public $Progress setFinish(int finish) {
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
}
