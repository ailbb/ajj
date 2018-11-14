package com.ailbb.ajj.file;

import com.ailbb.ajj.$;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Wz on 11/14/2018.
 */
public class $FileConfigure {
    public Properties getProperties(String path) {
        return $.file.properties.getProperties(path);
    }

    public InputStream getInputStream(String path) throws IOException {
        try {
            InputStream inputStream = $.file.getResourceAsStream(path.replaceAll("^.+:(/|)", "")); // 第一直接

            if(null == inputStream) throw new NullPointerException();

            return inputStream;
        } catch (Exception e) {
            return getInputStreamInJar(path);
        }
    }

    public InputStream getInputStreamInJar(String path) throws IOException {
        try {
            String defaultPath = $.getPath(); // 获取默认路径
            int jarIndex = defaultPath.lastIndexOf("\\.jar!/"); // jar包内

            if(-1 != jarIndex) { // 如果在jar包内，则将jar包所处位置当作根目录
                defaultPath = defaultPath.substring(0, jarIndex);
                defaultPath = defaultPath.substring(0, defaultPath.lastIndexOf("/") + 1);
            }

            return $.file.getInputStream(defaultPath + path);
        } catch (Exception e) {
            return getInputStreamInFile(path);
        }
    }

    public InputStream getInputStreamInFile(String path) throws IOException {
        try {
            return $.file.getInputStream($.getPath(path));
        } catch (Exception e) {
            $.info("Fail Load InputStream.");
            throw e;
        }
    }

}
