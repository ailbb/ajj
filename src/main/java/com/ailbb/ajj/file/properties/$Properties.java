package com.ailbb.ajj.file.properties;

import com.ailbb.ajj.$;
import com.ailbb.ajj.file.yml.$Yml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Wz on 8/22/2018.
 */
public class $Properties {
    private Map<String, Properties> propertiesMap = new HashMap<>();
    public static final String $SUFFIX = ".properties";

    /**
     * 获取文件
     * @param path 文件路径，相对路径/绝对路径
     * @return 文件对象
     */
    public Properties getProperties(String path) {
        Properties prop = new Properties();

        try {
            if(!$.isEmptyOrNull(path) && path.endsWith($Yml.$SUFFIX))
                prop = $.file.yml.getProperties(path);
            else
                prop.load($.file.configure.getInputStream(path));

            propertiesMap.put(path, prop);
        } catch (Exception e) {
            $.error(String.format("Fail load properties %s：", path), e);
        }

        return prop;
    }

    public String getProperty(String path, String name) {
        return $.isEmptyOrNull(propertiesMap.get(path)) ? getProperties(path).getProperty(name) : propertiesMap.get(path).getProperty(name);
    }

    public String getProperty(String name) {
        for(String key: propertiesMap.keySet()) {
            String value = propertiesMap.get(key).getProperty(name);
            if(!$.isEmptyOrNull(value)) return value;
        }

        return null;
    }

}
