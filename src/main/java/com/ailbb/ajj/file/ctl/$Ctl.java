package com.ailbb.ajj.file.ctl;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Progress;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * Created by Wz on 8/2/2018.
 */
public class $Ctl {
    List<$Option> options = new ArrayList<>();
    List<$Field> fields = new ArrayList<>();
    int version = 1;
    String name;
    String colSep;
    String rowSep;
    int send_threads = 10;

    String discarded_1 = "";

    public $Ctl parse(File _file)  {
        return parse($.file.readFile(_file).getDataToString());
    }

    /*
     * 格式化ctl内容
     * @param context 格式化的文本
     * @return ctl对象
     */
    public $Ctl parse(String context){
        $Progress p_option = $.progress();
        $Progress p_discarded1 = $.progress();
        $Progress p_rowSep = $.progress();
        $Progress p_colSep = $.progress();
        $Progress p_field = $.progress();
        
        for(String c : context.split("\n")) {
            boolean is = parseOption(c, p_option).pass() && // 解析设置
                    parseDiscarded1(c, p_discarded1).pass() && // 解析丢弃掉的数据
                    parseRowSep(c, p_rowSep).pass() && // 解析行分隔符
                    parseColSep(c, p_colSep).pass() && // 解析列分隔符
                    parseField(c, p_field).pass() // 解析field
            ;
        }

        return this;
    }

    /*
     * 解析参数
     * @param row 当前行
     * @param progress 进度条
     * @return 进度条
     */
    private $Progress parseOption(String row, $Progress progress) {
        if(progress.setRunning(!progress.isEnd()).isEnd()) return progress;

        List<$Option> list = new ArrayList<>();

        try {
            list = (List<$Option>)progress.getResult().getData();
        } catch (Exception e) {
            progress.getResult().setData(list);
        }

        if(-1 != row.indexOf("=")) {
            String[] strs = row.replaceAll(",","").split("=|--");
            list.add(new $Option(
                    $.string.trim(strs[0]),
                    $.string.trim(strs[1]),
                    strs.length>2 ? $.string.trim(strs[2]) : null
            ));
        }

        if($.test("\\)$", $.string.trim(row))) {
            this.setOptions(list);
            progress.setEnd(true);
        }

        return progress;
    }

    /*
     * 解析丢弃的内容
     * @param row 当前行
     * @param progress 进度条
     * @return 进度条
     */
    private $Progress parseDiscarded1(String row, $Progress progress) {
        if(progress.setRunning(!progress.isEnd()).isEnd()) return progress;

        progress.getResult().setData(row);

        this.setDiscarded_1(row);

        return progress.setEnd(true);
    }

    /*
     * 解析行分隔符
     * @param row 当前行
     * @param progress 进度条
     * @return 进度条
     */
    private $Progress parseRowSep(String row, $Progress progress) {
        if(progress.setRunning(!progress.isEnd()).isEnd()) return progress;
        String[] splits = row.split("\\s+");
        String sep = $.regex.pickup("'", ".+", "'", splits[3]);
        String val = "\\x" + $.string.join("\\x", sep, 2); // infile * "str X'03fe0d0a'"，提取''号内的数据，添加分隔符

        progress.getResult().setData(val);

        this.setRowSep(val);

        return progress.setEnd(true);
    }

    /*
     * 解析列分隔符
     * @param row 当前行
     * @param progress 进度条
     * @return 进度条
     */
    private $Progress parseColSep(String row, $Progress progress) {
        if(progress.setRunning(!progress.isEnd()).isEnd()) return progress;

        row = row.toUpperCase();

        String[] splits = row.split("\\s+");
        String table = splits[3];
        String version = $.regex.pickup("_V", "\\d+$", null, table);

        table = table.replaceAll("_V\\d+$", "");
        String sep = $.regex.pickup("'", ".+", "'", row);

        if(!$.isEmptyOrNull(sep)) {
            String val = "\\x" + $.string.join("\\x", sep , 2); // infile * "str X'03fe0d0a'"，提取''号内的数据，添加分隔符

            progress.getResult().setData(val);

            this.setColSep(val);
        }

        if(null != version) this.setVersion(Integer.parseInt(version));
        this.setName(table);

        return progress.setEnd(true);
    }

    /*
     * 解析id的内容
     * @param row 当前行
     * @param progress 进度条
     * @return 进度条
     */
    private $Progress parseField(String row, $Progress progress) {
        if(progress.setRunning(!progress.isEnd()).isEnd()) return progress;


        List<$Field> list = new ArrayList<>();

        try {
            list = (List<$Field>)progress.getResult().getData();
        } catch (Exception e) {
            progress.getResult().setData(list);
        }

        if(!$.test("^\\(|\\)$", $.string.trim(row))) {
            String[] strs = $.string.trim(row.replaceAll(",$","")).split("\\s+|\\|");
            list.add(new $Field(
                    $.string.trim(strs[0]),
                    strs.length>1 ? $.string.trim(strs[1]) : null
            ));
        }

        if($.test("\\)$", $.string.trim(row))) {
            this.setFields(list);
            progress.setEnd(true);
        }

        return progress;
    }

    $Progress setStart($Progress progress) {
        return progress;
    }

    $Progress setEnd($Progress progress) {
        if(!progress.isEnd()) progress.setStart(true);
        return progress;
    }

    public List<$Option> getOptions() {
        return options;
    }

    public $Ctl setOptions(List<$Option> options) {
        this.options = options;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public $Ctl setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }

    public $Ctl setName(String name) {
        this.name = name;
        return this;
    }

    public String getColSep() {
        return colSep;
    }

    public $Ctl setColSep(String colSep) {
        this.colSep = colSep;
        return this;
    }

    public String getRowSep() {
        return rowSep;
    }

    public $Ctl setRowSep(String rowSep) {
        this.rowSep = rowSep;
        return this;
    }

    public int getSend_threads() {
        return send_threads;
    }

    public $Ctl setSend_threads(int send_threads) {
        this.send_threads = send_threads;
        return this;
    }

    public String getDiscarded_1() {
        return discarded_1;
    }

    public $Ctl setDiscarded_1(String discarded_1) {
        this.discarded_1 = discarded_1;
        return this;
    }

    public List<$Field> getFields() {
        return fields;
    }

    public $Ctl setFields(List<$Field> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public String toString() {
        return "$Ctl{" +
                "options=" + options +
                ", fields=" + fields +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", colSep='" + colSep + '\'' +
                ", rowSep='" + rowSep + '\'' +
                ", send_threads=" + send_threads +
                ", discarded_1='" + discarded_1 + '\'' +
                '}';
    }
}
