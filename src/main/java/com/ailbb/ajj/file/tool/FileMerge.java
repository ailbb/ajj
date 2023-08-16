package com.ailbb.ajj.file.tool;

import org.apache.hc.core5.util.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileMerge {

    public boolean mergeFiles(String[] fpaths, String resultPath) {

        if (fpaths == null || fpaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i ++) {
            files[i] = new File(fpaths[i]);
            if (TextUtils.isEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < fpaths.length; i ++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < fpaths.length; i ++) {
            files[i].delete();
        }

        return true;
    }


    public boolean mergeFiles(File[] files, String resultPath) {

        if (files == null || files.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (files.length == 1) {
            return files[0].renameTo(new File(resultPath));
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < files.length; i ++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < files.length; i ++) {
            files[i].delete();
        }

        return true;
    }

}
