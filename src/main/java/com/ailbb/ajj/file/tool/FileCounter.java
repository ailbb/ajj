package com.ailbb.ajj.file.tool;

import com.ailbb.ajj.$;

import java.io.File;
import java.util.*;

public class FileCounter {
    public static Map<String,Long> fileMapper = new TreeMap<>();
    public static Map<String,List<String>> fileMapperList = new TreeMap<>();

    /**
     * 文件大小统计主类
     * @param path
     * @param contextFilter
     */
    public static void fileTypeSizeCount(String path, Map<String,String> contextFilter) {
        doFileSizeCount(path, contextFilter);
        List<Map.Entry<String,Long>> list = new ArrayList<Map.Entry<String,Long>>(fileMapper.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Long>>() {
            //升序排序
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });

        for(Map.Entry<String,Long> mapping:list){
            $.sout(mapping.getKey()+" --- "+$.convert(mapping.getValue()));
        }

        cmdServer();
    }

    /**
     * 执行文件大小统计
     * @param path
     * @param contextFilter
     */
    private static void doFileSizeCount(String path, Map<String,String> contextFilter){

        File f = $.file.getFile(path);

        if(f.isDirectory()) {
            for(String p : f.list()) {
                doFileSizeCount(path+"\\"+p, contextFilter);
            }
        } else {
            $.sout("扫描路径：" + f.getPath() + " --- " + $.convert(f.length()));
            String prefix = f.getName().contains(".") ? f.getName().substring(f.getName().lastIndexOf(".")) : f.getName();
            if(null == fileMapper.get(prefix)) fileMapper.put(prefix, 0L);
            if(null == fileMapperList.get(prefix)) fileMapperList.put(prefix, new ArrayList<>());

            if(null == contextFilter || (null != contextFilter && null != contextFilter.get(prefix))) {
                fileMapper.put(prefix, fileMapper.get(prefix) + f.length());
                fileMapperList.get(prefix).add(f.getPath());
            }
        }

    }

    /**
     * 命令行服务
     */
    private static void cmdServer(){
        Scanner input = new Scanner(System.in);
        final int[] i = {0};
        String val = null;       // 记录输入度的字符串

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if(i[0]++ == 100) {
                            input.close();
                            $.sout("超时退出！");
                        }
                        Thread.sleep(1000); // 100秒超时退出
                    }
                } catch (Exception e) {}
            }
        }).start();

        do{
            System.out.print("\n\n\n请输入命令：");
            val = input.next();
            i[0] = 0; // 重新计时
            commonLine(val);
        }while(!val.equals("#"));   // 如果输入的值不版是#就继续输入
        $.sout("你输入了\"#\"，程序已经退出！");
        input.close(); // 关闭资源
    }

    /**
     * 解析获取输入的命令行
     * @param cmd
     */
    private static void commonLine(String cmd) {
        if(cmd.startsWith("list"))
            showFileSizeCountDetail(cmd.split(":")[1]);
    }

    /**
     * 显示文件详情
     * @param prefix
     */
    private static void showFileSizeCountDetail(String prefix){
        int i = 0;
        for(String p : fileMapperList.get(prefix)) {
            File f = new File(p);
            if(f.length() < 1024*10) {
                i++;
                continue;
            }
            $.sout(p + " -- " + $.convert(f.length()));
        }

        $.sout("隐藏了" +i+ "个小于10KB的文件。");
    }

}
