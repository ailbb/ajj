package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.file.csv.$CSV;
import com.ailbb.ajj.file.ctl.$Ctl;
import com.ailbb.ajj.file.excel.$Excel;
import com.ailbb.ajj.file.properties.$Properties;
import com.ailbb.ajj.file.yml.$Yml;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ailbb.ajj.$.*;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $File {
    public $Path path = new $Path();

    public $FileConfigure configure = new $FileConfigure();

    public $Excel excel = new $Excel();

    public $Ctl ctl = new $Ctl();

    public $CSV csv = new $CSV();

    public $Properties properties = new $Properties();

    public $Yml yml = new $Yml();

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

    public String readStr(InputStream is)  {
        return read(is).getDataToString();
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

    public $File writeFile(InputStream input, String path) throws IOException {
        //只能获取单input为单文件上传
        OutputStream output = null;
        try {
            writeFile(input,  output = new FileOutputStream($.getPath(path)));
        } catch (FileNotFoundException e) {
            $.mkdir(path);
            return writeFile(input, path);
        } catch (IOException e) {
            throw e;
        } finally {
            closeStearm(input).closeStearm(output);
        }
        return this;
    }

    public $File writeFile(InputStream input, OutputStream output) throws IOException {
        //只能获取单input为单文件上传
        try {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            closeStearm(input).closeStearm(output);
        }
        return this;
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

    public $Result copyFile(File file, String destPath)  {
        return copyFile(file, destPath, false);
    }

    public $Result copyFile(File sfile, String destPath, boolean isReplace)  {
        $Result rs = $.result();
        File dfile = getFile(destPath);
        String sourcePath = sfile.getPath();

        if(!sfile.isFile()) {
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

    public $Result copyFile(String sourcePath, String destPath, boolean isReplace)  {
        $Result rs = $.result();

        if(!isExists(sourcePath)) return rs;

        return copyFile(getFile(sourcePath), destPath, isReplace);
    }

    public boolean isFile(String path){
        return getFile(path).isFile();
    }

    public boolean isDirectory(String path){
        return getFile(path).isDirectory();
    }

    public boolean isExists(String path){
        try {
            getInputStream(path).close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public File getFile(String path){
        return new File(getPath(path));
    }

    public String getFileType(File file){
        return getFileType(file.getName());
    }

    public String getFileType(String fileName){
        if($.isEmptyOrNull(fileName)) return null;

        String[] fileNameSplit = fileName.split("\\.");

        return fileNameSplit.length > 1 ? fileNameSplit[fileNameSplit.length-1] : null;
    }

    public $Result uploadFile(HttpServletRequest request) {
        return uploadFile(request, null);
    }

    public $Result uploadFile(HttpServletRequest request, String path) {
        return uploadFile(request, null, path, null);
    }

    /**
     * 通用解析器
     * @param request 上传请求
     * @param response
     * @param path 上传路径
     * @param type 筛选文件类型
     * @return
     */
    public $Result uploadFile(HttpServletRequest request, HttpServletResponse response, String path, String type) {
        if($.isEmptyOrNull(path)) path = getPath($.concat(getRootPath(), "/upload", "/", $.now("nm")));
        $Result rs = $.result();

        ///处理中文乱码问题
        try {
            if(!$.isEmptyOrNull(response)) response.setContentType("text/html;ENCODING=utf-8");
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            rs.addError(exception(e));
        }

        //检查请求是否是multipart/form-data类型
        if(!ServletFileUpload.isMultipartContent(request)){  //不是multipart/form-data类型
            return rs.setSuccess(false).addMessage("Form type is not equals multipart/form-data!");
        }

        // 针对spring封装后的解析器
        if(request instanceof MultipartHttpServletRequest) {
            // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                List<MultipartFile> multi_file = multiRequest.getFiles(iter.next().toString());
                for (MultipartFile file : multi_file) {
                    rs.concat(uploadFile(file, path, type));
                }
            }
        } else {
            //创建上传所需要的两个对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //解析器依赖于工厂
            ServletFileUpload sfu = new ServletFileUpload(factory);
            //创建容器来接受解析的内容
            List<FileItem> items;

            //将上传的文件信息放入容器中
            try {
                items = sfu.parseRequest(request);
            } catch (FileUploadException e) {
                return rs.addError(exception(e));
            }

            //遍历容器,处理解析的内容;封装两个方法，一个处理普通表单域，一个处理文件的表单域
            for(FileItem item : items){
                rs.concat(uploadFile(item, path, type));
            }
        }

        return rs;
    }

    public $Result uploadFile(MultipartFile file, String path, String type){
        try {
            if(file instanceof CommonsMultipartFile) {
                return uploadFile(((CommonsMultipartFile) file).getFileItem(), path, type);
            }
        } catch (Exception e) {
            $.warn(e);
        }

        $Result rs = mkdir(path);
        $FileInfo fi = new $FileInfo(file);
        $.timeclock();

        String fileName = fi.getFileName();  //得到上传文件的文件名

        if(!$.isEmptyOrNull(fileName) && !$.isEmptyOrNull(type) && !fi.getType().startsWith(type)){
            return rs.addMessage(false, "Type is not equals：" + type);
        }

        try {
            //写入服务器或者磁盘
            file.transferTo(fi.initFile(new File($.getPath(path, fileName))).getFile());
            //向控制台打印文件信息
            info(String.format("Upload file：%s, Size: %s, RunTime：%s ms", fileName, fi.getSize(), fi.setRunTime($.timeclock()).getRunTime()));
        } catch (Exception e) {
            return rs.addError(exception(e));
        }

        return rs.setData(fi);
    }

    public $Result uploadFile(FileItem item, String path, String type){
        $Result rs = mkdir(path);
        $FileInfo fi = new $FileInfo(item);
        $.timeclock();

        if(item.isFormField()){
            // 打印文件信息
            info(String.format("Upload info：%s, Value: %s", fi.getFieldName(), fi.getContent()));
        } else {
            String fileName = fi.getFileName();  //得到上传文件的文件名

            if(!$.isEmptyOrNull(fileName) && !$.isEmptyOrNull(type) && !fi.getType().startsWith(type)){
                return rs.addMessage(false, "Type is not equals：" + type);
            }

            try {
                //写入服务器或者磁盘
                item.write(fi.initFile(new File($.getPath(path, fileName))).getFile());
                //向控制台打印文件信息
                info(String.format("Upload file：%s, Size: %s, RunTime：%s ms", fileName, fi.getSize(), fi.setRunTime($.timeclock()).getRunTime()));
            } catch (Exception e) {
                return rs.addError(exception(e));
            }
        }

        return rs.setData(fi);
    }

    public InputStream getInputStream(String path) throws IOException {
        InputStream is = null;

        path = getPath(path);

        if(test("\\.jar!", path)) { // 如果内容在jar包内，则用流去读取
            is = new URL("jar:file:" + path).openConnection().getInputStream();
        } else {
            is = new FileInputStream(getFile(path));
        }

        if(null == is) throw new FileNotFoundException(path);

        info(String.format("Read file：%s", path));

        return is;
    }

    public InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    public List[] parseHeaderAndData(List list){
        List<Object> headers = new ArrayList<>();
        List<List<Object>> datas = new ArrayList<>();

        for(Object o : list) {
            try {
                Map<String,Object> map = (Map<String,Object>)o;
                if($.isEmptyOrNull(headers)) for(String k: map.keySet()) headers.add(k);
                datas.add($.list.toList(map.values()));
            } catch (Exception e) {
                datas.add((List<Object>)o);
            }
        }

        return new List[]{headers, datas};
    }

    public $Result delete(String... path) {
        $Result rs = $.result();

        for(String p : path) {
            File file = new File(getPath(p));
            if(!file.exists()) {
                rs.addMessage(info(String.format("Delete directory：%s", p)));
                file.deleteOnExit();
            }
        }

        return rs;
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

    public $File closeStearm(AutoCloseable closeable){
        if(null != closeable)
            try {
                closeable.close();
            } catch (IOException e) {
                $.exception(e);
            } catch (Exception e) {
                $.exception(e);
            }

        return this;
    }
}
