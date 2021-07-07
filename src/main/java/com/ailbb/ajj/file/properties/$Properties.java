package com.ailbb.ajj.file.properties;

import com.ailbb.ajj.$;
import com.ailbb.ajj.file.yml.$Yml;
import org.springframework.core.env.PropertyResolver;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/*
 * Created by Wz on 8/22/2018.
 */
public class $Properties {
    private Map<String, Properties> propertiesMap = new HashMap<>();
    public static final String $SUFFIX = ".properties";

    /*
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
        return $.isEmptyOrNull(propertiesMap.get(path)) ? getProperty(getProperties(path), name) : getProperty(propertiesMap.get(path), name);
    }

    public String getProperty(Properties pro, String name) {
        String values = pro.getProperty(name);

        if(!$.isEmptyOrNull(values) && $.str(values).contains("${")) {
            List<String> regexs = $.regex("\\$\\{[^\\]]+\\}", values);
            for(String r: regexs) {
                values = values.replace(r, getProperty(pro, r.replaceAll("^\\$\\{|\\}$", "")));
            }
        }

        return values;
    }

    public String getProperty(String name) {
        for(String key: propertiesMap.keySet()) {
            String value = getProperty(propertiesMap.get(key), name);
            if(!$.isEmptyOrNull(value)) return value;
        }

        return null;
    }


    public String getPropertyDef(PropertyResolver pr, String key, String def) {
        return  $.lastDef(def, pr.getProperty(key, ""));
    }
}
