package com.ailbb.ajj.sys;


import com.ailbb.ajj.$;
import java.util.Date;


public class $SysLoadState {

    /**
     *
     */
    private static final long serialVersionUID = -4863071148000213553L;

    private String id;

    /**
     * host名称
     */
    private String hostname;

    /**
     * 1分钟之前到现在的负载
     */
    private Double oneLoad;

    /**
     * 5分钟之前到现在的负载
     */
    private Double fiveLoad;

    /**
     * 15分钟之前到现在的负载
     */
    private Double fifteenLoad;

    /**
     * 登录用户数量 废弃
     */
    private String users;

    /**
     * 添加时间
     * yyyy-MM-dd hh:mm:ss
     */
    private String dateStr;

    /**
     * 创建时间
     */
    private Date createTime;

    public $SysLoadState(){
        this.id = $.snowflakeIdStr();
    }


    public Double getOneLoad() {
        return oneLoad;
    }

    public void setOneLoad(Double oneLoad) {
        this.oneLoad = oneLoad;
    }

    public Double getFiveLoad() {
        return fiveLoad;
    }

    public void setFiveLoad(Double fiveLoad) {
        this.fiveLoad = fiveLoad;
    }

    public Double getFifteenLoad() {
        return fifteenLoad;
    }

    public void setFifteenLoad(Double fifteenLoad) {
        this.fifteenLoad = fifteenLoad;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("$SysLoadState{");
        sb.append("id='").append(id).append('\'');
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", oneLoad=").append(oneLoad);
        sb.append(", fiveLoad=").append(fiveLoad);
        sb.append(", fifteenLoad=").append(fifteenLoad);
        sb.append(", users='").append(users).append('\'');
        sb.append(", dateStr='").append(dateStr).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}