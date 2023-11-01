package com.ailbb.ajj.lang;

import com.ailbb.ajj.$;

import java.lang.reflect.Method;

public class $Cast {

    /**
     * 类型转换
     * @param object
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T cast(Object object, Class<T> clazz) {
        T t = null;
        try {
            Method[] methods = object.getClass().getMethods();
            t = clazz.getDeclaredConstructor().newInstance();

            for(Method method : methods) {
                if(method.getName().equals("getClass")) continue;
                if(method.getName().indexOf("get") != -1 || method.getName().startsWith("is") ) {
                    try {
                        clazz.getMethod(method.getName().replace("get","set"), method.getReturnType()).invoke(t, method.invoke(object));
                    } catch (Exception e){
                        $.debugOut(e);
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return t;
        }
    }

}
