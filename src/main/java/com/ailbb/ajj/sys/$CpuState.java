package com.ailbb.ajj.sys;


import com.ailbb.ajj.$;
import java.util.Date;


public class $CpuState {

    /**
     *
     */
    private static final long serialVersionUID = -2913111613773445949L;

    private String id;

    /**
     * host名称
     */
    private String hostname;

    /**
     * 用户态的CPU时间（%）废弃
     */
    private String user;

    /**
     * cpu使用率
     */
    private Double sys;

    /**
     * 当前空闲率
     */
    private Double idle;

    /**
     * cpu当前等待率
     */
    private Double iowait;

    /**
     * 硬中断时间（%） 废弃
     */
    private String irq;

    /**
     * 软中断时间（%） 废弃
     */
    private String soft;

    /**
     * 添加时间
     * MM-dd hh:mm:ss
     */
    private String dateStr;

    /**
     * 创建时间
     */
    private Date createTime;

    public $CpuState(){
        this.id = $.snowflakeIdStr();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getSys() {
        return sys;
    }

    public void setSys(Double sys) {
        this.sys = sys;
    }

    public Double getIdle() {
        return idle;
    }

    public void setIdle(Double idle) {
        this.idle = idle;
    }

    public Double getIowait() {
        return iowait;
    }

    public void setIowait(Double iowait) {
        this.iowait = iowait;
    }

    public String getIrq() {
        return irq;
    }

    public void setIrq(String irq) {
        this.irq = irq;
    }

    public String getSoft() {
        return soft;
    }

    public void setSoft(String soft) {
        this.soft = soft;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDateStr() {
        return this.dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "CpuState{" +
                "hostname='" + hostname + '\'' +
                ", user='" + user + '\'' +
                ", sys=" + sys +
                ", idle=" + idle +
                ", iowait=" + iowait +
                ", irq='" + irq + '\'' +
                ", soft='" + soft + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}