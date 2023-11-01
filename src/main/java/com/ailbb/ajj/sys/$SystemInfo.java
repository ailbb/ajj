package com.ailbb.ajj.sys;

import com.ailbb.ajj.$;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class $SystemInfo {

    private static final long serialVersionUID = 1L;
    private String id;

    // 主机名
    private String hostname;
    // 主机平台
    private String platForm;

    private String platformVersion;
    // 更新时间
    private Long uptime;
    private String uptimeStr;
    private Long bootTime;
    private String bootTimeStr;
    private String procs;
    private Double memPer;
    private int cpuCoreNum;
    private Double cpuPer;
    private String cpuXh;
    private String state;
    private String agentVer;
    private Date createTime;
    private String remark;
    private String totalMem;
    private String submitSeconds;
    private String bytesRecv; // 网卡：每秒接收的数据包
    private String bytesSent; // 网卡：每秒发送的数据包
    private String rxbyt; // 网卡：每秒接收的字节数
    private String txbyt; // 网卡：每秒发送的字节数
    private String winConsole;
    private String hostnameExt;
    private Double fiveLoad;
    private Double fifteenLoad;
    private int netConnections;
    private String groupId;
    private String account;
    private long totalSwapMem;
    private long swapMemPer;
    private String groupName;
    private String portName;
    private String dockerName;
    private String fileWarnName;
    private String image;
    private Double diskPer;
    private String selected;
    private Integer warnCount;
    private String warnQueryWd;
    private String versionDetail;

    public $SystemInfo(){
        this.id = $.snowflakeIdStr();
    }

    public String getVersionDetail() {
        return versionDetail;
    }

    public void setVersionDetail(String versionDetail) {
        this.versionDetail = versionDetail;
    }

    public String getHostname()
    {
        return hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public String getPlatForm()
    {
        return platForm;
    }

    public void setPlatForm(String platForm)
    {
        this.platForm = platForm;
    }

    public String getPlatformVersion()
    {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion)
    {
        this.platformVersion = platformVersion;
    }

    public Long getUptime()
    {
        return uptime;
    }

    public void setUptime(Long uptime)
    {
        this.uptime = uptime;
    }

    public Long getBootTime()
    {
        return bootTime;
    }

    public void setBootTime(Long bootTime)
    {
        this.bootTime = bootTime;
    }

    public Double getMemPer()
    {
        if(null == memPer)
            memPer = Double.valueOf(0.0D);
        return memPer;
    }

    public void setMemPer(Double memPer)
    {
        this.memPer = memPer;
    }

    public int getCpuCoreNum()
    {
        return cpuCoreNum;
    }

    public void setCpuCoreNum(int cpuCoreNum)
    {
        this.cpuCoreNum = cpuCoreNum;
    }

    public Double getCpuPer()
    {
        if(null == cpuPer)
            cpuPer = Double.valueOf(0.0D);
        return cpuPer;
    }

    public void setCpuPer(Double cpuPer)
    {
        this.cpuPer = cpuPer;
    }

    public String getCpuXh()
    {
        return cpuXh;
    }

    public void setCpuXh(String cpuXh)
    {
        this.cpuXh = cpuXh;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getProcs()
    {
        return procs;
    }

    public void setProcs(String procs)
    {
        this.procs = procs;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getTotalMem()
    {
        return totalMem;
    }

    public void setTotalMem(String totalMem)
    {
        this.totalMem = totalMem;
    }

    public String getUptimeStr()
    {
        return this.uptimeStr;
    }

    public void setUptimeStr(String uptimeStr)
    {
        this.uptimeStr = uptimeStr;
    }

    public String getBootTimeStr()
    {
        return this.bootTimeStr;
    }

    public void setBootTimeStr(String bootTimeStr)
    {
        this.bootTimeStr = bootTimeStr;
    }

    public String getSubmitSeconds()
    {
        if(StringUtils.isEmpty(submitSeconds))
            submitSeconds = "120";
        return submitSeconds;
    }

    public void setSubmitSeconds(String submitSeconds)
    {
        this.submitSeconds = submitSeconds;
    }

    public String getAgentVer()
    {
        return agentVer;
    }

    public void setAgentVer(String agentVer)
    {
        this.agentVer = agentVer;
    }

    public String getPortName()
    {
        return portName;
    }

    public void setPortName(String portName)
    {
        this.portName = portName;
    }

    public String getDockerName()
    {
        return dockerName;
    }

    public void setDockerName(String dockerName)
    {
        this.dockerName = dockerName;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getFileWarnName()
    {
        return fileWarnName;
    }

    public void setFileWarnName(String fileWarnName)
    {
        this.fileWarnName = fileWarnName;
    }

    public Double getDiskPer()
    {
        return diskPer;
    }

    public void setDiskPer(Double diskPer)
    {
        this.diskPer = diskPer;
    }

    public String getBytesRecv()
    {
        return bytesRecv;
    }

    public void setBytesRecv(String bytesRecv)
    {
        this.bytesRecv = bytesRecv;
    }

    public String getBytesSent()
    {
        return bytesSent;
    }

    public void setBytesSent(String bytesSent)
    {
        this.bytesSent = bytesSent;
    }

    public String getRxbyt()
    {
        if(StringUtils.isEmpty(rxbyt))
            rxbyt = "0";
        return rxbyt;
    }

    public void setRxbyt(String rxbyt)
    {
        this.rxbyt = rxbyt;
    }

    public String getTxbyt()
    {
        if(StringUtils.isEmpty(txbyt))
            txbyt = "0";
        return txbyt;
    }

    public void setTxbyt(String txbyt)
    {
        this.txbyt = txbyt;
    }

    public String getWinConsole()
    {
        return winConsole;
    }

    public void setWinConsole(String winConsole)
    {
        this.winConsole = winConsole;
    }

    public String getSelected()
    {
        return selected;
    }

    public void setSelected(String selected)
    {
        this.selected = selected;
    }

    public String getHostnameExt()
    {
        return hostnameExt;
    }

    public void setHostnameExt(String hostnameExt)
    {
        this.hostnameExt = hostnameExt;
    }

    public Double getFiveLoad()
    {
        if(null == fiveLoad)
            fiveLoad = Double.valueOf(0.0D);
        return fiveLoad;
    }

    public void setFiveLoad(Double fiveLoad)
    {
        this.fiveLoad = fiveLoad;
    }

    public Double getFifteenLoad()
    {
        if(null == fifteenLoad)
            fifteenLoad = Double.valueOf(0.0D);
        return fifteenLoad;
    }

    public void setFifteenLoad(Double fifteenLoad)
    {
        this.fifteenLoad = fifteenLoad;
    }

    public Integer getWarnCount()
    {
        return warnCount;
    }

    public void setWarnCount(Integer warnCount)
    {
        this.warnCount = warnCount;
    }

    public int getNetConnections()
    {
        return netConnections;
    }

    public void setNetConnections(int netConnections)
    {
        this.netConnections = netConnections;
    }

    public long getTotalSwapMem()
    {
        return totalSwapMem;
    }

    public void setTotalSwapMem(long totalSwapMem)
    {
        this.totalSwapMem = totalSwapMem;
    }

    public long getSwapMemPer()
    {
        return swapMemPer;
    }

    public void setSwapMemPer(long swapMemPer)
    {
        this.swapMemPer = swapMemPer;
    }

    public String getWarnQueryWd()
    {
        return warnQueryWd;
    }

    public void setWarnQueryWd(String warnQueryWd)
    {
        this.warnQueryWd = warnQueryWd;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("$SystemInfo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", platForm='").append(platForm).append('\'');
        sb.append(", platformVersion='").append(platformVersion).append('\'');
        sb.append(", uptime=").append(uptime);
        sb.append(", uptimeStr='").append(uptimeStr).append('\'');
        sb.append(", bootTime=").append(bootTime);
        sb.append(", bootTimeStr='").append(bootTimeStr).append('\'');
        sb.append(", procs='").append(procs).append('\'');
        sb.append(", memPer=").append(memPer);
        sb.append(", cpuCoreNum=").append(cpuCoreNum);
        sb.append(", cpuPer=").append(cpuPer);
        sb.append(", cpuXh='").append(cpuXh).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", agentVer='").append(agentVer).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", totalMem='").append(totalMem).append('\'');
        sb.append(", submitSeconds='").append(submitSeconds).append('\'');
        sb.append(", bytesRecv='").append(bytesRecv).append('\'');
        sb.append(", bytesSent='").append(bytesSent).append('\'');
        sb.append(", rxbyt='").append(rxbyt).append('\'');
        sb.append(", txbyt='").append(txbyt).append('\'');
        sb.append(", winConsole='").append(winConsole).append('\'');
        sb.append(", hostnameExt='").append(hostnameExt).append('\'');
        sb.append(", fiveLoad=").append(fiveLoad);
        sb.append(", fifteenLoad=").append(fifteenLoad);
        sb.append(", netConnections=").append(netConnections);
        sb.append(", groupId='").append(groupId).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", totalSwapMem=").append(totalSwapMem);
        sb.append(", swapMemPer=").append(swapMemPer);
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", portName='").append(portName).append('\'');
        sb.append(", dockerName='").append(dockerName).append('\'');
        sb.append(", fileWarnName='").append(fileWarnName).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", diskPer=").append(diskPer);
        sb.append(", selected='").append(selected).append('\'');
        sb.append(", warnCount=").append(warnCount);
        sb.append(", warnQueryWd='").append(warnQueryWd).append('\'');
        sb.append(", versionDetail='").append(versionDetail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}