package com.ailbb.ajj.file;

import static com.ailbb.ajj.$.*;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;

/**
 * Created by Wz on 6/20/2018.
 */
public class $File {

    public String readFile(String path) {

        String content = "";
        InputStream is;

        try {
            path = getPath(path);

            info(String.format("Read file：%s", path));

            if(test("\\.jar!", path)) { // 如果内容在jar包内，则用流去读取
                is = new URL("jar:file:" + path).openConnection().getInputStream();
            } else {
                is = new FileInputStream(getFile(path));
            }

            if(null == is) throw new FileNotFoundException(path);

            return read(is);
        } catch (Exception e) {
            error(e);
        }

        return content;
    }

    public String read(InputStream is) {
        StringBuffer content = new StringBuffer();
        if(null != is) {
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader reader = new BufferedReader (new InputStreamReader(bis));
            //之所以用BufferedReader,而不是直接用BufferedInputStream读取,是因为BufferedInputStream是InputStream的间接子类,
            //InputStream的read方法读取的是一个byte,而一个中文占两个byte,所以可能会出现读到半个汉字的情况,就是乱码.
            //BufferedReader继承自Reader,该类的read方法读取的是char,所以无论如何不会出现读个半个汉字的.
            try {
                while (reader.ready()) {
                    content.append((char) reader.read());
                }
            } catch (Exception e) {
                error(e);
            } finally {
                try {
                    if(null != bis) bis.close();
                } catch (Exception e){
                    error(e);
                }
                try {
                    if(null != reader) reader.close();
                } catch (Exception e){
                    error(e);
                }
            }
        }

        return content.toString();
    }

    public void writeFile(String path, Object... object) {
        if(null == path) return;

        path = getPath(path);
        File file = getFile(path);
        FileWriter fw = null;
        try {
            info(String.format("Write file：%s", path));

            mkdir(path.substring(0, path.lastIndexOf("/")));
            fw = new FileWriter(file);
            if(null != object) for(Object o : object) fw.write(o.toString());
            fw.flush();
        } catch (Exception e) {
            error(e);
        } finally {
            try {
                if(null != fw) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String sourcePath, String destPath) {
        copyFile(sourcePath, destPath, false);
    }

    public void copyFile(String sourcePath, String destPath, boolean isReplace) {
        if(!isExists(sourcePath)) return;

        // format path
        sourcePath = getPath(sourcePath);
        destPath = getPath(destPath);
        File sfile = getFile(sourcePath);
        File dfile = getFile(destPath);

        if(!isFile(sourcePath)) {
            if(!dfile.exists()) dfile.mkdirs();

            for(String s: sfile.list()) {
                copyFile(concat(sourcePath, "/", s), concat(destPath, "/", s), isReplace);
            }
        } else if(dfile.exists() && !isReplace) {
            warn(String.format("%s is exists! -> %s", destPath, convert(dfile.length())));
        } else {
            FileChannel inputChannel = null;
            FileChannel outputChannel = null;
            try {
                info(String.format("Copy file：%s -> %s -> %s", sourcePath, destPath, convert(sfile.length())));
                inputChannel = new FileInputStream(sfile).getChannel();
                outputChannel = new FileOutputStream(dfile).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (Exception e) {
                error(e);
            } finally {
                try {
                    inputChannel.close();
                    outputChannel.close();
                } catch (Exception e) {
                    warn(e);
                }
            }
        }
    }

    public boolean isFile(String path){
        return getFile(path).isFile();
    }

    public boolean isExists(String path){
        return getFile(path).exists();
    }

    public File getFile(String path){
        return new File(getPath(path));
    }

    public void mkdir(String... path) {
        for(String p : path) {
            File file = new File(getPath(p));
            if(!file.exists()) {
                info(String.format("Make directory：%s", p));
                file.mkdirs();
            }
        }
    }

}
