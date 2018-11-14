package com.ailbb.ajj.file.yml;

import com.ailbb.ajj.$;
import com.ailbb.ajj.file.properties.$Properties;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Wz on 11/14/2018.
 */
public class $Yml {
    public static final String $SUFFIX = ".yml";

    public Properties getProperties(String path){
        if(!$.isEmptyOrNull(path) && path.endsWith($.file.properties.$SUFFIX)) return $.file.properties.getProperties(path);

        Properties prop = new Properties();
        Yaml yaml = new Yaml();

        try {
            InputStream inputStream = $.file.configure.getInputStream(path);

            Iterator<Object> result = yaml.loadAll(inputStream).iterator();

            while(result.hasNext()){
                Map map=(Map)result.next();
                iteratorYml(prop, map,null);
            }

        } catch (Exception e) {
            $.error(String.format("Fail load properties %s：", path), e);
        }

        return prop;
    }



    public static void iteratorYml(Properties prop, Map map,String key){
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key2 = entry.getKey();
            Object value = entry.getValue();

            if(value instanceof LinkedHashMap){
                if(key==null){
                    iteratorYml(prop, (Map)value,key2.toString());
                }else{
                    iteratorYml(prop, (Map)value,key+"."+key2.toString());
                }
            }

            if(value instanceof String){
                if(key==null){
                    prop.setProperty(key2.toString(), value.toString());
                }
                if(key!=null){
                    prop.setProperty(key+"."+key2.toString(), value.toString());
                }
            }
        }

    }
}
