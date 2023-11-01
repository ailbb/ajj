package com.ailbb.ajj.http;

import com.ailbb.ajj.$;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Download {
    Map<String, Boolean> downloadList = new HashMap<>();
    Map<String, Boolean> waitList = new HashMap<>();
    public int maxThread = 10;

    public $Download(){}

    public boolean download(String addressURL, String targetPath) {
        while(!getDownloadThread(addressURL)) { // 如果没有获取到下载资源，就等待
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 转换为utf8,防止中文路径无法下载
        String httpUrl = toUtf8URL(addressURL);
        URL url = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;

        try {
            url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10 * 1000); // 设置超时时间为10秒
            inputStream = conn.getInputStream(); // 获得下载下来的输入流

            // 将输入流转换为字节数组
            byte[] byteData = readInputStream(inputStream);

            // 文件保存位置
            String fileName = addressURL.substring(addressURL.lastIndexOf("/")+1);
            String path = $.getPath(targetPath.endsWith(fileName) ? targetPath : (targetPath + File.separator + fileName));
            $.mkdir(path.substring(0,path.lastIndexOf("/")));
            File file = new File(path);
            fos = new FileOutputStream(file);
            fos.write(byteData);
            fos.flush();
            $.info("Download URL ["+addressURL+"] size:"+$.unit.convert(byteData.length, $.unit.$BYTE)  +" >>> Local Path ["+path+"]");
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            downloadList.remove(addressURL);
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    private synchronized boolean getDownloadThread(String addressURL) {
        waitList.put(addressURL, true);

        if(downloadList.size() < maxThread){
            waitList.remove(addressURL); // 移除等待队列
            downloadList.put(addressURL, true); // 进入下载队列
        }

        return null != downloadList.get(addressURL);
    }

    public boolean download(List<String> urls, String targetDir) {
        $.info("开始下载文件，总共链接数为："+urls.size() + "（个）;");
        HashMap<String, Boolean> resultMap = new HashMap<>();

        for(int i=0; i<urls.size(); i++) {
            int idx = i+1;
            String picStr = urls.get(i);
            $.async(new Runnable() {
                @Override
                public void run() {
                    $.info(">> 正在下载文件第："+ idx + "（个）;");
                    ;
                    boolean isDone = download(picStr, targetDir);
                    resultMap.put(picStr, isDone);
                }
            });
        }

        while(resultMap.size() != urls.size()){}; // 如果没完，则一直等待
        $.info(urls.size() + "（个）文件下载完成！");
        return true;
    }

    public boolean download(Map<String, String> urlMap) {
        $.info("开始下载文件，总共链接数为："+urlMap.size() + "（个）;");
        HashMap<String, Boolean> resultMap = new HashMap<>();
        int i=0;
        for(String url : urlMap.keySet()) {
            int idx = i++;
            String downLoadPath = urlMap.get(url);
            $.async(new Runnable() {
                @Override
                public void run() {
                    boolean isDone = download(url, downLoadPath);
                    resultMap.put(url, isDone);
                }
            });
        }

        while(resultMap.size() != urlMap.size()){}; // 如果没完，则一直等待
        $.info(urlMap.size() + "（个）文件下载完成！");
        return true;
    }

    public boolean smartDownload(String text, String targetDir, String tag, String baseURL) {
        Map<String, String> urlMap = new HashMap<>();

        for(String str : text.split("\n")) {
            if(str.contains(baseURL) &&$.test(tag, str)){
                String pickStr = $.regex.pickup("<"+tag+"[^>]+>", ".+", "</"+tag+">", str);
                String fileName = pickStr.replaceAll(baseURL, "");
                urlMap.put(pickStr, $.getPath(targetDir,fileName));
            }
        }

        while(!download(urlMap)){};
        return true;
    }
    public static synchronized byte[] readInputStream(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bos.toByteArray();
    }

    public static synchronized String toUtf8URL(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    $.error(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    int lastCheckDownload = 0;
    int lastCheckWait = 0;
    public void startChecking(){
        $.async(true, () -> {
            $.info("启动监听任务队列检查："+Thread.currentThread().getId() + "/"+Thread.currentThread().getName());
            while (true){
                if(downloadList.size() != 0) {
                    lastCheckDownload = downloadList.size();
                    $.info("正在下载任务队列："+downloadList.size() + "/"+maxThread);
                    if(downloadList.size()<3){
                        for(String key: downloadList.keySet()) $.log(key);
                    }
                }
                if(waitList.size() != 0) {
                    lastCheckWait = waitList.size();
                    $.info("正在等待下载队列："+waitList.size());
                }

                if(downloadList.size() == 0 && lastCheckDownload != 0) {
                    lastCheckDownload = 0;
                    lastCheckWait = 0;
                    $.info("当前下载任务队列："+downloadList.size());
                    $.info("当前等待下载队列："+waitList.size());
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public $Download setMaxThread(int i) {
        this.maxThread = i;
        return this;
    }

    public int getMaxThread() {
        return this.maxThread;
    }
}
