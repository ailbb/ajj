package com.ailbb.ajj.file;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Path {
    public String getPath(String path){
        String pr = null;

        if(isEmptyOrNull(path)) path = "/";

        if(system().equals("windows")) {
            if(test("^[A-Za-z]:", path) || test("/[A-Za-z]:", path)  ) pr = rel("/", path);
        } else if(system().equals("linux")) {
            if(path.startsWith("/")) pr = rel(path);
        } else {
            pr = path;
        }

        return isEmptyOrNull(pr) ? rel(Thread.currentThread().getContextClassLoader().getResource("").getPath(), path) : pr;
    }

    public String getPath(Class clazz){
        return clazz.getResource("").getPath().replaceFirst("file:", "");
    }

    public String getRootPath(){
        String path = getPath(this.getClass()); // 获取当前类所处的位置
        int jarIndex = path.lastIndexOf("\\.jar!/"); // jar包内
        int webInfIndex = path.lastIndexOf("WEB-INF"); // web-inf目录下
        int classesIndex = path.lastIndexOf("classes"); // 编译环境下

        if(-1 != webInfIndex) { // 如果在web-inf目录下，说明在在lib目录，则根目录为web-inf上级目录
            path = path.substring(0, webInfIndex);
        }

        if(-1 != classesIndex) { // 如果为classes下，则将classes当作根目录
            path = path.substring(0, classesIndex + "classes".length());
        }

        if(-1 != jarIndex) { // 如果在jar包内，则将jar包所处位置当作根目录
            path = path.substring(0, jarIndex + "\\.jar!/".length());
        }

        return rel(path);
    }

    public String getRelativePath(String path){
        return getRelativePath(getRootPath(), path);
    }

    public String getRelativePath(String rootPath, String path){
        if(isEmptyOrNull(path)) return path;
        String relPath = rel(path); // url格式化
        rootPath = rel(rootPath);

        if(!relPath.startsWith(rootPath)) return path;

        return relPath.substring(rootPath.length());
    }

}
