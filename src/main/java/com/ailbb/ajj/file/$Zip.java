package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
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
                    String zipPath = parentPath + file.getName();
                    in = new FileInputStream(file);
                    zipOut.putNextEntry(new ZipEntry(zipPath));

                    int temp = 0;
                    while((temp = in.read()) != -1){
                        zipOut.write(temp);
                    }

                    in.close();

                    $.info("Zip File : " + file.getAbsoluteFile() + " >>> " + zipPath);

                    if(isDelete) {
                        file.delete();
                        $.info("Delete File : " + file.getAbsoluteFile());
                    }
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
        return compress(in, size, targetPath, fileName, $POSTFIX);
    }

    public File compress(InputStream in, long size, String targetPath, String fileName, String POSTFIX) {
        ZipOutputStream zos = null;
        try {
            String destName = (fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName) + POSTFIX;
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

    public $Result uncompress(String zipPath)  {
        return uncompress(zipPath, $.getFile(zipPath).getParent());
    }

    public $Result uncompress(String zipPath, String sDestPath)  {
        $Result rs = $.result();
        $.info("uncompress:" + zipPath);

        try {
            ArrayList<String> allFileName = new ArrayList<String>();
            // 先指定压缩档的位置和档名，建立FileInputStream对象
            FileInputStream fins = new FileInputStream(zipPath);
            // 将fins传入ZipInputStream中
            ZipInputStream zins = new ZipInputStream(fins);
            ZipEntry ze = null;
            byte[] ch = new byte[256];
            while ((ze = zins.getNextEntry()) != null) {
                File zfile = new File(sDestPath, ze.getName());
                File fpath = new File(zfile.getParentFile().getPath());
                $.info("uncompress:" + fpath.getAbsolutePath() + " >>> " + zfile.getAbsolutePath());
                if (ze.isDirectory()) {
                    if (!zfile.exists())
                        zfile.mkdirs();
                    zins.closeEntry();
                } else {
                    if (!fpath.exists())
                        fpath.mkdirs();
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
                    allFileName.add(zfile.getAbsolutePath());
                    while ((i = zins.read(ch)) != -1)
                        fouts.write(ch, 0, i);
                    zins.closeEntry();
                    fouts.close();
                }
            }
            fins.close();
            zins.close();
        } catch (Exception e) {
            rs.addError(e);
        }

        return rs;
    }

    public String subFix(String name) {
        if(name.indexOf(".") != -1) return name.substring(0, name.indexOf("."));
        return name;
    }

}
