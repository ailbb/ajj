package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
 * Created by Wz on 8/3/2018.
 */
public class $GZip {
    public static final String $POSTFIX = ".tar.gz";

    /**
     * 压缩文件
     * @param file
     * @param targetPath
     * @return
     */
    public File compress(File file, String targetPath) {
        try {
            return compress(new FileInputStream(file), file.length(), targetPath, file.getName());
        } catch (Exception e) {
            $.error(e);
        }
        return null;
    }

    /**
     * 压缩文件
     * @param in
     * @param size
     * @param targetPath
     * @param fileName
     * @return
     */
    public File compress(InputStream in, long size, String targetPath, String fileName) {
        TarArchiveOutputStream tar = null;
        GZIPOutputStream gzip = null;
        File tmpFile = null;
        try {
            String destName = (fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName);
            tmpFile = $.file.getFile(targetPath, destName);
            File parentFile = tmpFile.getParentFile();
            if (!parentFile.exists()) {
                $.info("路径不存在，创建路径："+parentFile.getPath());
                parentFile.mkdirs();
            }
            tar = new TarArchiveOutputStream(new FileOutputStream(tmpFile));
            TarArchiveEntry entry = new TarArchiveEntry(fileName);
            entry.setSize(size);
            tar.putArchiveEntry(entry);
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                tar.write(buffer, 0, len);
            }
            tar.closeArchiveEntry();
            tar.finish();
            tar.close();
            tar = null;
            in.close();
            File zipFile = $.file.getFile(targetPath, destName + $POSTFIX);
            in = new FileInputStream(tmpFile);
            gzip = new GZIPOutputStream(new FileOutputStream(zipFile));
            while ((len = in.read(buffer)) > 0) {
                gzip.write(buffer, 0, len);
            }
            return zipFile;
        } catch (Exception e) {
            $.error(e);
        } finally {
            try {
                if (in != null) in.close();
                if (tmpFile != null && tmpFile.exists()) tmpFile.delete();
                if (tar != null) tar.close();
                if (gzip != null) gzip.close();
            } catch (IOException e) {
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
            rs = unTarGz(zipPath, sDestPath);
        } catch (IOException e) {
            rs.addError(e);
        }
        return rs;
    }

    public String subFix(String name) {
        String tmpName = name.toLowerCase();
        int pos = tmpName.indexOf($POSTFIX);

        if(pos != -1) return name.substring(0, pos);

        return name;
    }

    /**
     * 解压tar.gz包到指定目录
     *
     * @param tarGzPath tar.gz压缩包
     * @param destPath  解压到指定目录
     */
    public $Result unTarGz(String tarGzPath, String destPath) throws IOException {
        return unTarGz($.getFile(tarGzPath),$.getFile(destPath));
    }

    /**
     * 解压tar.gz包到指定目录
     *
     * @param tarGzFile tar.gz压缩包
     * @param destPath  解压到指定目录
     */
    public $Result unTarGz(File tarGzFile, File destPath) throws IOException {
        $Result rs = $.result();

        if (!tarGzFile.exists()) {
            throw new FileNotFoundException("路径【" + tarGzFile + "】不存在tar.gz压缩包");
        }

        if(tarGzFile.isDirectory()) {
            for(File f : tarGzFile.listFiles()) unTarGz(f, $.getFile(destPath+"/"+subFix(f.getName())));
        }

        if (destPath.exists()) { // 如果已经存在，直接返回
            rs.addMessage($.warn("路径已经存在【" + destPath + "】。"));
            return rs;
        }

        try (FileInputStream fis = new FileInputStream(tarGzFile)) {
            return unTarGz(fis, subFix(tarGzFile.getName()).equals(destPath.getName()) ? destPath.getParentFile() : destPath);
        }
    }

    /**
     * 解压tar.gz包到指定目录
     *
     * @param is       tar.gz的输入流
     * @param destPath 解压到指定目录
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public $Result unTarGz(InputStream is, File destPath) throws IOException {
        $Result rs = $.result();

        try (BufferedInputStream bis = new BufferedInputStream(is);
             GzipCompressorInputStream gzip = new GzipCompressorInputStream(bis);
             TarArchiveInputStream tis = new TarArchiveInputStream(gzip)) {

            for (TarArchiveEntry entry = tis.getNextTarEntry(); entry != null; entry = tis.getNextTarEntry()) {

                File dest = new File(destPath, entry.getName());

                $.debugOut("解压文件："+dest.getAbsolutePath());
                rs.addDataList(dest.getAbsolutePath());

                if (entry.isDirectory()) {
                    dest.mkdirs();
                    continue;
                }

                tis.transferTo(new FileOutputStream(dest));
            }
        }

        return rs;
    }

}
