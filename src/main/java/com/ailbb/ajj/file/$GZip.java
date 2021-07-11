package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Created by Wz on 8/3/2018.
 */
public class $GZip {
    public static final String $POSTFIX = ".tar.gz";

    public File compress(File file, String targetPath) {
        try {
            return compress(new FileInputStream(file), file.length(), targetPath, file.getName());
        } catch (Exception e) {
            $.error(e);
        }
        return null;
    }

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

}
