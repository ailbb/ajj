package com.ailbb.ajj.file.ctl;

/**
 * Created by Wz on 8/2/2018.
 */
public class $Field {
    String name;
    String type;

    public $Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public $Field setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public $Field setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        if(null != type)
            return "{ name='" + name + '\'' + ", type='" + type + '\'' + '}';
        else
            return name;
    }

}
