package com.ailbb.ajj.jar;

import com.ailbb.ajj.$;

import java.io.File;
import java.lang.reflect.Method;

public class $Jar {
    public void decompilerJar(String sourcePath, String targetPath){
        if($.file.isDirectory(sourcePath)) for(String f : $.getFile(sourcePath).list()) {
            if(f.endsWith(".jar")) decompilerJar(sourcePath + File.separator + f, targetPath);
        } else {
            if(!$.file.isExists(targetPath)) $.mkdir(targetPath);
            try {

                Class clzz = Class.forName("org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler");

                clzz.getMethod("main", new Class[]{String[].class}).invoke(null, (Object)new String[]{"-dgs=true",sourcePath, targetPath});
            } catch (Exception e) {
                $.warn(e);
                $.warn("Not found Intellij.idea.plugins [java-decompiler.jar], please Add [java-decompiler.jar] for classpath or project.");
                $.warn("You can open dir [\\JetBrains\\IntelliJ IDEA 2021.1\\plugins\\java-decompiler\\lib] found it [java-decompiler.jar].");
            }
        }
    }
}
