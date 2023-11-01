package com.ailbb.ajj.sys;

public class $NetTestState {
    private double loss = 100;
    private double delay = 9999;
    private int status = -1;

    private String targetIp = "";

    public $NetTestState(String targetIp){ this.targetIp = targetIp; }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "$NetTestState{" +
                "loss=" + loss +
                ", delay=" + delay +
                ", status=" + status +
                '}';
    }
}

