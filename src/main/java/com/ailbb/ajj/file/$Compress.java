package com.ailbb.ajj.file;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Wz on 8/3/2018.
 */
public class $Compress {
    public $Zip zip = new $Zip();

    // 压缩
    public String compress(String path, String... paths) {
        return null;
    }

    // 压缩
    public String compress(String path, List<String> paths) {
        return null;
    }

    public String zip(String path, String... paths) {
        return zip.compress(path, paths);
    }

    public String zip(String path, List<String> paths) {
        return zip.compress(path, paths);
    }

    public String zip(String path, boolean isDelete, String... paths) {
        return zip.compress(path, isDelete, paths);
    }

    public String zip(String path, boolean isDelete, List<String> paths) {
        return zip.compress(path, isDelete, paths);
    }
}
