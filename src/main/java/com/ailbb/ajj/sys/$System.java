package com.ailbb.ajj.sys;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$CallbackRow;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.unit.$FormatUtil;
import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.util.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $System {
    public static $NetWorkTool netWork = new $NetWorkTool();

    public String system(){
        return System.getProperty("os.name").toLowerCase().startsWith("windows") ? "windows" : "linux";
    }

    public OperatingSystemMXBean operatingSystemMXBean(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        return operatingSystemMXBean;
    }

    public int cpu(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        return operatingSystemMXBean.getAvailableProcessors();
    }

    public long mem(){ // 获取内存管理MXBean
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        return osBean.getTotalMemorySize();
    }

    public MemoryUsage memHeapMemoryUsage(){ // 获取内存管理MXBean
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 获取堆内存使用情况
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        return heapMemoryUsage;
    }

    public MemoryUsage memNonHeapMemoryUsage(){ // 获取内存管理MXBean
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 获取堆内存使用情况
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        return nonHeapMemoryUsage;
    }


    /*
      "hostname": "192.168.7.243",
      "dateStr": "14:08:57",
      "idle": 199000.0,
      "createTime": "2023-09-18T06:08:57.000+00:00",
      "irq": "0",
      "pageSize": 20,
      "iowait": 0.0,
      "id": "1703652561212538880",
      "page": 1,
      "sys": 0.5,
      "user": "0",
      "soft": "0"
     */
    public $CpuState getCpuState() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        $CpuState o = new $CpuState();
        o.setHostname($.getIp());
        o.setDateStr($.now("n"));
        o.setCreateTime(new Date());
        o.setIrq("0");
        o.setIowait(0.0);

        // 过滤不合理数值
        while (null==o.getIdle() || o.getIdle()<=0){
            o.setIdle(Double.valueOf(osBean.getCpuLoad())); // CPU负荷
        }

        if(!$.system().equals("windows")) {
            o.setIdle(o.getIdle()/$.cpu());
        }

        // 过滤不合理数值
        while (null==o.getSys() || o.getSys()<=0){
            o.setSys(osBean.getSystemLoadAverage()); // CPU负荷
            if(o.getSys() == -1) o.setSys(o.getIdle()); // CPU负荷
        }

        o.setIdle(o.getIdle()*100);
        o.setUser("0"); // 用户
        o.setSoft(String.valueOf(osBean.getAvailableProcessors())); // 用户运行的程序数
//        o.setSoft(String.valueOf(getProcessNums(null,null))); // 用户运行的程序数

        return o;
    }

    public $SysLoadState getSysLoadState(){
        $SysLoadState o = new $SysLoadState();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        while (null==o.getOneLoad() || o.getOneLoad()<=0){
            o.setOneLoad(osBean.getSystemLoadAverage()); // CPU负荷
            if(o.getOneLoad() <= 0) {
                double cpuload = Double.valueOf(osBean.getCpuLoad());

                if(!$.system().equals("windows")) {
                    cpuload = cpuload/$.cpu();
                }

                o.setOneLoad(cpuload); // CPU负荷
            }
        }
        o.setHostname($.getIp());
        o.setDateStr($.now("n"));
        o.setCreateTime(new Date());
        o.setFiveLoad(osBean.getSystemLoadAverage());
        o.setFifteenLoad(osBean.getSystemLoadAverage());
//        sysLoadState.setUsers();
        return o;
    }

    public Map<String, InetAddress> getNetworkLanIPs() { return netWork.getNetworkLanIPs(); }

    public Map<String, InetAddress> getNetworkLanIPs(String ip) { return netWork.getNetworkLanIPs(ip); }


    public $NetIoState getNetWork(){ return netWork.getNetWork(); }

    public $NetIoState getNetWork(String targetIp){ return netWork.getNetWork(targetIp); }

    public List<InetAddress> getLocalIps(){ return netWork.getLocalIps(); }

    public int getProcessNums(String userName, String processName) {
        BufferedReader reader =null;
        Runtime runtime = Runtime.getRuntime();
        List<String> tasklist = new ArrayList<String>();
        Process process=null;
        int count=0; //统计进程数

        try {
            /*
             * 自适应执行查询进程列表命令
             */
            Properties prop= System.getProperties();
            //获取操作系统名称
            String os= prop.getProperty("os.name");
            if (os != null && os.toLowerCase().indexOf("linux") > -1) {
                //1.适应与linux
                //显示所有进程
                process = Runtime.getRuntime().exec("ps -ef");
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                while((line = reader.readLine())!=null){
                    if(null == processName || line.contains(processName)){
                        count++;
                    }
                }
            } else {
                //2.适应与windows
                process = runtime.exec("tasklist");
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s = "";
                while ((s = reader.readLine()) != null) {
                    if ("".equals(s)) {
                        continue;
                    }
                    tasklist.add(s+" ");
                }

                // 获取每列最长的长度
                String maxRow = tasklist.get(1) + "";
                String[] maxCol = maxRow.split(" ");
                // 定义映像名称数组
                String[] taskName = new String[tasklist.size()];
                // 定义 PID数组
                String[] taskPid = new String[tasklist.size()];
                // 会话名数组
                String[] taskSessionName = new String[tasklist.size()];
                // 会话#数组
                String[] taskSession = new String[tasklist.size()];
                // 内存使用 数组
                String[] taskNec = new String[tasklist.size()];
                for (int i = 0; i < tasklist.size(); i++) {
                    String data = tasklist.get(i) + "";
                    for (int j = 0; j < maxCol.length; j++) {
                        switch (j) {
                            case 0:
                                taskName[i]=data.substring(0, maxCol[j].length()+1);
                                data=data.substring(maxCol[j].length()+1);
                                break;
                            case 1:
                                taskPid[i]=data.substring(0, maxCol[j].length()+1);
                                data=data.substring(maxCol[j].length()+1);
                                break;
                            case 2:
                                taskSessionName[i]=data.substring(0, maxCol[j].length()+1);
                                data=data.substring(maxCol[j].length()+1);
                                break;
                            case 3:
                                taskSession[i]=data.substring(0, maxCol[j].length()+1);
                                data=data.substring(maxCol[j].length()+1);
                                break;
                            case 4:
                                taskNec[i]=data;
                                break;
                        }
                    }
                }

                for (int i = 0; i < taskNec.length; i++) {
                    //打印进程列表
                    if(null == processName || taskName[i].contains(processName)){
                        count++;//用于进程计数
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            $.closeStream(reader);
        }
        return count;

    }

    /*
      "hostname": "192.168.7.243",
      "total": "31GB",
      "dateStr": "14:08:57",
      "createTime": "2023-09-18T06:08:57.000+00:00",
      "pageSize": 20,
      "used": "1GB",
      "id": "1703652561313202176",
      "page": 1,
      "free": "29GB",
      "usePer": 6.2
     */
    public $MemState getMemState() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        $MemState o = new $MemState();
        o.setHostname($.getIp());
        o.setTotal($.unit.convert(osBean.getTotalMemorySize()));
        o.setDateStr($.now("n"));
        o.setCreateTime(new Date());
        o.setUsed($.unit.convert(osBean.getTotalMemorySize()-osBean.getFreeMemorySize()));
        o.setFree($.unit.convert(osBean.getFreeMemorySize()));
        o.setUsePer(100-(Double.valueOf(osBean.getFreeMemorySize())/Double.valueOf(osBean.getTotalMemorySize())*100));
        return o;
    }

    public $SystemInfo getSystemInfo(){
        Properties props = System.getProperties();
        RuntimeMXBean runtimeMX = ManagementFactory.getRuntimeMXBean();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        File tempDir = null;
        try {
            tempDir = File.createTempFile("tempDirTest", null);
        } catch (IOException e) {
            $.warn(e);
        }
        Runtime runtime = Runtime.getRuntime();

        $NetIoState nis = netWork.getNetWork();
        $SysLoadState sls = getSysLoadState();

        $SystemInfo s = new $SystemInfo();
        s.setHostname($.getIp());
        s.setPlatForm(props.getProperty("os.name"));
        s.setPlatformVersion(props.getProperty("os.version"));
        s.setUptime(System.currentTimeMillis()-runtimeMX.getStartTime());
        s.setUptimeStr($FormatUtil.timesToDay(s.getUptime()));
        s.setBootTime(runtimeMX.getStartTime());
        s.setBootTimeStr($.date.format(runtimeMX.getStartTime()));
        s.setProcs(String.valueOf(osBean.getAvailableProcessors()));
        s.setMemPer(100-(Double.valueOf(osBean.getFreeMemorySize())/Double.valueOf(osBean.getTotalMemorySize())*100));
        s.setCpuCoreNum($.system.cpu());
        while (null==s.getCpuPer() || s.getCpuPer()==0){
            s.setCpuPer(Double.valueOf(osBean.getCpuLoad()*100)); // CPU空闲时间
        }
        s.setCpuXh(props.getProperty("os.arch"));
        s.setState("1");
        s.setAgentVer(null);
        s.setCreateTime(new Date());
        s.setRemark("");
        s.setTotalMem($.unit.convert(osBean.getTotalMemorySize()));
        s.setSubmitSeconds(String.valueOf(osBean.getProcessCpuTime()));
        s.setBytesRecv("-1");
        s.setBytesSent("-1");
        s.setRxbyt(nis.getRxbyt());
        s.setTxbyt(nis.getTxbyt());
//                s.setWinConsole();
        s.setHostnameExt($.getHostName());
        s.setFiveLoad(sls.getFiveLoad());
        s.setFifteenLoad(sls.getFifteenLoad());
        s.setNetConnections(Integer.valueOf(nis.getNetConnections()));
//                s.setGroupId();
//                s.setAccount();
        s.setTotalSwapMem( osBean.getTotalSwapSpaceSize());
        s.setSwapMemPer( osBean.getFreeSwapSpaceSize());
//                s.setGroupName();
//                s.setPortName();
//                s.setDockerName();
//                s.setFileWarnName();
//                s.setImage();
        s.setDiskPer(null == tempDir ? 0 : (Double.valueOf(tempDir.getUsableSpace())/Double.valueOf(tempDir.getTotalSpace())*100));
//                s.setSelected();
//                s.setWarnCount();
//                s.setWarnQueryWd();
//                s.setVersionDetail();

        return s;
    }

    public $Result runCmd(String command) throws IOException {
        return runCmd(command, "");
    }

    public $Result runCmd(String command, String workdir) throws IOException {
        return runCmd(command, workdir, null);
    }

    public $Result runCmd(String command, $CallbackRow callback) throws IOException {
        return runCmd(command, callback, null, null);
    }

    public $Result runCmd(String command, String workdir, $CallbackRow callback) throws IOException {
        return runCmd(command, callback, null, workdir);
    }

    public void checkCmdTimeOut(Process finalPro, int timeout){
        long startTime = System.currentTimeMillis();

        $.async(()->{
            // 等待命令执行完成或超时
            boolean isTimeout = false;
            int count = 0;

            while (finalPro.isAlive()) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime > timeout) {
                    // 超时，终止进程
                    finalPro.destroy();
                    isTimeout = true;
                    break;
                }
            }

            if (isTimeout) {
                $.info("["+ finalPro.pid() + "] Command execution timed out");
            } else {
                // 检查命令的退出值
                int exitValue = finalPro.exitValue();
                $.debugOut("Exit Value: " + exitValue);
            }
        });

    }

    public $Result runCmd(String command, $CallbackRow callback, String defaultValue, String workdir) throws IOException {
        $Result rs = $.result();
        Process pro = null;
        BufferedReader input = null;
        int index = 0;

        try {
            Runtime r = Runtime.getRuntime();
            $.debugOut("[COMMAND] 执行命令："+command);


            if($.isEmptyOrNull(workdir)) {
                pro = r.exec(command);
            } else {
                File directory = $.getFile(workdir);

                ProcessBuilder processBuilder;
                if($.isLinux()) {
                    processBuilder = new ProcessBuilder("bash","-c",command);
                } else {
                    processBuilder = new ProcessBuilder(command);
                }
                processBuilder.directory(directory);
                pro = processBuilder.start();
            }

            checkCmdTimeOut(pro, 10*60*1000);

            input = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            String line = null;
            while((line=input.readLine())!=null){
                line = line.trim();
                index++;
                rs.addData(line);

                if(null != callback) callback.row(line,index);
            }

            // 如果结果是空的，并且默认值不为空，则循环默认值；
            if(index == 0 && !$.isEmptyOrNull(defaultValue)) {
                for(String line_1 : defaultValue.split("\n")){
                    line_1 = line_1.trim();
                    index++;
                    rs.addData(line_1);

                    if(null != callback) callback.row(line_1,index);
                }
            }
        } catch (IOException e){
            // 如果结果是空的，并且默认值不为空，则循环默认值；
            if(index == 0 && !$.isEmptyOrNull(defaultValue)) {
                for(String line_1 : defaultValue.split("\n")){
                    line_1 = line_1.trim();
                    index++;
                    rs.addData(line_1);

                    if(null != callback) callback.row(line_1,index);
                }
            } else {
                throw e;
            }
        } finally{
            $.closeStream(input);
            Optional.ofNullable(pro).ifPresent(p->p.destroy());
        }

        return rs;
    }
}
