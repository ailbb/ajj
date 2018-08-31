package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.file.ctl.$Ctl;
import com.ailbb.ajj.file.properties.$Properties;

import static com.ailbb.ajj.$.*;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by Wz on 6/20/2018.
 */
public class $File {
    public $Path path = new $Path();

    public $Ctl ctl = new $Ctl();

    public $Properties properties = new $Properties();

    public $Compress compress = new $Compress();

    public $Result zip(String path, String... paths)  {
        return compress.zip(path, paths);
    }

    public $Result zip(String path, List<String> paths)  {
        return compress.zip(path, paths);
    }

    public $Result zip(String path, boolean isDelete, String... paths)  {
        return compress.zip(path, isDelete, paths);
    }

    public $Result zip(String path, boolean isDelete, List<String> paths)  {
        return compress.zip(path, isDelete, paths);
    }

    public $Result readFile(String path)  {
        return readFile(path, charset.UTF8);
    }

    public $Result readFile(String path, String charset)  {
        try {
            return read(getInputStream(path), charset);
        } catch (IOException e) {
            return $.result().addError($.exception(e));
        }
    }

    public $Result readFile(File file)  {
        try {
            return read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return $.result().addError($.exception(e));
        }
    }

    public $Result read(InputStream is)  {
        return read(is, charset.UTF8);
    }
    
    public $Result read(InputStream is, String charset)  {
        $Result rs = $.result();

        StringBuffer content = new StringBuffer();
        if(null != is) {
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader reader = null;
            // 之所以用BufferedReader,而不是直接用BufferedInputStream读取,是因为BufferedInputStream是InputStream的间接子类,
            // InputStream的read方法读取的是一个byte,而一个中文占两个byte,所以可能会出现读到半个汉字的情况,就是乱码.
            // BufferedReader继承自Reader,该类的read方法读取的是char,所以无论如何不会出现读个半个汉字的.
            try {
                reader = new BufferedReader (new InputStreamReader(bis, charset));
                while (reader.ready()) {
                    content.append((char) reader.read());
                }

                rs.setData($.str(content));
            } catch (UnsupportedEncodingException e) {
                rs.addError($.exception(e));
            } catch (IOException e) {
                rs.addError($.exception(e));
            } finally {
                try {
                    if(null != bis) bis.close();
                } catch (IOException e){
                    rs.addError($.exception(e));
                }

                try {
                    if(null != reader) reader.close();
                } catch (IOException e){
                    rs.addError($.exception(e));
                }
            }
        }

        return rs;
    }

    public $Result writeFile(String path, Object... object)  {
        return writeFile(path, false, object);
    }

    public $Result writeFile(String path, boolean isAppend, Object... data)  {
        $Result rs = $.result();

        if(null == path) return rs;

        path = getPath(path);
        File file = getFile(path);
        FileWriter fw = null;
        try {
            if(file.exists() && isAppend)
                warn(String.format("Append file：%s", path));
            else
                info(String.format("Write file：%s", path));

            mkdir(path.substring(0, path.lastIndexOf("/")));
            fw = new FileWriter(file);
            if(null != data) for(Object o : data) fw.write(o.toString());
            fw.flush();

            rs.addData(path);
        } catch (IOException e) {
            rs.addError($.exception(e));
        } finally {
            try {
                if(null != fw) fw.close();
            } catch (IOException e) {
                $.warn(e);
            }
        }

        return rs;
    }

    public $Result copyFile(String sourcePath, String destPath)  {
        return copyFile(sourcePath, destPath, false);
    }

    public $Result copyFile(String sourcePath, String destPath, boolean isReplace)  {
        $Result rs = $.result();

        if(!isExists(sourcePath)) return rs;

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
            rs.addMessage(warn(String.format("%s is exists! -> %s", destPath, convert(dfile.length()))));
        } else {
            FileChannel inputChannel = null;
            FileChannel outputChannel = null;
            try {
                rs.addMessage(info(String.format("Copy file：%s -> %s -> %s", sourcePath, destPath, convert(sfile.length()))));
                inputChannel = new FileInputStream(sfile).getChannel();
                outputChannel = new FileOutputStream(dfile).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (FileNotFoundException e) {
                rs.addError(exception(e));
            } catch (IOException e) {
                rs.addError(exception(e));
            } finally {
                try {
                    inputChannel.close();
                    outputChannel.close();
                } catch (IOException e) {
                    rs.addError(exception(e));
                }
            }
        }

        return rs.addData("sourcePath", sourcePath).addData("destPath", destPath).addData("isReplace", isReplace);
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

    public InputStream getInputStream(String path) throws IOException {
        InputStream is = null;

        path = getPath(path);

        info(String.format("Read file：%s", path));

        if(test("\\.jar!", path)) { // 如果内容在jar包内，则用流去读取
            is = new URL("jar:file:" + path).openConnection().getInputStream();
        } else {
            is = new FileInputStream(getFile(path));
        }

        if(null == is) throw new FileNotFoundException(path);

        return is;
    }

    public $Result mkdir(String... path) {
        $Result rs = $.result();

        for(String p : path) {
            File file = new File(getPath(p));
            if(!file.exists()) {
                rs.addMessage(info(String.format("Make directory：%s", p)));
                rs.addData(file);
                file.mkdirs();
            }
        }

        return rs;
    }

}
