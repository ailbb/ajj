package com.ailbb.ajj.file;

import com.ailbb.ajj.$;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ailbb.ajj.$.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Path {
    private String tempDirName = "temp";

    public String getPath(String path){
        String currpath = "";

        if(system().equals("windows")) {
            if(test("^[A-Za-z]:", path) || test("/[A-Za-z]:", path)  ) currpath = rel("/", path);
        } else {
            if(path.startsWith("/")) currpath = rel(path);
        }

        try {
            if(!isEmptyOrNull(currpath)) return rel(currpath);

            currpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        } catch (Exception e) {
            currpath = System.getProperty("user.dir");
        }

        return isEmptyOrNull(currpath) ? rel(path) : rel(subRootPath(currpath, 1), path);
    }

    public String getDirPath(String path){
        File file = $.file.getFile(path);

        if(!$.isEmptyOrNull(file)) {
            path = getPath(file.getPath());

            if(file.isFile())
                path = path.substring(0, path.lastIndexOf("/"));
        }

        return path;
    }

    public String getPath(String... paths){
        String path = "";
        for(String p : paths) path = $.concat(path, "/", p);
        return getPath(path);
    }

    public String getPath(Class clazz){
        if($.isEmptyOrNull(clazz)) clazz = Runtime.class;

        return clazz.getResource("").getPath().replaceFirst("file:", "");
    }

    public String getTempPath(){
        return getRootPath() + tempDirName;
    }

    public String getTempPath(String path){
        return getRootPath() + tempDirName + "/" + path;
    }

    public String getRootPath(){
        return getRootPath(null); // 获取当前类所处的位置
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

    public String subRootPath(String path, int type){
        try {
            if(type == 2 && !$.isEmptyOrNull($.tomcat.getRootPath())) return $.tomcat.getRootPath(); // 如果是web路径，且有tomcat的根目录，拿tomcat的目录

            PathPatten[] pathPattens = new PathPatten[0];

            switch (type) {
                case 1: // 1，jar包工程
                    pathPattens = new PathPatten[]{
                            new PathPatten(1, "/WEB-INF/"),
                            new PathPatten(2, "/classes/")
                    };
                    break;
                case 2: // 2，web工程
                    pathPattens = new PathPatten[]{
                            new PathPatten(2, "/webapp/"),
                            new PathPatten(2, "/web/"),
                            new PathPatten(2, "/webContent/"),
                            new PathPatten(2, "/src/main/webapp"),
                            new PathPatten(2, "/public"),
                            new PathPatten(2, "/static")
                    };
                    break;
                default:
                    break;
            }

            String prefix = ".jar!/";
            String jarPath = null;
            List<String> searchPaths = new ArrayList<>();
            int prefixIndex = (path = getPath(path + "/")).lastIndexOf(prefix);

            if(-1 != prefixIndex) {
                searchPaths.add((jarPath = path.substring(0, prefixIndex + prefix.length()))); // 如果有jar包，记录jar包的位置
            }

            searchPaths.add(path); // 最后查找当前目录

            for(PathPatten p : pathPattens) { // 匹配所有路径
                for(String s_path : searchPaths) {
                    int index = s_path.lastIndexOf(p.getPatten());

                    if(-1 != index) {
                       return s_path.substring(0, p.getType() == 1 ? index : (index + p.getPatten().length()));
                    }

                    String tempPath = getPath(s_path + "/");

                    // 先找同级
                    tempPath = tempPath.substring(0, tempPath.length() - 1);
                    tempPath = tempPath.substring(0, tempPath.lastIndexOf("/") + 1) + p.getPatten();

                    if($.isExists(tempPath)) return rel(tempPath);

                    // 再找下级
                    tempPath = s_path + "/" + p.getPatten();

                    if($.isExists(tempPath)) return rel(tempPath);
                }
            }

            // 如果所有都没匹配到，则直接返回jar的路径
            if(-1 != prefixIndex) return jarPath;
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

    public String getTempDirName() {
        return tempDirName;
    }

    public void setTempDirName(String tempDirName) {
        this.tempDirName = tempDirName;
    }

}
