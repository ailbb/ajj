package com.ailbb.ajj.jar;

import com.ailbb.ajj.$;

import java.io.File;
import java.lang.reflect.Method;

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
                $.warn(e);
                $.warn("Not found Intellij.idea.plugins [java-decompiler.jar], please Add [java-decompiler.jar] for classpath or project.");
                $.warn("You can open dir [\\JetBrains\\IntelliJ IDEA 2021.1\\plugins\\java-decompiler\\lib] found it [java-decompiler.jar].");
                return null;
            }
        }

        return null;
    }
}
