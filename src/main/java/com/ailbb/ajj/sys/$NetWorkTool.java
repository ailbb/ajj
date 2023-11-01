package com.ailbb.ajj.sys;


import com.ailbb.ajj.$;
import com.ailbb.ajj.date.$Timeclock;
import com.ailbb.ajj.entity.$CallbackRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网速相关信息
 *
 * @author huasheng
 */
public class $NetWorkTool {

    /**
     * 网速测速时间2s
     */
    public  final int SLEEP_SECOND = 4;

    public synchronized $NetIoState getNetWork(){return getNetWork(null); }

    public synchronized $NetIoState getNetWork(String targetIp){
        $NetIoState netIoState = new $NetIoState();
        $NetTestState netTestState1 = null;
        $NetTestState netTestState2 = null;
        try {
            $.debugOut("[NETWORK]获取网络状态-1:"+targetIp);
            $NetIoState netIoStateStart = readNetInLine();

            if(!$.isEmptyOrNull(targetIp)) {
                $.debugOut("[NETWORK]测试Ping状态-1:"+targetIp);
                netTestState1 = getNetTestStateByPing(targetIp);
            }

            Thread.sleep(SLEEP_SECOND*1000);

            $.debugOut("[NETWORK]获取网络状态-2:"+targetIp);
            $NetIoState netIoStateEnd = readNetInLine();
            if(!$.isEmptyOrNull(targetIp)) {
                $.debugOut("[NETWORK]测试Ping状态-2:"+targetIp);
                netTestState2 = getNetTestStateByPing(targetIp);
            }
            netIoState.setDateStr($.now("n"));
            netIoState.setNetConnections(String.valueOf(getNetConnections()));

            netIoState.setReceivedBytes(netIoStateEnd.getReceivedBytes() - netIoStateStart.getReceivedBytes()); // 上行速率(kB/s)
            netIoState.setSendBytes(netIoStateEnd.getSendBytes() - netIoStateStart.getSendBytes()); // 下行速率(kB/s)
            netIoState.setReceivedNonUnicastPackets(netIoStateEnd.getReceivedNonUnicastPackets() - netIoStateStart.getReceivedNonUnicastPackets());
            netIoState.setReceivedUnicastPackets(netIoStateEnd.getReceivedUnicastPackets() - netIoStateStart.getReceivedUnicastPackets());
            netIoState.setSendUnicastPackets(netIoStateEnd.getSendUnicastPackets() - netIoStateStart.getSendUnicastPackets());
            netIoState.setReceivedErrors(netIoStateEnd.getReceivedErrors() - netIoStateStart.getReceivedErrors());
            netIoState.setSendErrors(netIoStateEnd.getSendErrors() - netIoStateStart.getSendErrors());
            netIoState.setSendNonUnicastPackets(netIoStateEnd.getSendNonUnicastPackets() - netIoStateStart.getSendNonUnicastPackets());
            netIoState.setReceivedUnknownProtocols(netIoStateEnd.getReceivedUnknownProtocols() - netIoStateStart.getReceivedUnknownProtocols());
            netIoState.setDropin(netIoStateEnd.getDropin() - netIoStateStart.getDropin());
            netIoState.setDropout(netIoStateEnd.getDropout() - netIoStateStart.getDropout());

            netIoState.setTxbyt(formatNumber(netIoState.getReceivedBytes(), SLEEP_SECOND)); // 上行速率(kB/s)
            netIoState.setRxbyt(formatNumber(netIoState.getSendBytes(), SLEEP_SECOND)); // 下行速率(kB/s)
            netIoState.setRxpck(formatNumber(netIoState.getReceivedUnicastPackets(), SLEEP_SECOND));
            netIoState.setTxpck(formatNumber(netIoState.getSendUnicastPackets(), SLEEP_SECOND));
            netIoState.setErrorin(formatNumber(netIoState.getReceivedErrors(), SLEEP_SECOND));
            netIoState.setErrorout(formatNumber(netIoState.getSendErrors(), SLEEP_SECOND));

            netIoState.setHostname($.getIp());
            netIoState.setCreateTime(new Date());
            netIoState.setName("-1");
            if(!$.isEmptyOrNull(targetIp)) {
                netIoState.setTestIp(netTestState2.getTargetIp());
                netIoState.setDelay($.max(netTestState1.getDelay(), netTestState2.getDelay()));
                netIoState.setLoss(($.max(netTestState1.getLoss(),netTestState2.getLoss())));
                netIoState.setStatus(netTestState2.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return netIoState;
        }
    }

    public long getNetConnections() {
        $.debugOut("[NETWORK]获取网络连接数。");
        if ($.system().equals("windows")) { // 获取linux环境下的网口上下行速率
            return getNetConnectionsWindows();
        } else { // 获取windows环境下的网口上下行速率
            return getNetConnectionsLinux();
        }
    }

    public long getNetConnectionsWindows() {
        try {
            return new $System().runCmd("netstat -ano").getTotal();
        } catch (IOException e) {
            return 0;
        }
    }

    public long getNetConnectionsLinux() {
        try {
            return new $System().runCmd("netstat -anut").getTotal();
        } catch (IOException e) {
            return 0;
        }
    }
    /**
     * 获取所有本地IP
     * @return
     */
    public List<InetAddress> getLocalIps(){
        List<InetAddress> broadcaseIps = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (inetAddress instanceof Inet4Address) {  // 检查是否为IPv4地址
                            if (!inetAddress.getHostAddress().equals("127.0.0.1") && !inetAddress.getHostAddress().equals("0.0.0.0")) {
                                broadcaseIps.add(inetAddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return broadcaseIps;
    }

    /**
     * 获取局域网的第一个IP地址
     * @return
     */
    public InetAddress getNetworkLanIPsRandom() {
        Map<String, InetAddress> computerIPs = getNetworkLanIPs(getLocalIps(), 0, 0, 1);
        for(String key: computerIPs.keySet()){
            return computerIPs.get(key);
        }
        return null;
    }

    /**
     * 获取局域网的所有IP地址
     * @return
     */
    public Map<String, InetAddress> getNetworkLanIPs() {
        return getNetworkLanIPs(getLocalIps(), 0, 0, 99999);
    }

    /**
     * 获取局域网的所有IP地址
     * @return
     */
    public Map<String, InetAddress> getNetworkLanIPs(String ip) {
        Map<String, InetAddress> computerIPs = new ConcurrentHashMap<>();

        InetAddress inet = null;
        try {
            inet = InetAddress.getByName(ip);
            computerIPs = getNetworkLanIPs(Arrays.asList(inet), 0, 0, 99999);
        } catch (UnknownHostException e) {}

        return computerIPs;
    }

    /**
     * 获取局域网的所有IP地址
     * 结束任务：32/1233/1265-1265
     * @return
     */
    public Map<String, InetAddress> getNetworkLanIPs(List<InetAddress> ips, int timeout, int poolSize, int maxSize) {
        Map<String, InetAddress> allIps = new HashMap<>();
        Map<String, InetAddress> scannersIps = new ConcurrentHashMap<>();
        Map<String, InetAddress> computerIPs = new ConcurrentHashMap<>();
        Map<String, InetAddress> noScannersIPs = new ConcurrentHashMap<>();
        $Timeclock t = $.newTimeclock();
        t.timeclock("[NETWORK]局域网扫描任务开始：",0);

        final int _timeout = timeout==0 ?  460 : timeout;

        ExecutorService runThreadPoolExecutor  = Executors.newFixedThreadPool(poolSize != 0 ? poolSize : 20);

        for(InetAddress inetAddress: ips){

            $.info("开始扫描局域网段：Computer IP: " + inetAddress.getHostAddress()+"---255");
            // 扫描指定子网的所有IP地址
            String subnet = inetAddress.getHostAddress().substring(0, inetAddress.getHostAddress().lastIndexOf(".")); // 请替换为您的局域网子网

            for (int i = 1; i <= 254; i++) {
                String ipAddress = subnet + "." + i;
                if(ipAddress.equals(inetAddress.getHostAddress())) continue;

                allIps.put(ipAddress, null);

                runThreadPoolExecutor.submit(() -> {
                    try {
                        InetAddress inet = null;
                        inet = InetAddress.getByName(ipAddress);
                        scannersIps.put(inet.getHostName(), inet);
//                        $.info("扫描IP："+inet.getHostAddress());
                        if (inet.isReachable(_timeout)) { // 500毫秒超时时间
                            //                        if ($.testPing(ipAddress)) { // 5000毫秒超时时间
                            computerIPs.put(inet.getHostName(), inet);
                            $.info("成功扫描到IP：" + inet.getHostName() + "/" + inet.getHostAddress());
                        } else {
                            noScannersIPs.put(inet.getHostName(), inet);
                            //                            $.info("未扫描到IP："+inet.getHostName()+"/"+inet.getHostAddress());
                        }
                    } catch (UnknownHostException e) {
                        $.warn("IP地址不存在：" + e);
                    } catch (IOException e) {
                        $.warn("扫描IP异常：" + e);
                    }
                });
            }
        }

        while (computerIPs.size() < maxSize){
            if(0 == allIps.size()) continue;

            $.info("当前扫描进度："+computerIPs.size()+"/"+noScannersIPs.size()+"/"+scannersIps.size()+"-"+allIps.size()+"|"+maxSize);

            if(allIps.size() == noScannersIPs.size()+computerIPs.size()) {

                $.info("打印列表："+computerIPs.size());

                computerIPs.keySet().forEach(hostname->{
                    $.sout("扫描到的IP列表："+hostname+"/"+computerIPs.get(hostname).getHostAddress());
                });

                $.info("结束任务："+computerIPs.size()+"/"+noScannersIPs.size()+"/"+scannersIps.size()+"-"+allIps.size()+"|"+maxSize);
                break;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        t.timeclock("[NETWORK]局域网扫描任务结束：", -1);
        runThreadPoolExecutor.shutdownNow();

        return computerIPs;
    }

    /**
     * 通过ip获取信息,loss:丢包率，delay:延时
     * @param targetIp 目标IP
     * @return
     */
    public $NetTestState getNetTestStateByPing(String targetIp){
        $NetTestState netTestState = new $NetTestState(targetIp);

        // 如果不存在，则随机取一个附近的IP
        if($.isEmptyOrNull(targetIp)) targetIp = getNetworkLanIPsRandom().getHostAddress();

        //获取当前进程运行对象
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        String line = null;
        InputStream inputStream = null;
        InputStreamReader isReader = null;
        BufferedReader reader = null;

        String os = $.system();
        double loss = 0;//丢包率
        double delay = 0;//延时
        try {
            process = runtime.exec(os.contains("win") ?
                    ("ping -n 5 -w 10 "+targetIp):
                    ("ping -c 5 -w 10 "+targetIp)
                    );
            inputStream = process.getInputStream();//实例化输入流
            isReader = new InputStreamReader(inputStream,"GB2312");
            reader = new BufferedReader(isReader);
            StringBuffer buffer = new StringBuffer();
            if (os.contains("win")){//Windows系统执行结果解析
                while ((line = reader.readLine()) != null){
                    //丢包率
                    if (line.contains("%")){
                        String tmploss = line.substring(line.lastIndexOf("=") + 1 ,line.indexOf("%")+1);
                        if (tmploss.contains("(")){
                            tmploss = tmploss.substring(tmploss.indexOf("(") + 1).trim();
                        }
                        loss = Double.valueOf(tmploss.replaceAll("%",""));
                    }
                    //网络延时
                    if ((line.contains(",") || line.contains("，")) && line.contains("=") && line.contains("ms")){
                        delay = Double.valueOf(line.substring(line.lastIndexOf("=") + 1 ,line.lastIndexOf("ms")).trim());
                    }
                    buffer.append(line + "\n");
                }
            }else{//Linux系统执行结果解析
                while ((line = reader.readLine()) != null){
                    //丢包率
                    if (line.contains("%")){
                        String[] msg = null;
                        if (line.contains(",")){
                            msg = line.split(",");
                        }else if (line.contains("，")){
                            msg = line.split("，");
                        }
                        if (msg.length > 0) {
                            loss = Double.valueOf(msg[2].substring(0, msg[2].indexOf("%")).trim());
                        }
                    }
                    //网络延时
                    if (line.contains("/")){
                        String[] msg = line.split("=");
                        String[] names = msg[0].split("/");
                        String[] values = msg[1].split("/");
                        for (int i = 0;i < names.length;i++){
                            String str = names[i];
                            if ("avg".equalsIgnoreCase(str)){
                                delay = Double.valueOf(values[i].replaceAll("ms",""));
                                break;
                            }
                        }
                    }
                    buffer.append(line + "\n");
                }
            }

            netTestState.setLoss(loss);
            if (delay != 0){
                netTestState.setStatus(1);
                netTestState.setDelay(delay);
            }

            inputStream.close();
            isReader.close();
            reader.close();
//            runtime.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return netTestState;
    }

    public $NetIoState readNetInLine() {
        $NetIoState netIoState = new $NetIoState();
        try {
            if ($.system().equals("windows")) { // 获取linux环境下的网口上下行速率
                return readNetInLineWindows();
            } else { // 获取windows环境下的网口上下行速率
                return readNetInLineLinux();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netIoState;
    }

    public $NetIoState readNetInLineWindows() {
        $NetIoState netIoState = new $NetIoState();

        try {
            new $System().runCmd("netstat -e", ($CallbackRow<String>) (line, index) -> {
                String[] sp = line.split("\s+");

                if(line.indexOf("Bytes")>=0){
                    long sp1 = Long.valueOf(sp[1]);
                    long sp2 = Long.valueOf(sp[2]);
                    netIoState.setReceivedBytes(sp1);
                    netIoState.setSendBytes(sp2);
                }else if(line.indexOf("Non-unicast")>=0){
                    long sp1 = Long.valueOf(sp[2]);
                    long sp2 = Long.valueOf(sp[3]);
                    netIoState.setReceivedNonUnicastPackets(sp1);
                    netIoState.setSendNonUnicastPackets(sp2);
                }else if(line.indexOf("Unicast")>=0){
                    long sp1 = Long.valueOf(sp[2]);
                    long sp2 = Long.valueOf(sp[3]);
                    netIoState.setReceivedUnicastPackets(sp1);
                    netIoState.setSendUnicastPackets(sp2);
                }else if(line.indexOf("Discards")>=0){
                    long sp1 = Long.valueOf(sp[1]);
                    long sp2 = Long.valueOf(sp[2]);
                    netIoState.setReceivedDiscards(sp1);
                    netIoState.setSendDiscards(sp2);
                    netIoState.setDropin(sp1);
                    netIoState.setDropout(sp2);
                }else if(line.indexOf("Errors")>=0){
                    long sp1 = Long.valueOf(sp[1]);
                    long sp2 = Long.valueOf(sp[2]);
                    netIoState.setReceivedErrors(sp1);
                    netIoState.setSendErrors(sp2);
                }else if(line.indexOf("Unknown")>=0){
                    long sp1 = Long.valueOf(sp[2]);
                    netIoState.setReceivedUnknownProtocols(sp1);
                }
                return false;
            });
        } catch (IOException e) {
            $.warn(e);
        }

        return netIoState;
    }

    public $NetIoState readNetInLineLinux() {
        $NetIoState netIoState = new $NetIoState();

            /*
            docker0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 172.17.0.1  netmask 255.255.0.0  broadcast 172.17.255.255
        inet6 fe80::42:5eff:fe6e:83bc  prefixlen 64  scopeid 0x20<link>
        ether 02:42:5e:6e:83:bc  txqueuelen 0  (Ethernet)
        0   1       2           3       4       5 6
        RX packets 8618549  bytes 1859786468 (1.7 GiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 10187301  bytes 2002746814 (1.8 GiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

eno1: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 192.168.5.171  netmask 255.255.248.0  broadcast 192.168.7.255
        inet6 fe80::3e7c:3fff:fef1:22e6  prefixlen 64  scopeid 0x20<link>
        ether 3c:7c:3f:f1:22:e6  txqueuelen 1000  (Ethernet)
        RX packets 69546053  bytes 15485433264 (14.4 GiB)
        RX errors 0  dropped 94281  overruns 4  frame 0
        TX packets 11423922  bytes 2924979708 (2.7 GiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
        device memory 0xd6e20000-d6e3ffff

eno2: flags=4099<UP,BROADCAST,MULTICAST>  mtu 1500
        ether 3c:7c:3f:f1:22:e7  txqueuelen 1000  (Ethernet)
        RX packets 0  bytes 0 (0.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
        device memory 0xd6e00000-d6e1ffff

lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        inet6 ::1  prefixlen 128  scopeid 0x10<host>
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 25540  bytes 2735216 (2.6 MiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 25540  bytes 2735216 (2.6 MiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

veth2587e62: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet6 fe80::3c11:7ff:fe98:a8fd  prefixlen 64  scopeid 0x20<link>
        ether 3e:11:07:98:a8:fd  txqueuelen 0  (Ethernet)
        RX packets 3  bytes 162 (162.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 6378  bytes 286487 (279.7 KiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

veth474c655: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet6 fe80::6cde:adff:fe5c:ccbc  prefixlen 64  scopeid 0x20<link>
        ether 6e:de:ad:5c:cc:bc  txqueuelen 0  (Ethernet)
        RX packets 7300408  bytes 1886020479 (1.7 GiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 8874907  bytes 1929456159 (1.7 GiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

veth8a1f3a8: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet6 fe80::c08d:e9ff:fe32:9f80  prefixlen 64  scopeid 0x20<link>
        ether c2:8d:e9:32:9f:80  txqueuelen 0  (Ethernet)
        RX packets 34567  bytes 2510362 (2.3 MiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 20433  bytes 1662449 (1.5 MiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

vethe6e1b91: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet6 fe80::a8f6:bcff:fea4:a46d  prefixlen 64  scopeid 0x20<link>
        ether aa:f6:bc:a4:a4:6d  txqueuelen 0  (Ethernet)
        RX packets 15917  bytes 1410860 (1.3 MiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 30964  bytes 2286778 (2.1 MiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0


             */

        try {
            new $System().runCmd("ifconfig", ($CallbackRow<String>) (line, index) -> {
                if(line.indexOf("RX packets")>=0){
                    String[] sp = line.split("\s+");
                    netIoState.setReceivedUnicastPackets(netIoState.getReceivedUnicastPackets()+Long.valueOf(sp[2]));
                    netIoState.setReceivedBytes(netIoState.getReceivedBytes()+Long.valueOf(sp[4]));
                    netIoState.setRxpck((sp[5]+sp[6]).replaceAll("\\(|\\)",""));
                }else if(line.indexOf("TX packets")>=0){
                    String[] sp = line.split("\s+");
                    netIoState.setSendUnicastPackets(netIoState.getSendUnicastPackets()+Long.valueOf(sp[2]));
                    netIoState.setSendBytes(netIoState.getSendBytes()+Long.valueOf(sp[4]));
                    netIoState.setTxpck((sp[5]+sp[6]).replaceAll("\\(|\\)",""));
                }else if(line.indexOf("RX errors")>=0){
                    String[] sp = line.split("\s+");
                    netIoState.setReceivedErrors(netIoState.getReceivedErrors()+Long.valueOf(sp[2]));
                    netIoState.setDropin(netIoState.getDropin() + Long.valueOf(sp[4]));
                }else if(line.indexOf("TX errors")>=0){
                    String[] sp = line.split("\s+");
                    netIoState.setSendErrors(netIoState.getSendErrors()+Long.valueOf(sp[2]));
                    netIoState.setDropout(netIoState.getDropout() + Long.valueOf(sp[4]));
                }
                return false;
            });
        } catch (IOException e) {
            $.warn(e);
        }

        return netIoState;
    }

    public String formatNumber(double f) {
        return new Formatter().format("%.2f", f).toString();
    }

    public String formatNumber(long f, long c) {
        try {
            return new Formatter().format("%s", Double.valueOf(f)/Double.valueOf(c)).toString();
        } catch (Exception e){
            $.warn("格式化异常："+f+"/"+c);
            return String.valueOf(f);
        }
    }
}
