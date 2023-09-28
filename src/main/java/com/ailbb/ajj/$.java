package com.ailbb.ajj;

import com.ailbb.ajj.cache.$CacheManagerImpl;
import com.ailbb.ajj.encrypt.util.StringEncryptorUtil;
import com.ailbb.ajj.encrypt.EncryptUtil;
import com.ailbb.ajj.encrypt.Encryption;
import com.ailbb.ajj.encrypt.util.AESUtil;
import com.ailbb.ajj.encrypt.util.Sm4Util;
import com.ailbb.ajj.file.$FileRunner;
import com.ailbb.ajj.file.properties.$Properties;
import com.ailbb.ajj.jar.$Jar;
import com.ailbb.ajj.jar.$Java;
import com.ailbb.ajj.jdbc.$JDBC;
import com.ailbb.ajj.date.$Date;
import com.ailbb.ajj.entity.$Progress;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.file.$File;
import com.ailbb.ajj.file.$Path;
import com.ailbb.ajj.http.*;
import com.ailbb.ajj.lang.*;
import com.ailbb.ajj.log.$Logger;
import com.ailbb.ajj.mail.$Mail;
import com.ailbb.ajj.mybatis.$Mybatis;
import com.ailbb.ajj.regex.$Regex;
import com.ailbb.ajj.server.$Server;
import com.ailbb.ajj.server.Host;
import com.ailbb.ajj.sys.$System;
import com.ailbb.ajj.test.$Test;
import com.ailbb.ajj.thread.$Thread;
import com.ailbb.ajj.tomcat.$Tomcat;
import com.ailbb.ajj.unit.$Charset;
import com.ailbb.ajj.unit.$Suffix;
import com.ailbb.ajj.unit.$Unit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.crypto.NoSuchPaddingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.util.*;

/*
 * Created by Wz on 5/9/2018.
 */
public class $ {
    //* 存储全局变量
    public static String $NAME = "";
    public static String $ROOT = "/" + $NAME;
    
    public static final int $TIMEOUT = 100000;
    public static final String $PROXY_PATH = "ajj.json";

    public static Map<String, $Proxy> $PROXY = new HashMap<String, $Proxy>();

    // http
    public static $Http http = new $Http();
    public static $Download downloader = new $Download();
    public static $Server server = new $Server();
    public static $Url url = new $Url();
    public static $Velocity velocity = new $Velocity();

    // file
    public static $File file = new $File();
    public static $Path path = new $Path();

    // date
    public static $Date date = new $Date();

    // 缓存对象
    public static $CacheManagerImpl cache = new $CacheManagerImpl();

    // tomcat处理类
    public static $Tomcat tomcat = new $Tomcat();

    // thread
    public static $Thread thread = new $Thread();

    // jar&java
    public static $Jar jar = new $Jar();
    public static $Java java = new $Java();

    // regex
    public static $Regex regex = new $Regex();

    // regex
    public static $Test test = new $Test();

    // unit
    public static $Unit unit = new $Unit();
    public static $Suffix suffix = new $Suffix();
    public static $Charset charset = new $Charset();

    // lang
    public static $Json json = new $Json();
    public static $String string = new $String();
    public static $Integer integer = new $Integer();
    public static $Longer longer = new $Longer();
    public static $Double doubled = new $Double();
    public static $Object object = new $Object();
    public static $Byte byter = new $Byte();
    public static $List list = new $List();
    public static $Map map = new $Map();
    public static $Bean bean = new $Bean();
    public static $Mybatis mybatis = new $Mybatis();

    // 加密方法
    public static EncryptUtil encrypt = new EncryptUtil();

    // system
    public static $System system = new $System();

    // jdbc
    public static $JDBC jdbc = new $JDBC();

    // log
    public static $Logger logger = new $Logger();

    public static Encryption stringencryptor = new StringEncryptorUtil();
    public static Encryption aes = new AESUtil();
    public static Encryption sm4 = new Sm4Util();

    // 属性工具类
    public static $Properties properties = file.properties;
    // email
    public static $Mail mail = new $Mail();

    // 结果对象
    public static $Result result() { return new $Result(); }

    // 进度条对象
    public static $Progress progress() { return new $Progress(); }

    static {
        try {
            $Proxy.init();
        } catch (Exception e){
            $.warn("系统无法初始化路径，缺少功能模块启动！"+e);
        }
    }


    public static String md5(String str){
        return encrypt.MD5(str);
    }
     //* Http area

    public static $Result get(String url)  {
        return http.get(url);
    }

     //* Http area

    public static $Result get(String url, boolean isClearSession)  {
        return http.get(url, isClearSession);
    }

    public static $Result post(String url)  {
        return http.post(url);
    }

    public static JSONObject getJSON(String url)  {
        return http.getJSON(url);
    }

    public static JSONObject getJSONObject(String url)  {
        return http.getJSONObject(url);
    }

    public static JSONArray getJSONArray(String url)  {
        return http.getJSONArray(url);
    }

    public static $Result ajax(String url)  {
        return http.ajax(url);
    }

    public static $Result get($Ajax ajax)  {
        return http.get(ajax);
    }

    public static $Result post($Ajax ajax)  {
        return http.post(ajax);
    }

    public static JSONObject getJSON($Ajax ajax)  {
        return http.getJSON(ajax);
    }

    public static JSONObject getJSONObject($Ajax ajax)  {
        return http.getJSONObject(ajax);
    }

    public static JSONArray getJSONArray($Ajax ajax)  {
        return http.getJSONArray(ajax);
    }

    public static $Result ajax($Ajax ajax)  {
        return http.ajax(ajax);
    }

    private static $Result sendGet($Ajax ajax)  {
        return http.sendGet(ajax);
    }

    private static $Result sendPost($Ajax ajax)  {
        return http.sendPost(ajax);
    }

    public static Map<String, Host> getHosts() {
        return server.getHosts();
    }

    public static Map<String, Host> getHostsByName() {
        return server.getHostsByName();
    }

    public static Map<String, Host> getHostsIp() {
        return server.getHostsIp();
    }

    public static String getIp(String... name) throws UnknownHostException {
        return http.getIp(name);
    }

    public static InetAddress getInetAddress(String... name) throws UnknownHostException {
        return http.getInetAddress(name);
    }

    public static $Result redirect(HttpServletResponse response, String url)  {
        return http.redirect(response, url);
    }

    public static $Result reforward(HttpServletRequest request, HttpServletResponse response, String url)  {
        return http.reforward(request, response, url);
    }

    public static Object getRequestBody(HttpServletRequest request) {
        return http.getRequestBody(request);
    }

    public static Cookie[] getCookie(HttpServletRequest request){
        return http.getCookie(request);
    }

    public static String getCookie(HttpServletRequest request, String key){
        return http.getCookie(request, key);
    }

    public static $Result send(HttpServletResponse response, Object object)  {
        return http.send(response, object);
    }

    public static $Result sendJSONP(HttpServletResponse response, String callback, Object object)  {
        return http.sendJSONP(response, callback, object);
    }

    public static $Result sendVelocity(HttpServletRequest request, HttpServletResponse response, String relPath, JSONObject object)  {
        return velocity.sendVelocity(request, response, relPath, object);
    }

    public static String url(String u) {
        return url.url(u);
    }

    public static String getParameterStr(Map<String, String[]>... map){
        return url.getParameterStr(map);
    }

    public static String getParameterStr(Object... objects){
        return url.getParameterStr(objects);
    }

    public static String getParameterStr(HttpServletRequest request){
        return url.getParameterStr(request);
    }

    public static String rel(String... path){
        return url.rel(path);
    }

     //* file area
    public static $Result read(InputStream is)  {
       return file.read(is);
    }

    public static $Result zip(String path, String... paths)  {
        return file.zip(path, paths);
    }

    public static $Result zip(String path, List<String> paths)  {
        return file.zip(path, paths);
    }

    public static $Result zip(String path, boolean isDelete, String... paths)  {
        return file.zip(path, isDelete, paths);
    }

    public static $Result zip(String path, boolean isDelete, List<String> paths)  {
        return file.zip(path, isDelete, paths);
    }

    public static $Result unzip(String path)  {
        return file.unzip(path);
    }

    public static $Result unzip(String path, String targetpath)  {
        return file.unzip(path, targetpath);
    }

    public static $Result readFile(String path)  {
        return file.readFile(path);
    }

    public static String readFileToText(String path)  {
        return file.readFileToText(path);
    }

    public static $Result writeFile(String path, String... datas)  {
        return file.writeFile(path, datas);
    }

    public static $Result writeFile(String path, boolean isAppend, String... datas)  {
        return file.writeFile(path, isAppend, datas);
    }

    public static $Result deleteFile(String... paths)  {
        return file.deleteFile(paths);
    }

    public static $Result delete(String... paths)  {
        return file.delete(paths);
    }

    public static $Result deleteFile(File... files)  {
        return file.deleteFile(files);
    }

    public static $Result delete(File... files)  {
        return file.delete(files);
    }

    public static int parseInt(Object o){ return integer.toInt(o); }

    public static void copyFile(String sourcePath, String destPath)  {
        file.copyFile(sourcePath, destPath);
    }

    public static void copyFile(String sourcePath, String destPath, boolean isReplace)  {
        file.copyFile(sourcePath, destPath, isReplace);
    }

    public static void searchPath(String path, $FileRunner runner) {
        file.searchPath(path, runner);
    }

    public static boolean isFile(String path){
        return file.isFile(path);
    }

    public static boolean isExists(String path){
        return file.isExists(path);
    }

    public static boolean isExists(String path, boolean isFormatPath){
        return file.isExists(path, isFormatPath);
    }

    public static File getFile(String path){
        return file.getFile(path);
    }

    public static List<File> getFiles(String... path){
        return file.getFiles(path);
    }

    public static void mkdir(String... path) {
        file.mkdir(path);
    }

    public static void mkdirOrFile(String... path) {
        file.mkdirOrFile(path);
    }

    public static String getPath(){
        return path.getPath("");
    }

    public static String getPath(String p){
        return path.getPath(p);
    }

    public static String getPath(String... ps){
        return path.getPath(ps);
    }

    public static String getRootPath(){
        return path.getRootPath();
    }

    public static String getWebRootPath(){
        return path.getWebRootPath();
    }

    public static String getWebRootPath(Class clazz){
        return path.getWebRootPath(clazz);
    }

    public static boolean isSuffix(String name) {
        return suffix.isSuffix(name);
    }

    public String getRelativePath(String _path){
        return path.getRelativePath(_path);
    }

     //* date area

    public static String now(String... ns){
        return date.now(ns);
    }

    /**
     * 计时器 | 获取当前时间与上一次时间的偏移量
     * @return 偏移量时间
     */
    public static long timeclock(){
        return date.timeclock();
    }

    /**
     * 计时器
     * @param tag 0:启动 >0:指定位置 -1获取当前时间与第一次记录的时间偏移量
     * @return 偏移量时间
     */
    public static long timeclock(int tag){
        return date.timeclock(tag);
    }

    /**
     * 获取时间缓存器数据
     * @return
     */
    public static List<Long> getTimeclockCache(){
        return date.getTimeclockCache();
    }

    public static String format(String patten, Date... d){
        return date.format(patten, d);
    }

    public static String format(Date... d){
        return date.format(d);
    }

    public static Date parse(String d, String... patten) throws ParseException {
        return date.parse(d, patten);
    }

    //* Thread area

    public static void async(Runnable... r){
        thread.async(r);
    }
    //* Thread area

    public static void async(boolean daemon, Runnable... r){
        thread.async(daemon, r);
    }

    //* regex

    public static List<String> regex(String pattern, String... str) {
        return regex.regex(pattern, str);
    }

    public static String pickup(String pattern, String str) {
        return regex.pickup(pattern, str);
    }

    /*
     * 拾取文本
     * @param before 前置文本
     * @param pattern 匹配表达式
     * @param end 后置文本
     * @param text 需要匹配的文本
     * @return 表达式的内容
     */
    public static String pickup(String before, String pattern, String end, String text) {
        return regex.pickup(pattern, text);
    }

    public static boolean test(String pattern, String... str) {
        return regex.test(pattern, str);
    }

    public static boolean match(String pattern, String... str) {
        return regex.match(pattern, str);
    }

    //* unit

    public static String convert(double num) {
        return unit.convert(num);
    }

    public static String convert(double num, String u) {
        return unit.convert(num, u);
    }

    //* lang

    public static String concat(Object... str){
        return string.concat(str);
    }

    public static String str(Object object){
        return string.str(object);
    }

    public static int toInt(Object object){
        return integer.toInt(object);
    }

    public static double toDouble(Object object){
        return doubled.toDouble(object);
    }

    public static String join(Collection list, Object... u){
        return string.join(list, u);
    }

    public static String join(Object[] list, Object... u){
        return string.join(list, u);
    }

    public static String first(String... strs) {
        return string.first(strs);
    }

    public static String firstDef(String def, String... strs) {
        return string.firstDef(def, strs);
    }

    public static <T> T  last(T... strs) {
        return object.last(strs);
    }

    public static String lastStr(Object... strs) {
        return string.last(strs);
    }

    public static <T> T  lastDef(T def, T... strs) {
        return object.lastDef(def, strs);
    }

    public static boolean isBaseType(Object... o) { return  object.isBaseType(o); }

    public static boolean isEmptyOrNull(Object... o){
        return object.isEmptyOrNull(o);
    }

    public static String notNull(Object o, String... message) {
        return object.notNull(o, message);
    }

    public static List<Integer> indexOfList(String r, String str) {
        return list.indexOfList(r, str);
    }


    //* json

    public static String toJsonString(Object object){
        return json.toJsonString(object);
    }

    public static String toJsonString(List<Object> object){
        return json.toJsonString(object);
    }

    public static List<Object> toJsonArray(Object object){
        return json.toJsonArray(object);
    }

    public static JSONObject toJsonObject(Object object){
        return json.toJsonObject(object);
    }

    public static Object toBean(Object object, Class cLass){
        return json.toBean(object, cLass);
    }

    //* system

    public static String system(){
        return system.system();
    }

    //* system

    public static $Result resultIf($Result rs1, $Result rs2){
        return rs1.isSuccess() ? rs1 : rs2;
    }

    //* log

    public static boolean isDebugEnabled(){
        return logger.isDebugEnabled();
    }

    public static String debug(Object... o){
        return logger.debug(o);
    }

    public static Exception exception(Exception... e){
        return logger.exception(e);
    }

    public static String error(Object... o){
        try {
            return logger.error(o);
        } catch (Exception e) {
            System.out.println("无法唤醒日志类："+e);
            System.out.println(o);
        }
        return $.lastStr(o);
    }

    public static String warn(Object... o){
        try {
            return logger.warn(o);
        } catch (Exception e) {
            System.out.println("无法唤醒日志类："+e);
            System.out.println(o);
        }
        return $.lastStr(o);
    }

    public static String info(Object... o){
        return logger.info(o);
    }

    public static String log(Object... o){
        return logger.log(o);
    }

    public static String sout(Object... o){
        return logger.sout(o);
    }

    public static String debugOut(Object... o){
        return logger.debugOut(o);
    }

    public static String simple(Object data) {
        return string.simple(data);
    }

    public static String toCameUnder(String c){
        return toCameUnder(c, true);
    }

    public static String toCameUnder(String c, boolean type){
        return type ? regex.camel2under(c) : regex.under2camel(c);
    }

    public static String camel2under(String c){
        return regex.camel2under(c);
    }

    public static String under2camel(String s){
        return regex.under2camel(s);
    }

    public static String decompilerJar(String sourcePath, String targetPath){
        return $.jar.decompilerJar(sourcePath, targetPath);
    }
    public static String decompilerClass(String sourcePath, String targetPath){
        return $.java.decompilerClass(sourcePath, targetPath);
    }

    public static String random(String s) {
        return random(s, ",");
    }

    public static String random(String o, String split) {
        return random(o.split(split));
    }

    public static String random(String[] lists) {
        return trim(list.random(lists));
    }
    public static <T> T random(Collection<T> lists) {
        return list.random(lists);
    }

    public static String trim(String str) {
        return string.trim(str);
    }

    public static Encryption Sm4(String key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new Sm4Util(key);
    }
    public static Encryption AES(String key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return new AESUtil(key);
    }


    public static boolean download(String url, String targetPath){
        return downloader.download(url, targetPath);
    }

    public static boolean smartDownload(List<String> urls, String targetDir){
        return downloader.download(urls, targetDir);
    }

    public static boolean smartDownload(String text, String targetDir, String tag, String baseURL){
        return downloader.smartDownload(text, targetDir, tag, baseURL);
    }
    public static Encryption StringEncryptor(String key) {
        return new StringEncryptorUtil(key);
    }

}
