package com.ailbb.ajj.server;

import com.ailbb.ajj.$;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wz on 4/4/2020.
 */
public class $Server {
    public Map<String,Host> getHostsIp(){
        return getHosts("ip");
    }

    public Map<String,Host> getHostsByName(){
        return getHosts("host");
    }

    public Map<String,Host> getHosts(){
        return getHosts("host");
    }

    public Map<String,Host> getHosts(String type){
        File fl = new File("/etc/hosts");
        File fw = new File("C:\\Windows\\System32\\drivers\\etc\\hosts");

        return getHosts(fl.exists() ? fl : fw, type);
    }

    public Map<String,Host> getHosts(File f){
        return getHosts(f, "host");
    }

    public Map<String,Host> getHosts(File f, String type){
        Map<String,Host> map = new HashMap<>();

        try {
            String s = "";
            BufferedReader input = new BufferedReader(new FileReader(f));
            while ((s = input.readLine()) != null) {
                if (s.contains("localhost") || $.isEmptyOrNull(s) || s.startsWith("#")) {
                    continue;
                } else {
                    String[] tt = s.split("\\s+");
                    if(tt.length < 2) continue;
                    Host host = new Host();
                    host.setHostName(tt[1]);
                    host.setSimpleName(tt.length == 3 ? tt[2] : tt[1]);
                    host.setIp(tt[0]);
                    map.put(type.toLowerCase().equals("ip") ? host.getIp() : host.getSimpleName(), host);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            $.error("没有找到/etc/hosts。。。", e);
        } catch (IOException e) {
            e.printStackTrace();
            $.error("读取/etc/hosts发生错误", e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }
}
