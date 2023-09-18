package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.file.csv.$CSV;
import com.ailbb.ajj.file.ctl.$Ctl;
import com.ailbb.ajj.file.excel.$Excel;
import com.ailbb.ajj.file.properties.$Properties;
import com.ailbb.ajj.file.tool.FileCounter;
import com.ailbb.ajj.file.tool.FileMerge;
import com.ailbb.ajj.file.xml.$Xml;
import com.ailbb.ajj.file.yml.$Yml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileUploadException;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import static com.ailbb.ajj.$.*;
import static com.ailbb.ajj.$.file;

/*
 * Created by Wz on 6/20/2018.
 */
public class $FileTailReader {
    private Map<String, $FileTailReaderInstance> cache = new HashMap<>();

    $FileTailReader(){}

    public $FileTailReaderInstance getInstance(File file) {
        String pathKey = file.getAbsolutePath();
        if(null == cache.get(pathKey))
            cache.put(pathKey, new $FileTailReaderInstance(file));

        return cache.get(pathKey);
    }

    public $FileTailReaderInstance getInstance(File file, TailReaderListener listener) {
        String pathKey = file.getAbsolutePath();
        if(null == cache.get(pathKey))
            cache.put(pathKey, new $FileTailReaderInstance(file, listener));
        else if(null == cache.get(pathKey).listeners.get(listener.getAliasName()))
            cache.get(pathKey).listeners.put(listener.getAliasName(), listener);

        return cache.get(pathKey);
    }

    public static class $FileTailReaderInstance {
        private File file; // 文件信息
        private long currentPos = 0; // 当前的位置
        private int intervalTime = 10; // 每10毫秒监听一次
        private int timeOut = 60*1000; // 1分钟没有写入，中断线程
        private int timeCount = 0; // 记录监听的次数
        private int dataCacheSize = 10000; // 最大缓存数量
        private boolean closed = false;
        private boolean runEnd = true;
        private boolean historyRead = false;
        private Map<String, TailReaderListener> listeners = new HashMap<>();
        private ArrayList<String> cacheData = new ArrayList<>();

        $FileTailReaderInstance(File file) {
            this.file = file;
            this.currentPos = file.length();
        }
        $FileTailReaderInstance(File file, TailReaderListener listener) {
            this.file = file;
            this.currentPos = file.length();
            this.listeners.put(listener.getAliasName(),listener);
        }
        
        $FileTailReaderInstance(File file, Map<String, TailReaderListener> listeners) {
            this.file = file;
            this.currentPos = file.length();
            this.listeners = listeners;
        }

        public $FileTailReaderInstance start(){
            return start(this.historyRead);
        }


        public $FileTailReaderInstance close(){
            this.closed = true;
            return this;
        }

        public $FileTailReaderInstance start(boolean historyRead){
            if(!runEnd) {
                $.debugOut("获取到正在运行的TailReader文件任务："+file.getPath());
                return this;
            }

            $.info("启动TailReader文件任务："+file.getPath());
            runEnd = !reset(historyRead); // 程序运行状态

            new Thread(()->{
                while (!closed) {
                    if(file.length() > currentPos) { // 当文件大小超出指针范围时，读取新的一部分
                        $.debugOut("监听到文件变化..."+file.getPath());

                        timeCount = 0; // 标识重置为0

                        String data = $.file.readFileToText(file, currentPos, false);

                        if(null != listeners && listeners.size()>0) { // 如果有监听器，则直接调用监听器消化数据
                            for(TailReaderListener trl : listeners.values()){
                                trl.fileReceive(file, currentPos);
                                trl.dataReceive(Arrays.asList(data));
                            }
                        } else { // 否则则加入缓存
                            if(cacheData.size() > dataCacheSize) cacheData.remove(0);
                            this.cacheData.add(data);
                        }

                        currentPos = file.length(); // 数据读取完毕，指针后移
                    } else if(file.length() == currentPos) {
                        $.debugOut("等待时间..."+(timeCount * intervalTime)+"/"+timeOut);
                        timeCount++; // 没有新内容写入
                    }

                    if(timeCount * intervalTime > timeOut) { // 如果超出超时时间，则中断线程
                        cacheData.clear(); // 清除缓存数据
                        break;
                    }

                    try {
                        if(intervalTime != 0) Thread.sleep(intervalTime); // 睡眠等待时间
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                reset(historyRead);
                $.info("文件超时未写入，任务结束："+file.getPath());
            }).start();

            return this;
        }
        
        

        public ArrayList<String> readData() {
            if(null != listeners && listeners.size()>0 && cacheData.size()==0) {
                $.warn("检测到有额外的监听器，将不会有数据返回！");
            }

            Object cloneResult = cacheData.clone();
            if($.isDebugEnabled())
                cacheData.forEach(r->{
                    $.debugOut("输出数据："+r);
                });
            cacheData.clear();
            return (ArrayList<String>)cloneResult;
        }

        public boolean reset(){ return reset(false); }
        public boolean reset(boolean historyRead){
            this.intervalTime = 500;
            this.timeOut = 60*1000;
            this.timeCount = 0;
            this.runEnd = true;
            this.historyRead = false;
            this.currentPos = historyRead ? 0 : file.length();
            cacheData.clear();

            return true;
        }
    }

    public interface TailReaderListener {
        void fileReceive(File file, long currentPos);
        void dataReceive(List<String> cacheData);
        String getAliasName();

    }

}
