package com.ailbb.ajj.file.xml;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class $Xml {
    $JDOMXML jdomxml = new $JDOMXML();

    public Map<String,String> ParseMap(File file, String name, String key){
        try {
            return jdomxml.ParseMap(file, name, key);
        } catch (Exception e) {
            return null;
        }
    }
}
