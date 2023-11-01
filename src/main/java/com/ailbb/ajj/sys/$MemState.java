package com.ailbb.ajj.sys;


import com.ailbb.ajj.$;
import java.util.Date;


public class $MemState {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;


    /**
     * host名称
     */
    private String hostname;

    /**
     * 总计内存，M
     */
    private String total;

    /**
     * 已使用多少，M
     */
    private String used;

    /**
     * 未使用，M
     */
    private String free;

    /**
     * 已使用百分比%
     */
    private Double usePer;

    /**
     * 添加时间
     * yyyy-MM-dd hh:mm:ss
     */
    private String dateStr;

    /**
     * 创建时间
     */
    private Date createTime;

    public $MemState(){
        this.id = $.snowflakeIdStr();
    }


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
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

    public Double getUsePer() {
        return usePer;
    }

    public void setUsePer(Double usePer) {
        this.usePer = usePer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("$MemState{");
        sb.append("id='").append(id).append('\'');
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", total='").append(total).append('\'');
        sb.append(", used='").append(used).append('\'');
        sb.append(", free='").append(free).append('\'');
        sb.append(", usePer=").append(usePer);
        sb.append(", dateStr='").append(dateStr).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}