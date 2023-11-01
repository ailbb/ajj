package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.file.csv.$CSV;
import com.ailbb.ajj.file.ctl.$Ctl;
import com.ailbb.ajj.file.excel.$Excel;
import com.ailbb.ajj.file.properties.$Properties;
import com.ailbb.ajj.file.tool.FileCounter;
import com.ailbb.ajj.file.tool.FileMerge;
import com.ailbb.ajj.file.xml.$Xml;
import com.ailbb.ajj.file.yml.$Yml;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileUploadException;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.ailbb.ajj.$.*;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $File {
    public $Path path = new $Path();

    public $FileConfigure configure = new $FileConfigure();

    public $Excel excel = new $Excel();

    public $Ctl ctl = new $Ctl();

    public $Xml xml = new $Xml();

    public $CSV csv = new $CSV();

    public FileMerge merge = new FileMerge();

    public $Properties properties = new $Properties();

    public $Yml yml = new $Yml();

    public $Compress compress = new $Compress();

    public $FileTailReader fileTailReader = new $FileTailReader();

    public $Result zip(String path, String... paths)  {
        return compress.zip(path, paths);
    }

    public $Result zip(String path, List<String> paths)  {
        return compress.zip(path, paths);
    }

    public $Result unzip(String path)  {
        return compress.unzip(path);
    }

    public $Result unzip(String path, String descPath)  {
        return compress.unzip(path, descPath);
    }

    public $Result zip(String path, boolean isDelete, String... paths)  {
        return compress.zip(path, isDelete, paths);
    }

    public $Result zip(String path, boolean isDelete, List<String> paths)  {
        return compress.zip(path, isDelete, paths);
    }

    public File zip(File file, String targetPath)  {
        return compress.zip(file, targetPath);
    }

    public File gzip(File file, String targetPath)  {
        return compress.gzip(file, targetPath);
    }

    public boolean isCompressFile(String fileName){
        return compress.isCompressFile(fileName);
    }

    public $Result readFile(String path)  {
        return readFile(path, charset.UTF8);
    }

    public String readFileToText(String path)  {
        return readFileToText(path, charset.UTF8);
    }

    public $Result readFile(String path, String charset)  {
        try {
            return read(getInputStream(path), charset);
        } catch (IOException e) {
            return $.result().addError($.exception(e));
        }
    }

    public String readFileToText(String path, String charset)  {
        try {
            return read(getInputStream(path), charset).getDataToString();
        } catch (IOException e) {
            $.warn(e);
            return "";
        }
    }

    public String readFileToText(File file)   {
        return readFileToText(file, 0, false);
    }
    public String readFileToText(File file, long currentPos)   {
        return readFileToText(file, currentPos, true);
    }
    public String readFileToText(File file, long currentPos, boolean hasNewLine)   {
        return readFileToText(file, currentPos, charset.UTF8, hasNewLine);
    }

    public String readFileToText(File file, long currentPos, String chartSet)   {
        return readFileToText(file, currentPos, chartSet, true);
    }

    public String readFileToText(File file, long currentPos, String chartSet, boolean hasNewLine)   {
        try {
            return readText(new FileInputStream(file), currentPos, chartSet, hasNewLine);
        } catch (IOException e) {
            $.warn(e);
            return "";
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
    public String readText(InputStream in, long currentPos, String charset)  {
        return readText(in, currentPos, charset, true);
    }

    public String readText(InputStream in, long currentPos, String charset, boolean hasNewLine)  {
        StringBuffer data = new StringBuffer();
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedReader reader = null;
        try {
            in.skip(currentPos);
            reader = new BufferedReader (new InputStreamReader(bis, charset));
            ByteBuffer bb = ByteBuffer.allocate(10);

            while (reader.ready()) {
                data.append((char) reader.read());
            }
            if(hasNewLine) data.append("\n");
            return data.toString();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            $.file.closeStream(in);
        }
        return "";

    }

    public long countLine(File f) {
        return countLine(f, (text,i) -> text);
    }

    public String readLine(File f) {
        return readLine(f, (text,i) -> text);
    }

    public String readLine(File f, $FileReplacer fr) {
        try {
            return readLine(new FileInputStream(f), charset.UTF8, fr);
        } catch (FileNotFoundException e) {
            $.exception(e);
        }
        return "";
    }

    public long countLine(File f, $FileReplacer fr) {
        try {
            return countLine(new FileInputStream(f), charset.UTF8, fr);
        } catch (FileNotFoundException e) {
            $.exception(e);
        }
        return 0;
    }

    public String readLine(InputStream is) {
        return readLine(is, charset.UTF8, (text,i) -> text);
    }

    public String readLine(InputStream is, $FileReplacer fr) {
        return readLine(is, charset.UTF8, fr);
    }

    public String readLine(InputStream is, String charset) {
        return readLine(is, charset, (text,i) -> text);
    }

    public String readLine(InputStream is, String charset, $FileReplacer fr) {
        StringBuffer content = new StringBuffer();
        if(null != is) {
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader reader = null;
            // 之所以用BufferedReader,而不是直接用BufferedInputStream读取,是因为BufferedInputStream是InputStream的间接子类,
            // InputStream的read方法读取的是一个byte,而一个中文占两个byte,所以可能会出现读到半个汉字的情况,就是乱码.
            // BufferedReader继承自Reader,该类的read方法读取的是char,所以无论如何不会出现读个半个汉字的.
            try {
                reader = new BufferedReader (new InputStreamReader(bis, charset));
                String row;
                int index = 0;
                while ((row = reader.readLine()) != null) {
                    Object rc = fr.getRowContext(row, index++);
                    if(null != rc) content.append(rc + "\r\n");
                }

                return content.toString();
            } catch (UnsupportedEncodingException e) {
                $.exception(e);
            } catch (IOException e) {
                $.exception(e);
            } finally {
                $.file.closeStream(bis);
                $.file.closeStream(reader);
            }
        }

        return "";
    }

    public long countLine(InputStream is, String charset, $FileReplacer fr) {
        StringBuffer content = new StringBuffer();
        if(null != is) {
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader reader = null;
            // 之所以用BufferedReader,而不是直接用BufferedInputStream读取,是因为BufferedInputStream是InputStream的间接子类,
            // InputStream的read方法读取的是一个byte,而一个中文占两个byte,所以可能会出现读到半个汉字的情况,就是乱码.
            // BufferedReader继承自Reader,该类的read方法读取的是char,所以无论如何不会出现读个半个汉字的.
            try {
                reader = new BufferedReader (new InputStreamReader(bis, charset));
                String row;
                int index = 1;
                while ((row = reader.readLine()) != null) index++;

                return index;
            } catch (UnsupportedEncodingException e) {
                $.exception(e);
            } catch (IOException e) {
                $.exception(e);
            } finally {
                $.file.closeStream(bis);
                $.file.closeStream(reader);
            }
        }

        return 0;
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
            closeStream(input).closeStream(output);
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
            closeStream(input).closeStream(output);
        }
        return this;
    }

    public $Result writeFile(String path, String... datas)  {
        return writeFile(path, false, datas);
    }

    public $Result writeFile(File file, String... datas)  {
        return writeFile(file, false, datas);
    }

    public $Result writeFile(String path, boolean isAdd, String... datas)  {
        $Result rs = $.result();

        if(null == path) return rs;

        File file = getFile(path = getPath(path));

        return writeFile(file, isAdd, datas);
    }

    public $Result writeFile(File file, boolean isAdd, String... datas)  {
        $Result rs = $.result();

        if(null == file) return rs;

        FileWriter fw = null;
        String path = getPath(file.getPath());

        try {
            if(file.exists() && isAdd)
                debugOut(String.format("Append Data To [%s]", file.getName()));
            else
                debugOut(String.format("Write Data To [%s]", file.getName()));

            if(!file.exists()) mkdir(path.substring(0, path.lastIndexOf("/")));

            fw = new FileWriter(file, isAdd);
            if(null != datas) for(String s : datas) fw.write(s);
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

    /**
     * 比对文件是否被修改
     * @param spath
     * @param dPath
     * @return
     */
    boolean isModify(String spath, String dPath){
        return isModify($.getFile(spath), $.getFile(dPath));
    }

    /**
     * 比对文件是否被修改
     * @param sfile
     * @param dfile
     * @return
     */
    boolean isModify(File sfile, File dfile){
        return getModify(sfile, dfile)!=0;
    }

    /**
     * 比对文件是否被修改
     * @param spath
     * @param dPath
     * @return
     */
    int getModify(String spath, String dPath){
        return getModify($.getFile(spath), $.getFile(dPath));
    }

    int getModify(File sfile, File dfile){
        int modify = 1;
        if(!sfile.exists() || !dfile.exists()) return modify;

        if(!sfile.getName().equals(dfile.getName())) return modify;

        return (int)(sfile.lastModified() - dfile.lastModified());
    }

    public $Result copyFile(String sourcePath, String destPath)  {
        return copyFile(sourcePath, destPath, getModify(sourcePath, destPath)>0);
    }

    public $Result copyFile(File file, String destPath)  {
        return copyFile(file, destPath, getModify(file, $.getFile(destPath))>0);
    }

    public $Result copyFile(File sfile, String destPath, boolean isReplace)  {
        return copyFile(sfile, destPath, isReplace, false);
    }

    public $Result copyFile(File sfile, String destPath, boolean isReplace, boolean isDel)  {
        $Result rs = $.result();
        File dfile = getFile(destPath); // 拷贝输出文件
        String sourcePath = sfile.getPath(); // 源文件

        if(!sfile.exists()) return rs.addMessage($.warn("File Not Found："+sfile.getPath()));

        if(!sfile.isFile()) {

            for(String s: sfile.list()) {
                copyFile(concat(sourcePath, "/", s), concat(destPath, "/", s), isReplace);
            }
        } else if(dfile.exists() && !isReplace) {
            rs.addMessage(warn(String.format("%s is exists! -> %s", destPath, convert(dfile.length()))));
        } else if(!sfile.getPath().equals(dfile.getPath())){
            mkdirOrFile(dfile);

            FileChannel inputChannel = null;
            FileChannel outputChannel = null;
            try {
                rs.addMessage(debugOut(String.format("Copy file：%s -> %s -> %s", sourcePath, destPath, convert(sfile.length()))));
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

                    if(isDel) sfile.deleteOnExit();
                } catch (IOException e) {
                    rs.addError(exception(e));
                }
            }
        }

        return rs.addData("sourcePath", sourcePath).addData("destPath", destPath).addData("isReplace", isReplace);
    }


    /**
     * 拷贝文件流到指定的文件
     * @param inputStream 文件流
     * @param destFilePath 目标文件路径
     * @param isReplace 如果存在是否替换
     * @return 结果
     */
    public $Result copyFile(InputStream inputStream, String destFilePath, boolean isReplace)  {
        $Result rs = $.result();
        File dfile = getFile(destFilePath);

        if(dfile.exists() && !isReplace) {
            rs.addMessage(warn(String.format("%s is exists! -> %s", destFilePath, convert(dfile.length()))));
        } else {
            mkdirOrFile(dfile); // 创建文件的

            OutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(dfile));

                int temp = 0;
                while((temp = inputStream.read()) != -1){
                    outputStream.write(temp);
                }

            } catch (FileNotFoundException e) {
                rs.addError(exception(e));
            } catch (IOException e) {
                rs.addError(exception(e));
            } finally {
                $.file.closeStream(inputStream);
                $.file.closeStream(outputStream);
            }
        }

        return rs.addData("destPath", destFilePath).addData("isReplace", isReplace);
    }


    public void searchPath(String path, $FileRunner runner) {
        File f = $.file.getFile(path);

        if(f.isDirectory()) {
            for(String p : f.list()) {
                searchPath(path+"\\"+p, runner);
            }
        } else {
            runner.run(f);
        }
    }


    public static void copyFileToModifyFix(String path, String sourceFix, String targetFix, boolean isReplace, boolean isDel, $FileReplacer runnable) {
        File f = $.file.getFile(path);

        sourceFix = (sourceFix.equals("*") || sourceFix.equals(".*")) ? "\\.[^\\.]+$" : (sourceFix + "$"); // 默认匹配结尾的
        sourceFix = sourceFix.replaceAll("\\$+", "\\$"); // 全匹配

        if(f.isDirectory()) {
            for(String p : f.list()) {
                copyFileToModifyFix(path+"\\"+p, sourceFix, targetFix, isReplace, isDel, runnable);
            }
        } else {
            if($.test(sourceFix, f.getPath())) {
                File targetFile = $.getFile(f.getPath().replaceAll(sourceFix, targetFix));

                if(!isReplace && targetFile.exists()) return; // 如果不替换，文件存在时候就跳过

                $.file.writeFile(targetFile, $.file.readLine(f, runnable));
                if(isDel) f.deleteOnExit();
            }
        }
    }

    public static void copyFileToModifyFix(String path, String sourceFix, String targetFix) {
        copyFileToModifyFix(path, sourceFix, targetFix, false, false);
    }

    public static void copyFileToModifyFix(String path, String sourceFix, Map<String,String> contextFilter) {
        copyFileToModifyFix(path, sourceFix, contextFilter, false, false);
    }

    public static void copyFileToModifyFix(String path, String sourceFix, String targetFix, boolean isReplace, boolean isDel) {
        File f = $.file.getFile(path);

        sourceFix = (sourceFix.equals("*") || sourceFix.equals(".*")) ? "\\.[^\\.]+$" : (sourceFix + "$"); // 默认匹配结尾的
        sourceFix = sourceFix.replaceAll("\\$+", "\\$"); // 全匹配

        if(f.isDirectory()) {
            for(String p : f.list()) {
                copyFileToModifyFix(path+"\\"+p, sourceFix, targetFix, isReplace, isDel);
            }
        } else {
            if($.test(sourceFix, f.getPath())) {
                $.file.copyFile(f, f.getPath().replaceAll(sourceFix, targetFix), isReplace, isDel);
            }
        }
    }

    public static void copyFileToModifyFix(String path, String sourceFix, Map<String,String> contextFilter, boolean isReplace, boolean isDel) {
        File f = $.file.getFile(path);

        sourceFix = (sourceFix.equals("*") || sourceFix.equals(".*")) ? "\\.[^\\.]+$" : (sourceFix + "$"); // 默认匹配结尾的
        sourceFix = sourceFix.replaceAll("\\$+", "\\$"); // 全匹配

        if(f.isDirectory()) {
            for(String p : f.list()) {
                copyFileToModifyFix(path+"\\"+p, sourceFix, contextFilter, isReplace, isDel);
            }
        } else {
            if($.test(sourceFix, f.getPath())) {
                for(String key : contextFilter.keySet()) {
                    if($.readFileToText(f.getPath()).contains(key)) {
                        $.file.copyFile(f, f.getPath().replaceAll(sourceFix, contextFilter.get(key)), isReplace, isDel);
                    }
                }
            }
        }
    }

    public $Result copyFile(String sourcePath, String destPath, boolean isReplace) {
        $Result rs = $.result();

        if(!isExists(sourcePath)) return rs.addMessage($.warn("sourcePath ["+sourcePath+"] not found."));

        try {
            return
                    test("\\.jar!", sourcePath) ?
                            copyFile(getInputStream(sourcePath), destPath, isReplace) :
                            copyFile(getFile(sourcePath), destPath, isReplace);
        } catch (Exception e) {
            $.warn("获取文件流失败..." + e);
        }

        return rs;
    }

    public boolean isFile(String path){
        return getFile(path).isFile();
    }

    public boolean isDirectory(String path){
        return getFile(path).isDirectory();
    }

    public boolean isExists(String path){
        return isExists(path, false);
    }
    public boolean isExists(File file){
        return file.exists();
    }

    public boolean isExists(String path, boolean isFormatPath){
        try {
            getInputStream(path, isFormatPath).close();
        } catch (IOException e) {
            try {
                return new File(path).exists();
            } catch (Exception e1) {
                return false;
            }
        }

        return true;
    }


    /**
     * 检查文件，如果不存在则创建
     * @param path
     * @return
     */
    public boolean checkToDo(String path){
        if(!isExists(path)) {
            $.info("路径不存在，创建路径："+ path);
            $.mkdirOrFile(path);
            return false;
        }
        return true;
    }

    public File getFile(String path){
        return new File(getPath(path));
    }

    public File getFile(String parent, String child) {
        return new File(getPath(path + "/"+child));
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

    /*
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
        if(!JakartaServletFileUpload.isMultipartContent(request)){  //不是multipart/form-data类型
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
            //解析器依赖于工厂
            JakartaServletFileUpload sfu = new JakartaServletFileUpload(
                    DiskFileItemFactory.builder().get() //创建上传所需要的两个对象
            );
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
                item.write(fi.initFile(new File($.getPath(path, fileName))).getFile().toPath().getParent());
                //向控制台打印文件信息
                info(String.format("Upload file：%s, Size: %s, RunTime：%s ms", fileName, fi.getSize(), fi.setRunTime($.timeclock()).getRunTime()));
            } catch (Exception e) {
                return rs.addError(exception(e));
            }
        }

        return rs.setData(fi);
    }

    public InputStream getInputStream(String path) throws IOException {
        return getInputStream(path, true);
    }


    public InputStream getInputStream(String path, boolean isFormatPath) throws IOException {
        InputStream is = null;

        path = isFormatPath ? getPath(path) : path;

        if(test("\\.jar!", path)) { // 如果内容在jar包内，则用流去读取
            is = new URL("jar:file:" + path).openConnection().getInputStream();
        } else {
            is = new FileInputStream(getFile(path));
        }

        if(null == is) throw new FileNotFoundException(path);

        debugOut(String.format("Read file：%s", path));

        return is;
    }

    public InputStream getInputStream(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        if(null == is) throw new FileNotFoundException(file.getAbsolutePath());

        debugOut(String.format("Read file：%s", file.getAbsolutePath()));

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

    public $Result deleteFile(String... paths)  {
        return delete(paths);
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

    public $Result deleteFile(File... files)  {
        return delete(files);
    }

    public $Result delete(File... files) {
        $Result rs = $.result();

        for(File file : files) {
            if(null != file && !file.exists()) {
                rs.addMessage(warn(String.format("Delete directory：%s", file.getPath())));
                file.deleteOnExit();
            }
        }

        return rs;
    }


    public $Result deleteRF(String... path) {
        $Result rs = $.result();

        for(String p : path) {
            File file = new File(getPath(p));
            if(file.exists()) {
                deleteRF(file);
            }
        }

        return rs;
    }

    public $Result deleteFileRF(File... files)  {
        return deleteRF(files);
    }

    public $Result deleteRF(File... files) {
        $Result rs = $.result();

        for(File file : files) {
            if(null != file && file.exists()) {
                rs.addMessage(warn(String.format("Delete File：%s", file.getPath())));
                if(file.isDirectory() && file.listFiles().length != 0){
                    deleteRF(file.listFiles());
                }

                file.delete();
            }
        }

        return rs;
    }

    public $Result mkdir(String... path) {
        $Result rs = $.result();

        for(String p : path) {
            mkdir(new File(getPath(p)));
        }

        return rs;
    }

    public $Result mkfile(String... path) {
        $Result rs = $.result();

        for(String p : path) {
            File _mkfile = $.getFile(p);

            try {
                mkdirParents(_mkfile).createNewFile();
            } catch (IOException e) {
                rs.addError(e);
            }
        }

        return rs;
    }

    public $Result mkfile(File... files) {
        $Result rs = $.result();

        for(File file : files) {
            try {
                rs.addMessage(debugOut(String.format("Create File：%s", file.getPath())));
                mkdirParents(file).createNewFile();
            } catch (IOException e) {
                rs.addError(e);
            }
        }

        return rs;
    }


    public $Result mkdirOrFile(String... path) {
        $Result rs = $.result();

        for(String p : path) {
            mkdirOrFile($.getFile(p));
        }

        return rs;
    }

    public $Result mkdirOrFile(File... files) {
        $Result rs = $.result();

        for(File file : files) {
            if(file.exists()) continue;

            int fidx = file.getName().lastIndexOf(".");

            if(fidx == 0) {
                mkfile(file);
            } else if(fidx > 0 && $.isSuffix(file.getName().substring(fidx+1))) {// 如果是文件名带小数点，认为是文件
                mkfile(file);
            } else {
                mkdir(file);
            }

            rs.addData(file);
        }

        return rs;
    }


    public $Result mkdir(File... files) {
        $Result rs = $.result();

        for(File file : files) {
            if(!file.exists()) {
                rs.addMessage(debugOut(String.format("Make directory：%s", file.getPath())));
                file.mkdirs(); // 创建目录
            }
        }

        return rs;
    }


    public File mkdirParents(File... files) {
        for(File file : files) {
            file.getParentFile().mkdirs(); // 创建目录
        }

        return $.last(files);
    }


    public static void fileTypeSizeCount(String path, Map<String,String> contextFilter){
        FileCounter.fileTypeSizeCount(path, contextFilter);
    }

    public static void fileLineCount(String path, Map<String,String> contextFilter){
        FileCounter.fileLineCount(path, contextFilter);
    }

    public $File close(AutoCloseable... closeables){
        for(AutoCloseable closeable : closeables) {
            if(null != closeable)
                try {
                    closeable.close();
                } catch (IOException e) {
                    $.exception(e);
                } catch (Exception e) {
                    $.exception(e);
                }
        }

        return this;
    }

    public $File closeStream(AutoCloseable... closeables){
        for(AutoCloseable closeable : closeables) {
            if(null != closeable)
                try {
                    closeable.close();
                } catch (IOException e) {
                    $.exception(e);
                } catch (Exception e) {
                    $.exception(e);
                }
        }

        return this;
    }

    public $File closeStream(AutoCloseable closeable, long dealyTimeOut, String flagKey){
        $DelayCloseable.doDelayCloseable(closeable, dealyTimeOut, flagKey);

        return this;
    }


    public List<File> getFiles(String[] paths){
        List<File> fileList = new ArrayList<>();
        for(String path : paths) fileList.addAll(getFiles(path));
        return fileList;
    }

    /**
     * 扫描资源文件所在的路径
     * @param paths 需要扫描的包路径
     * @return 资源列表
     */
    public Resource[] scanFilesResource(String... paths){
        Resource[] mapperLocations = new Resource[]{};
        for(String packageSearchPath: paths) {
            Resource[] _mapperLocation = new Resource[0];
            try {
                _mapperLocation = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
            } catch (IOException e) {
                $.warn("扫描错误：" + e);
            }
            mapperLocations = Arrays.copyOf(mapperLocations, mapperLocations.length + _mapperLocation.length); // 扩容
            System.arraycopy(_mapperLocation, 0, mapperLocations, mapperLocations.length - _mapperLocation.length, _mapperLocation.length); // 临时数组和数组合并
        }
        return mapperLocations;
    }

    public List<File> scanPath(String path) {
        List<File> paths = new ArrayList<>();
        scanPath(path, (f)->{
            paths.add(f);
        });
        return paths;
    }

    public List<String> scanPathToString(String path) {
        List<String> paths = new ArrayList<>();
        scanPath(path, (f)->{
            paths.add(f.getAbsolutePath());
        });
        return paths;
    }

    public void scanPath(String path, $FileRunner runner) {
        File f = $.file.getFile(path);

        if(f.isDirectory()) {
            for(String p : f.list()) {
                scanPath(path+"\\"+p, runner);
            }
        } else {
            runner.run(f);
        }
    }

    public List<File> getFiles(String path){
        return getFiles(new File(path));
    }

    public List<File> getFiles(File file){
        List<File> fileList = new ArrayList<>();

        if (file.isDirectory()) {
            fileList.addAll(this.getFiles(file));
        } else if (file.isFile()) {
            fileList.add(file);
        } else {
            $.warn("Error file." + file.getName());
        }

        return fileList;
    }


    /**
     * 创建软连接，或者拷贝
     */
    public $Result mkLinkOrCopyFile(File f, File linkFile){
        $Result rs = $.result();

        if(linkFile.exists() && f.lastModified() <= linkFile.lastModified()) { // 如果路径不存在，则将本地地址，做软连接到指定路径
            rs.addMessage($.debugOut("获取软连接成功：" + f.getAbsolutePath()+" =====>>> " + linkFile.getAbsolutePath()));
            return rs;
        }

        // 非压缩文件，则直接创建软连接
        try {
            Files.createSymbolicLink(f.toPath(), linkFile.toPath());
            rs.addDataList(f.getAbsolutePath());
            rs.addMessage($.debugOut("创建软连接成功：" + f.getAbsolutePath()+" =====>>> " + linkFile.getAbsolutePath()));
        } catch (IOException e) {
            try {
                rs.addMessage($.warn("无权限创建软连接，尝试拷贝文件到目标目录：" + f.getAbsolutePath()+" =====>>> " + linkFile.getAbsolutePath()));
                $.file.copyFile(f, linkFile.getAbsolutePath(), true);
                rs.addDataList(f.getAbsolutePath());
                rs.addMessage($.debugOut("拷贝文件成功：" + f.getAbsolutePath()+" =====>>> " + linkFile));
            } catch (Exception e1) {
                rs.addMessage($.debugOut("拷贝文件失败："+e1)).setSuccess(false);
            }
        }

        return rs;
    }

    /**
     * 创建软连接，或者拷贝
     */
    public $Result mkLinkOrCopyFileAndUnZIP(File f, File linkFile){
        $Result rs = $.result();

        if($.isZip(f.getName())){ // 如果是压缩文件，则解压到对应目录
            try {
                String zipPath = $.suffix.replaceSuffix(linkFile.getAbsolutePath());

                while ($.isZip(zipPath)) zipPath = $.suffix.replaceSuffix(zipPath);

                if($.isExists(zipPath)) { // 如果路径不存在，则将本地地址，做软连接到指定路径
                    $.debugOut("获取软连接成功：" + f.getAbsolutePath()+" =====>>> " + zipPath);
                    return rs;
                }

                rs.addMessage($.debugOut("解压文件到目标目录"));
                $.unzip(f.getAbsolutePath(),  zipPath);
                rs.addDataList(f.getAbsolutePath());
                if(rs.isSuccess()){
                    rs.addMessage($.debugOut("解压文件成功：" + f.getAbsolutePath()+" =====>>> " + zipPath));
                } else {
                    rs.addMessage($.warn("解压文件失败：")).setSuccess(false);
                    rs.getError().forEach(e->{
                        e.printStackTrace();
                    });
                }
            } catch (Exception e1) {
                $.warn("解压文件失败："+e1);
            }
            return rs;
        }

        if(linkFile.exists() && f.lastModified() <= linkFile.lastModified()) { // 如果路径不存在，则将本地地址，做软连接到指定路径
            rs.addMessage($.debugOut("获取软连接成功：" + f.getAbsolutePath()+" =====>>> " + linkFile.getAbsolutePath()));
            return rs;
        }

        // 非压缩文件，则直接创建软连接
        try {
            Files.createSymbolicLink(f.toPath(), linkFile.toPath());
            rs.addDataList(f.getAbsolutePath());
            rs.addMessage($.debugOut("创建软连接成功：" + f.getAbsolutePath()+" =====>>> " + linkFile.getAbsolutePath()));
        } catch (IOException e) {
            try {
                rs.addMessage($.warn("无权限创建软连接，尝试拷贝文件到目标目录：" + f.getAbsolutePath()+" =====>>> " + linkFile.getAbsolutePath()));
                $.file.copyFile(f, linkFile.getAbsolutePath(), true);
                rs.addDataList(f.getAbsolutePath());
                rs.addMessage($.debugOut("拷贝文件成功：" + f.getAbsolutePath()+" =====>>> " + linkFile));
            } catch (Exception e1) {
                rs.addMessage($.debugOut("拷贝文件失败："+e1)).setSuccess(false);
            }
        }

        return rs;
    }

}
