package com.ailbb.ajj.jar;

import com.ailbb.ajj.$;

import java.io.File;

public class $Jar {
    public void decompilerJar(String sourcePath, String targetPath){
        if($.file.isDirectory(sourcePath)) for(String f : $.getFile(sourcePath).list()) {
            if(f.endsWith(".jar")) decompilerJar(sourcePath + File.separator + f, targetPath);
        } else {
            if(!$.file.isExists(targetPath)) $.mkdir(targetPath);
            org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler.main(new String[]{"-dgs=true",sourcePath, targetPath});
        }
    }
}
