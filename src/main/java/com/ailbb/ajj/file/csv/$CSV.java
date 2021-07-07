package com.ailbb.ajj.file.csv;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Created by Wz on 9/13/2018.
 */
public class $CSV {
    // 内容存储初始化容量
    public static final int $INITIAL_STRING_SIZE = 256;
    // 默认分隔符
    private String separator =  ",";
    // 默认行尾符
    public static final String $DEFAULT_LINE_END = "\r\n";
    // 文件后缀
    public static final String $SUFFIX = "csv";
    // 编码
    private String charSet = "gbk";

    public $Result writeToCSV(String fileName, List data) {
        return writeToCSV(null, fileName, data);
    }

    public $Result writeToCSV(String path, String fileName, List data) {
        List[] list = $.file.parseHeaderAndData(data);
        return writeToCSV(null, fileName, list[0], list[1]);
    }

    public $Result writeToCSV(String path, String fileName, List<Object> headers, List<List<Object>> data) {
        $Result rs = $.result();
        PrintWriter pw = null;

        path = !$.isEmptyOrNull(path) ? $.path.getPath(path) : $.path.getPath($.path.getRootPath(), "export/csv", $.now("nm")) ;

        fileName = !$.isEmptyOrNull(fileName) ? fileName.replaceAll("\\..+", "") : $.now("nss");

        try {
            $.file.mkdir($.path.getPath(path)); // 创建路径

            pw = new PrintWriter($.path.getPath(path, $.concat(fileName, "." , $SUFFIX)), charSet);
            if(!$.isEmptyOrNull(headers)) pw.println($.string.join(headers, separator));

            //创建记录
            for(int i = 0; i < data.size(); i++){
                pw.println($.string.join(data.get(i), separator));
            }
            pw.flush();
        } catch (Exception e) {
            rs.addError($.exception(e));
        } finally {
            $.file.closeStearm(pw);
        }

        return rs;
    }

}
