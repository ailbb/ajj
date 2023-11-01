package com.ailbb.ajj.log;

import com.ailbb.ajj.$;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static com.ailbb.ajj.$.*;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Logger {
    public static int LOG_LEVEL = 0;
    private static String LOG_PATH = "log/";
    private String File_PATH = null;
    private boolean PRINT_DZ = false; // 打印堆栈信息
    private boolean PRINT_TIME = true; // 打印时间信息
    private $LoggerCallback loggerCallback = null;
    private List<String> history = new ArrayList<>();
    private int maxHistroyLine = 100000; // 默认缓存1000行记录
    private boolean ended = false;
    public static boolean DebugEnabled = false;

    public PrintStream ps;

    public $Logger() {
         this.ps  = System.out;
    }

    public $Logger(PrintStream ps) {
         this.ps  = ps;
    }
    public $Logger(String filePath) {
        this(filePath, null);

        $.info("初始化日志路径：" + File_PATH);
    }

    public $Logger($LoggerCallback loggerCallback) {
        this(null, loggerCallback);
    }

    public $Logger(org.slf4j.Logger slf4jLogger) {
        this.regist(slf4jLogger);
    }

    public $Logger(String filePath, $LoggerCallback loggerCallback) {
        this.File_PATH = $.getPath(filePath);
        this.loggerCallback = loggerCallback;
    }

    public $Logger regist(org.slf4j.Logger slf4jLogger) {
        this.loggerCallback = new $LoggerCallback() {
            @Override
            public void log(Object... var1) {
                for(Object o : var1) slf4jLogger.info($.str(o));
            }

            @Override
            public void sout(Object... var1) {
                for(Object o : var1) slf4jLogger.info($.str(o));
            }

            @Override
            public void trace(Object... var1) {
                for(Object o : var1) slf4jLogger.trace($.str(o));
            }

            @Override
            public void debug(Object... var1) {
                for(Object o : var1) slf4jLogger.debug($.str(o));
            }

            @Override
            public void info(Object... var1) {
                for(Object o : var1) slf4jLogger.info($.str(o));
            }

            @Override
            public void warn(Object... var1) {
                for(Object o : var1) slf4jLogger.warn($.str(o));
            }

            @Override
            public void error(Object... var1) {
                for(Object o : var1) slf4jLogger.error($.str(o));
            }
        };

        return this;
    }

    public $Logger init(){ return init(date.now("ns") + ".log"); }

    public $Logger init(String filePath){ return new $Logger($.getPath() + LOG_PATH + filePath); }

    public $Logger init(String filePath, $LoggerCallback loggerCallback){ return new $Logger($.getPath() + LOG_PATH + filePath, loggerCallback); }

    public $Logger setLevel(int level){
        $Logger.LOG_LEVEL = level;
        return this;
    }

    public String err(Object... o){ return error(o); }

    public String error(Object... o){

        if(null != loggerCallback) {
            loggerCallback.error(o);
        } else {
            for(Object oi : o) {
                sout(String.format("[ERROR]\t%s", "[" + getDzInfo() + "]" + oi));
            }
        }
        return $.lastStr(o);
    }

    public String warn(Object... o){
        if(null != loggerCallback) {
            loggerCallback.warn(o);
        } else {
            for(Object oi : o) {
                sout(String.format("[WARNING]\t%s", oi));
            }
        }
        return $.lastStr(o);
    }

    public String info(Object... o){
        if(null != loggerCallback) {
            loggerCallback.info(o);
        } else {
            for(Object oi : o) {
                sout( String.format("[INFO]\t%s", oi));
            }
        }
        return $.lastStr(o);
    }

    public boolean isDebugEnabled(){
        return DebugEnabled;
    }
    public String debugOut(Object... o) {
        return debug(o);
    }

    public String debug(Object... o){
        if(!isDebugEnabled()) return $.lastStr(o);

        if(null != loggerCallback) {
            loggerCallback.debug(o);
        } else {
            for(Object oi : o) {
                sout( String.format("[DEBUG]\t%s", oi));
            }
        }
        return $.lastStr(o);
    }

    public String log(Object... o){
        if(null != loggerCallback) {
            loggerCallback.log(o);
        } else {
            for(Object oi : o) {
                sout(String.format("[LOG]\t%s", oi));
            }
        }
        return $.lastStr(o);
    }

    public Exception exception(Exception... e){

        for(Exception ei : e) {
            String emg = ei.toString();

            if(null != this.File_PATH) file.writeFile(this.File_PATH, true, emg);

            if(null != loggerCallback) {
                loggerCallback.error(emg);
            } else {
                ei.printStackTrace();
            }
        }

        return isEmptyOrNull(e) ? null : e[e.length-1];
    }

    public String msg(Object... o){ return sout(o); }

    public String firstOut(Object... o) {
        List<Object> lo = new ArrayList<>();

        for (Object _s : o) {
            if(!history.contains(_s)) lo.add(_s);
        }

        return sout($.list.listToArray(lo));
    }

    public String sout(Object... o){
        for(Object oi : o) {
            if(null == oi) oi = "";

            String os = oi.toString();
            String s = os+"\n";

            if(PRINT_TIME) s = now("s") + "\t" + s;

            if(PRINT_DZ) s = "[" + getDzInfo() + "]" + "\t" + s;

            if(null != this.File_PATH) file.writeFile(this.File_PATH, true, s);

            // 如果有日志类接管
            if(null != loggerCallback) {
                loggerCallback.info(os);
            } else {
                this.ps.print(s);
            }

            if(history.size() > maxHistroyLine) history.remove(0);

            history.add(os); // 添加历史信息
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
                tag = e.getClassName() + "." + e.getMethodName() +" | "+ e.getLineNumber();
                break;
            }
        }
        if (tag == null) {
            tag = log.getClassName() + "." + log.getMethodName() +" | "+ log.getLineNumber();

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

    public boolean isEnded() {
        return ended;
    }

    public $Logger end() {
        this.ended = ended;
        return this;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public PrintStream getPs() {
        return ps;
    }

    public void setPs(PrintStream ps) {
        this.ps = ps;
    }
}
