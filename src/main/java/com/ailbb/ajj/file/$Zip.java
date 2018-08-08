package com.ailbb.ajj.file;

import com.ailbb.ajj.$;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Wz on 8/3/2018.
 */
public class $Zip {
    public static final String $POSTFIX = "zip";

    /**
     * 压缩文件
     * @param path 生成目录
     * @param paths 文件目录
     * @return
     */
    public String compress(String path, String... paths) {
        return compress(path, false, Arrays.asList(paths));
    }

    public String compress(String path, List<String> paths) {
        return compress(path, false, paths);
    }

    /**
     * 压缩文件
     * @param path 生成目录
     * @param paths 文件目录
     * @return
     */
    public String compress(String path, boolean isDelete, String... paths) {
        return compress(path, isDelete, Arrays.asList(paths));
    }

    public String compress(String path, boolean isDelete, List<String> paths) {
        ZipOutputStream zipOut = null;
        path = $.getPath(path);

        try {
            File file = $.getFile(path);

            if(file.exists()) file.delete(); // 如果压缩包文件名存在，则先删除

            zipOut = new ZipOutputStream(new FileOutputStream(file));

            for(String p: paths) {
                compress(file.getPath(), "/", zipOut, isDelete, $.getFile(p)); // 要被压缩的文件夹
            }

            $.info(String.format("Write Zip file：%s", path));

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(zipOut != null) zipOut.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 核心压缩类
     * @param filePath 压缩文件存储的路径
     * @param parentPath 父目录
     * @param zipOut 压缩文件流
     * @param isDelete 是否压缩完毕后删除原文件
     * @param files 文件数
     * @return
     * @throws Exception
     */
    private void compress(String filePath, String parentPath, ZipOutputStream zipOut, boolean isDelete, File... files) throws Exception {
        for(File file : files) {
            if(file.isDirectory()){
                compress(filePath, parentPath + file.getName() + "/", zipOut, isDelete, file.listFiles());
            } else if(!file.getPath().equals(filePath)){
                InputStream in = null;
                try {
                    in = new FileInputStream(file);
                    zipOut.putNextEntry(new ZipEntry(parentPath + file.getName()));

                    int temp = 0;
                    while((temp = in.read()) != -1){
                        zipOut.write(temp);
                    }

                    in.close();

                    if(isDelete) file.delete();
                } catch (Exception e) {
                    throw e;
                } finally {
                    if(in != null) in.close();
                }
            }
        }
    }
}
