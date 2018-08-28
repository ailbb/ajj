package com.ailbb.ajj.file.properties;

import com.ailbb.ajj.$;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.ailbb.ajj.$.rel;

/**
 * Created by Wz on 8/22/2018.
 */
public class $Properties {
    private Map<String, Properties > propertiesMap = new HashMap<>();

    /**
     * 获取文件
     * @param path
     * @return
     */
    public Properties getProperties(String path) {
        try {
            Properties prop = new Properties();
            prop.load(getInputStream(path));
            propertiesMap.put(path, prop);
            return prop;
        } catch (Exception e) {
            $.exception(e);
            return null;
        }
    }

    public String getProperty(String path, String name) {
        return $.isEmptyOrNull(propertiesMap.get(path)) ? null : propertiesMap.get(path).getProperty(name);
    }

    public String getProperty(String name) {
        for(String key: propertiesMap.keySet()) {
            String value = propertiesMap.get(key).getProperty(name);
            if(!$.isEmptyOrNull(value)) return value;
        }

        return null;
    }

    public InputStream getInputStream(String path) {
        String defaultPath = $.getPath(); // 获取默认路径

        try {
            int jarIndex = defaultPath.lastIndexOf("\\.jar!/"); // jar包内

            if(-1 != jarIndex) { // 如果在jar包内，则将jar包所处位置当作根目录
                defaultPath = defaultPath.substring(0, jarIndex);
                defaultPath = defaultPath.substring(0, defaultPath.lastIndexOf("/") + 1);
                return $.file.getInputStream(defaultPath + path);
            }
        } catch (Exception e) {
            $.info("资源加载失败，使用默认路径..");
        }

        return getResourceAsStream(path);
    }

    public InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
