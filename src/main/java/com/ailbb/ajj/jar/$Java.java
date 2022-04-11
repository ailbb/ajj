package com.ailbb.ajj.jar;

import com.ailbb.ajj.$;

import java.io.File;

public class $Java {
    public String decompilerClass(String sourcePath, String targetPath){
        $.file.checkToDo(targetPath);

        String jarPath = !sourcePath.endsWith(".jar") ? compressSourceJarPath(sourcePath, $.getFile(sourcePath).getParent()) : sourcePath;

        // 解析jar包
        String decodeJarPath = $.jar.decompilerJar($.getFile(jarPath).getAbsolutePath(), targetPath);

        // 解压jar包路径
        unCompressSourceJarPath(decodeJarPath);

        return targetPath;
    }

    private String compressSourceJarPath(String sourcePath, String targetPath) {
        $.info("压缩文件中..."+ targetPath +" >>> ");

        String zipPath = $.file.zip(
                $.getFile(targetPath).getAbsolutePath()+ "\\"+$.getFile(sourcePath).getName()+".jar",
                sourcePath
        ).getDataToString();

        $.info("压缩完成..."+ sourcePath +" >>> " + $.getFile(zipPath).getAbsolutePath());
        return zipPath;
    }

    public String unCompressSourceJarPath(String decodeJarPath) {
        $.info("解压文件中..."+ decodeJarPath +" >>> ");

        File file = $.getFile(decodeJarPath);

        String zipPath = $.file.unzip(file.getAbsolutePath()).getDataToString();

        $.info("解压完成..."+ file.getParent());
        return zipPath;
    }

    public String unCompressSourceJarPath(String decodeJarPath, String descPath) {
        $.info("解压文件中..."+ decodeJarPath +" >>> ");

        File file = $.getFile(decodeJarPath);

        String zipPath = $.file.unzip(file.getAbsolutePath(), descPath).getDataToString();

        $.info("解压完成..."+ descPath);
        return zipPath;
    }
}
