package com.ailbb.ajj.file;

import com.ailbb.ajj.$;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

/*
 * Created by Wz on 9/12/2018.
 */
public class $FileInfo {
    private MultipartFile multipartFile;
    private FileItem fileItem;
    private File file;

    private String fileName;
    private String fieldName;

    private String type;
    private long size;

    private String path;
    private String relativePath;

    private Object content = "";

    private long runTime;

    public $FileInfo() {}

    public $FileInfo(FileItem item) {
        initFileItem(item);
    }

    public $FileInfo(File file) {
        initFile(file);
    }

    public $FileInfo(MultipartFile file) {
        initMultipartFile(file);
    }

    public $FileInfo initFile(File file) {
        return setFile(file)
            .setFileName(file.getName())
            .setPath(file.getPath())
            .setRelativePath($.path.getRelativePath(file.getPath()))
            .setSize(file.length())
            .setType($.file.getFileType(file))
        ;
    }

    public $FileInfo initMultipartFile(MultipartFile file) {
        try {
            if(file instanceof CommonsMultipartFile) {
                return initFileItem(((CommonsMultipartFile) file).getFileItem());
            }
        } catch (Exception e) { $.warn(e); }

        return setMultipartFile(file)
                .setFileName(file.getOriginalFilename())
                .setType($.file.getFileType(file.getOriginalFilename()))
                .setSize(file.getSize())
                ;
    }

    public $FileInfo initFileItem(FileItem item) {
        try {
            this.setFileItem(item)
                    .setFileName(item.getName())
                    .setType(item.getContentType())
                    .setSize(item.getSize());
        } catch (Exception e) {}

        try {
            return setFieldName(item.getFieldName())
                    .setContent(item.getString("utf-8"));
        } catch (Exception e) {}

        return this;
    }

    public long getRunTime() {
        return runTime;
    }

    public $FileInfo setRunTime(long runTime) {
        this.runTime = runTime;
        return this;
    }

    public long getSize() {
        return size;
    }

    public $FileInfo setSize(long size) {
        this.size = size;
        return this;
    }

    public Object getContent() {
        return content;
    }

    public $FileInfo setContent(Object content) {
        this.content = content;
        return this;
    }

    public String getType() {
        return type;
    }

    public $FileInfo setType(String type) {
        this.type = type;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public $FileInfo setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public $FileInfo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public $FileInfo setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
        return this;
    }

    public File getFile() {
        return file;
    }

    public $FileInfo setFile(File file) {
        this.file = file;
        return this;
    }

    public String getPath() {
        return path;
    }

    public $FileInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public $FileInfo setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public $FileInfo setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        return this;
    }
}
