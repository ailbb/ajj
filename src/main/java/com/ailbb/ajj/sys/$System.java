package com.ailbb.ajj.sys;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

/*
 * Created by Wz on 6/20/2018.
 */
public class $System {
    public String system(){
        return System.getProperty("os.name").toLowerCase().startsWith("windows") ? "windows" : "linux";
    }

    public OperatingSystemMXBean operatingSystemMXBean(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        return operatingSystemMXBean;
    }

    public int cpu(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        return operatingSystemMXBean.getAvailableProcessors();
    }

    public long mem(){ // 获取内存管理MXBean
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 获取堆内存使用情况
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        return heapMemoryUsage.getMax();
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
}
