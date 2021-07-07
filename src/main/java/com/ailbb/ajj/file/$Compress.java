package com.ailbb.ajj.file;

import com.ailbb.ajj.entity.$Result;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/*
 * Created by Wz on 8/3/2018.
 */
public class $Compress {
    public $Zip zip = new $Zip();
    public $GZip gzip = new $GZip();

    // 压缩
    public $Result compress(String path, String... paths) {
        return zip.compress(path, paths);
    }

    // 压缩
    public $Result compress(String path, List<String> paths) {
        return zip.compress(path, paths);
    }

    public $Result zip(String path, String... paths)  {
        return zip.compress(path, paths);
    }

    public $Result zip(String path, List<String> paths)  {
        return zip.compress(path, paths);
    }

    public $Result zip(String path, boolean isDelete, String... paths)  {
        return zip.compress(path, isDelete, paths);
    }

    public $Result zip(String path, boolean isDelete, List<String> paths)  {
        return zip.compress(path, isDelete, paths);
    }

    /*
     * 文件压缩到指定地点
     * @param file
     * @param targetPath
     * @return
     */
    public File zip(File file, String targetPath)  {
        return zip.compress(file, targetPath);
    }

    /*
     * 文件流压缩到指定路径
     * @param inputStream
     * @param size
     * @param targetPath
     * @param fileName
     * @return
     */
    public File zip(InputStream inputStream, long size, String targetPath, String fileName) {
        return zip.compress(inputStream, size, targetPath, fileName);
    }

    /*
     * 文件压缩到指定地点
     * @param file
     * @param targetPath
     * @return
     */
    public File gzip(File file, String targetPath)  {
        return gzip.compress(file, targetPath);
    }

    /*
     * 文件流压缩到指定路径
     * @param inputStream
     * @param size
     * @param targetPath
     * @param fileName
     * @return
     */
    public File gzip(InputStream inputStream, long size, String targetPath, String fileName) {
        return gzip.compress(inputStream, size, targetPath, fileName);
    }

    /*
     * 是否未压缩文件
     * @param fileName
     * @return
     */
    public boolean isCompressFile(String fileName){
        return null == fileName ? false : (fileName.endsWith($Zip.$POSTFIX) || fileName.endsWith($GZip.$POSTFIX));
    }

}
