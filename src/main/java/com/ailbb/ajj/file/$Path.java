package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Path {
    public String getPath(String path){
        String currpath = "";

        if(isEmptyOrNull(path)) path = "/";

        if(system().equals("windows")) {
            if(test("^[A-Za-z]:", path) || test("/[A-Za-z]:", path)  ) currpath = rel("/", path);
        } else {
            if(path.startsWith("/")) currpath = rel(path);
        }

        try {
            if(!isEmptyOrNull(currpath)) return rel(currpath);

            currpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        } catch (Exception e) {
            String userdir = System.getProperty(System.getProperty("user.dir"));
            String uclasspath = System.getProperty(System.getProperty("java.class.PathPatten"));

            if(!isEmptyOrNull(userdir)) currpath = userdir;

            if(!$.isEmptyOrNull(uclasspath)) {
                if(!$.isEmptyOrNull(currpath) && -1 == uclasspath.indexOf(currpath))
                    currpath =  currpath + "/" + uclasspath;
                else
                    currpath = uclasspath;
            }
        }


        return isEmptyOrNull(currpath) ? rel(path) : rel(subRootPath(currpath, 1), path);
    }

    public String getPath(String... paths){
        String path = "";
        for(String p : paths) path = $.concat(path, "/", p);
        return getPath(path);
    }

    public String getPath(Class clazz){
        if($.isEmptyOrNull(clazz)) clazz = Bootstrap.class;

        return clazz.getResource("").getPath().replaceFirst("file:", "");
    }

    public String getRootPath(){
        return getRootPath(null);
    }

    public String getRootPath(Class clazz){
        return subRootPath(null == clazz ? getPath("") : getPath(clazz), 1); // 获取当前类所处的位置
    }

    public String getWebRootPath(){
        return getWebRootPath(null);
    }

    public String getWebRootPath(Class clazz){
        return subRootPath(null == clazz ? getPath("") : getPath(clazz), 2); // 获取当前类所处的位置
    }

    private String subRootPath(String path, int type){
        try {
            PathPatten[] paths = new PathPatten[0];

            switch (type) {
                case 1: // 1，jar包工程
                    paths = new PathPatten[]{
                            new PathPatten(1, "/WEB-INF/"),
                            new PathPatten(2, "/classes/"),
                            new PathPatten(2, "\\.jar!/")
                    };
                    break;
                case 2: // 2，web工程
                    paths = new PathPatten[]{
                            new PathPatten(2, "/webapp/"),
                            new PathPatten(2, "/web/"),
                            new PathPatten(2, "/webContent/"),
                    };
                    break;
                default:
                    break;
            }

            for(PathPatten p : paths) {
                int index = path.lastIndexOf(p.getPatten());

                if(-1 != index) {
                    path = path.substring(0, p.getType() == 1 ? index : (index + p.getPatten().length()));
                    break;
                }

                // 如果是web路径，且没有找到，则遍历目录下的web文件夹
                if(type == 2) {
                    String tempPath = getPath(path + "/");

                    // 先找同级
                    tempPath = tempPath.substring(0, tempPath.lastIndexOf("/"));
                    tempPath = tempPath.substring(0, tempPath.lastIndexOf("/") + 1) + p.getPatten();

                    if($.isExists(tempPath)) {
                        path = tempPath;
                        break;
                    }

                    // 再找下级
                    tempPath = path + "/" + p.getPatten();

                    if($.isExists(tempPath)) {
                        path = tempPath;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            $.warn(e);
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

    private static class PathPatten {
        int type; // 1表示截取之前的，2表示截取之后的
        String patten;

        public PathPatten(int type, String patten) {
            this.type = type;
            this.patten = patten;
        }

        public int getType() {
            return type;
        }

        public PathPatten setType(int type) {
            this.type = type;
            return this;
        }

        public String getPatten() {
            return patten;
        }

        public PathPatten setPatten(String patten) {
            this.patten = patten;
            return this;
        }
    }

}
