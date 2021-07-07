package com.ailbb.ajj.log;

import static com.ailbb.ajj.$.*;

import com.ailbb.ajj.$;
import com.ailbb.ajj.exception.$Exception;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Logger {
    public static int LOG_LEVEL = 0;
    private static String LOG_PATH = $.getPath() + "log/";
    private String File_PATH = null;
    private boolean PRINT_DZ = false; // 打印堆栈信息
    private boolean PRINT_TIME = true; // 打印时间信息
    private $LoggerCallback loggerCallback = null;
    private List<String> history = new ArrayList<>();
    private int maxHistroyLine = 1000; // 默认缓存1000行记录

    public $Logger() {}
    public $Logger(String filePath) {
        this(filePath, null);

        $.info("初始化日志路径：" + File_PATH);
    }

    public $Logger($LoggerCallback loggerCallback) {
        this(null, loggerCallback);
    }

    public $Logger(String filePath, $LoggerCallback loggerCallback) {
        this.File_PATH = $.getPath(filePath);
        this.loggerCallback = loggerCallback;
    }

    public $Logger init(){ return init(date.now("ns") + ".log"); }

    public $Logger init(String filePath){ return new $Logger(LOG_PATH + filePath); }

    public $Logger init(String filePath, $LoggerCallback loggerCallback){ return new $Logger(LOG_PATH + filePath, loggerCallback); }

    public $Logger setLevel(int level){
        $Logger.LOG_LEVEL = level;
        return this;
    }

    public String error(Object... o){
        for(Object oi : o) exception(new $Exception(String.format("[ERROR]\t%s", "[" + getDzInfo() + "]" + oi)));
        return $.lastStr(o);
    }

    public String warn(Object... o){
        for(Object oi : o) sout(String.format("[WARNING]\t%s", oi));
        return $.lastStr(o);
    }

    public String info(Object... o){
        for(Object oi : o) sout( String.format("[INFO]\t%s", oi));
        return $.lastStr(o);
    }

    public String log(Object... o){
        for(Object oi : o) sout(String.format("[LOG]\t%s", oi));
        return $.lastStr(o);
    }

    public Exception exception(Exception... e){

        for(Exception ei : e) {
            String emg = ei.toString();

            if(null != this.File_PATH) file.writeFile(this.File_PATH, true, emg);

            if(null != loggerCallback) loggerCallback.callback(emg);

            ei.printStackTrace();
        }

        return isEmptyOrNull(e) ? null : e[e.length-1];
    }

    public String sout(Object... o){
        for(Object oi : o) {
            String s = oi.toString()+"\n";

            if(PRINT_TIME) s = now("s") + "\t" + s;

            if(PRINT_DZ) s = "[" + getDzInfo() + "]" + "\t" + s;

            if(null != this.File_PATH) file.writeFile(this.File_PATH, true, s);

            if(null != loggerCallback) loggerCallback.callback(s);

            if(history.size() > maxHistroyLine) history.remove(0);

            history.add(s); // 添加历史信息

            System.out.print(s);

        }

        return $.lastStr(o);
    }

    public List<String> getHistory() {
        return history;
    }

    /**
     * 获取堆栈信息
     * @return 堆栈信息
     */

    String getDzInfo(){
        String tag = null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement log = stackTrace[1];
        for (int i = 1; i < stackTrace.length; i++) {
            StackTraceElement e = stackTrace[i];
            if (!e.getClassName().equals(log.getClassName())) {
                tag = e.getClassName() + "." + e.getMethodName() + e.getLineNumber();
                break;
            }
        }
        if (tag == null) {
            tag = log.getClassName() + "." + log.getMethodName() + log.getLineNumber();

        }
        return tag;
    }


    public int getMaxHistroyLine() {
        return maxHistroyLine;
    }

    public $Logger setMaxHistroyLine(int maxHistroyLine) {
        this.maxHistroyLine = maxHistroyLine;
        return this;
    }

    public boolean isPRINT_DZ() {
        return PRINT_DZ;
    }

    public $Logger setPRINT_DZ(boolean PRINT_DZ) {
        this.PRINT_DZ = PRINT_DZ;
        return this;
    }

    public boolean isPRINT_TIME() {
        return PRINT_TIME;
    }

    public $Logger setPRINT_TIME(boolean PRINT_TIME) {
        this.PRINT_TIME = PRINT_TIME;
        return this;
    }

    public $LoggerCallback getLoggerCallback() {
        return loggerCallback;
    }

    public $Logger setLoggerCallback($LoggerCallback loggerCallback) {
        this.loggerCallback = loggerCallback;
        return this;
    }
}
