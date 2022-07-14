package com.ailbb.ajj.jar;

import com.ailbb.ajj.$;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class $Jar {
    public String decompilerJar(String sourcePath, String targetPath){
        if($.file.isDirectory(sourcePath)) for(String f : $.getFile(sourcePath).list()) {
            if(f.endsWith(".jar")) return decompilerJar(sourcePath + File.separator + f, targetPath);
            return null;
        } else {
            if(!$.file.isExists(targetPath)) {
                $.info("目录不存在，创建目录："+ targetPath);
                $.mkdir(targetPath);
            }

            try {
                $.info("Start Decompiler Jar..." + sourcePath);


                $.info("开始反编译...");

                Class clzz = Class.forName("org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler");

                clzz.getMethod("main", new Class[]{String[].class}).invoke(null, (Object)new String[]{"-dgs=true",sourcePath, targetPath});

                $.info("反编译成功...");
                return $.getPath(targetPath) + "/" + $.getFile(sourcePath).getName();
            } catch (Exception e) {
                String ideaPath = System.getenv("IntelliJ IDEA");
                if(!$.isEmptyOrNull(ideaPath)){
                    return decompilerJarLocalIntellij(sourcePath, targetPath, ideaPath);
                } else {
                    $.warn(e);
                    $.warn("Not found Intellij.idea.plugins [java-decompiler.jar], please Add [java-decompiler.jar] for classpath or project.");
                    $.warn("You can open dir [/JetBrains/IntelliJ IDEA 2021.1/plugins/java-decompiler/lib] found it [java-decompiler.jar].");
                }
                return null;
            }
        }

        return null;
    }

    public String decompilerJarLocalIntellij(String sourcePath, String targetPath, String ideaPath){
        try {
            if($.isEmptyOrNull(ideaPath)) ideaPath = System.getenv("IntelliJ IDEA");

            String sp = File.separator;
            String jarPath = $.getPath(ideaPath.substring(0, ideaPath.lastIndexOf(sp)) + "/plugins/java-decompiler/lib/java-decompiler.jar");
            File file = $.getFile(jarPath);
            $.warn("You are in Intellij idea path. Find news java-decompiler.jar [" + jarPath + "]");
            if(!file.exists()) {
                $.warn("Not found Intellij.idea.plugins [java-decompiler.jar], please Add [java-decompiler.jar] for classpath or project.");
                $.warn("You can open dir [/JetBrains/IntelliJ IDEA 2021.1/plugins/java-decompiler/lib] found it [java-decompiler.jar].");
            } else {
                $.info("loading ... " + jarPath);
                URL url =  new URL("file:"+file.getAbsolutePath());
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
                Class clzz = urlClassLoader.loadClass("org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler");

                clzz.getMethod("main", new Class[]{String[].class}).invoke(null, (Object)new String[]{"-dgs=true",sourcePath, targetPath});

                $.info("反编译成功...");
            }

        } catch (Exception e) {
            
        }
        return null;
    }

}
