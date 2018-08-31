package com.ailbb.ajj.file;

import com.ailbb.ajj.entity.$Result;

import java.util.List;

/**
 * Created by Wz on 8/3/2018.
 */
public class $Compress {
    public $Zip zip = new $Zip();

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
}
