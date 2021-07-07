package com.ailbb.ajj.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 * Created by Wz on 9/10/2018.
 */
public class $Bean {
    public String toCaseGetMethodStr(String key){
        return "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    public Method toCaseGetMethod(String key, Class c) throws NoSuchMethodException {
        return c.getMethod(toCaseGetMethodStr(key));
    }

    public String toCaseGetMethodStr(Field field){
        return toCaseGetMethodStr(field.getName());
    }

    public Method toCaseGetMethod(Field field, Class c) throws NoSuchMethodException {
        return toCaseGetMethod(field.getName(), c);
    }

    public String toCaseSetMethodStr(String key){
        return "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    public Method toCaseSetMethod(String key, Class c, Class argsClass) throws NoSuchMethodException {
        return c.getMethod(toCaseSetMethodStr(key), argsClass);
    }

    public Method toCaseSetMethod(Field field, Class c) throws NoSuchMethodException {
        return toCaseSetMethod(field.getName(), c, field.getType());
    }
}
