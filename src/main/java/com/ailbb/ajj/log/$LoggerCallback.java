package com.ailbb.ajj.log;

public interface $LoggerCallback {
    /*
     * 日志的回调函数
     * @param log
     */
    void log(Object... var1);
    void sout(Object... log);
    void debug(Object... var1);
    void info(Object... var1);
    void warn(Object... var1);
    void error(Object... var1);
    void trace(Object... var1);
}
