package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Created by Wz on 8/3/2018.
 */
public class $Zip {
    public static final String $POSTFIX = ".zip";

    /*
     * 压缩文件
     * @param path 生成目录
     * @param paths 文件目录
     * @return $Result
     */
    public $Result compress(String path, String... paths)  {
        return compress(path, false, Arrays.asList(paths));
    }

    public $Result compress(String path, List<String> paths)  {
        return compress(path, false, paths);
    }

    /*
     * 压缩文件
     * @param path 生成目录
     * @param paths 文件目录
     * @return $Result
     */
    public $Result compress(String path, boolean isDelete, String... paths)  {
        return compress(path, isDelete, Arrays.asList(paths));
    }

    /*
     *
     * 压缩文件
     * @param path 生成目录
     * @param isDelete 遇到同名是否删除
     * @param paths 文件目录
     * @return $Result
     */
    public $Result compress(String path, boolean isDelete, List<String> paths)  {
        $Result rs = $.result();
        ZipOutputStream zipOut = null;
        path = $.getPath(path);

        try {
            File file = $.getFile(path);

            if(file.exists()) file.delete(); // 如果压缩包文件名存在，则先删除

            zipOut = new ZipOutputStream(new FileOutputStream(file));

            for(String p: paths) {
                compress(file.getPath(), "/", zipOut, isDelete, $.getFile(p)); // 要被压缩的文件夹
            }

            rs.addMessage($.info(String.format("Write Zip file：%s", path)));

            return rs.setData(path);
        } catch (FileNotFoundException e) {
            rs.addError($.exception(e));
        } finally {
            try {
                if(zipOut != null) zipOut.close();
            } catch (IOException e) {
                rs.addError($.exception(e)).setSuccess(true);
            }
        }

        return rs;
    }

    /*
     * 核心压缩类
     * @param filePath 压缩文件存储的路径
     * @param parentPath 父目录
     * @param zipOut 压缩文件流
     * @param isDelete 是否压缩完毕后删除原文件
     * @param files 文件数
     * @return $Result
     */
    private $Result compress(String filePath, String parentPath, ZipOutputStream zipOut, boolean isDelete, File... files)  {
        $Result rs = $.result();

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
                } catch (FileNotFoundException e) {
                    rs.addError($.exception(e));
                } catch (IOException e) {
                    rs.addError($.exception(e));
                } finally {
                    try {
                        if(in != null) in.close();
                    } catch (IOException e) {
                        $.warn(e);
                    }
                }
            }
        }

        return rs;
    }


    /*
     * 压缩文件到指定路径
     * @param file
     * @param targetPath
     * @return
     */
    public File compress(File file, String targetPath) {
        try {
            return compress(new FileInputStream(file), file.length(), targetPath, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public File compress(InputStream in, long size, String targetPath, String fileName) {
        ZipOutputStream zos = null;
        try {
            String destName = (fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName) + $POSTFIX;
            File zipFile = $.file.getFile(targetPath, destName);
            File parentFile = zipFile.getParentFile();
            if (!parentFile.exists()) {
                $.info("路径不存在，创建路径："+parentFile.getPath());
                parentFile.mkdirs();
            }
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            zos.putNextEntry(new ZipEntry(fileName));
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            return zipFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (zos != null) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
