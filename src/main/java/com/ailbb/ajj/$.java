package com.ailbb.ajj;

import com.ailbb.ajj.date.$Date;
import com.ailbb.ajj.entity.$Progress;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.file.$File;
import com.ailbb.ajj.file.$Path;
import com.ailbb.ajj.http.*;
import com.ailbb.ajj.lang.$Json;
import com.ailbb.ajj.lang.$List;
import com.ailbb.ajj.lang.$Object;
import com.ailbb.ajj.lang.$String;
import com.ailbb.ajj.log.$Logger;
import com.ailbb.ajj.mail.$Mail;
import com.ailbb.ajj.regex.$Regex;
import com.ailbb.ajj.sys.$System;
import com.ailbb.ajj.thread.$Thread;
import com.ailbb.ajj.unit.$Charset;
import com.ailbb.ajj.unit.$Unit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by Wz on 5/9/2018.
 */
public class $ {
    public static String $ = "ajj";
    public static String $NAME = $;
    public static String $ROOT = "/" + $;
    
    public static final long $TIMEOUT = 100000;
    public static final String $PROXY_PATH = "ajj.json";

    public static Map<String, Proxy> $PROXY = new HashMap<String, Proxy>();

    // http
    public static $Http http = new $Http();
    public static $Url url = new $Url();
    public static $Velocity velocity = new $Velocity();

    // file
    public static $File file = new $File();
    public static $Path path = new $Path();

    // date
    public static $Date date = new $Date();

    // thread
    public static $Thread thread = new $Thread();

    // regex
    public static $Regex regex = new $Regex();

    // unit
    public static $Unit unit = new $Unit();
    public static $Charset charset = new $Charset();

    // lang
    public static $Json json = new $Json();
    public static $String string = new $String();
    public static $Object object = new $Object();
    public static $List list = new $List();

    // system
    public static $System system = new $System();

    // log
    public static $Logger logger = new $Logger();

    // email
    public static $Mail mail = new $Mail();

    // 结果对象
    public static $Result result() { return new $Result(); }

    // 进度条对象
    public static $Progress progress() { return new $Progress(); }

    static {
        Proxy.init();
    }

    public static $ ajj = $();

    public static $ $(){ return new $(); }

     //* Http area

    public static String get(String url){
        return http.get(url);
    }

    public static String post(String url){
        return http.post(url);
    }

    public static JSONObject getJSON(String url){
        return http.getJSON(url);
    }

    public static JSONObject getJSONObject(String url){
        return http.getJSONObject(url);
    }

    public static JSONArray getJSONArray(String url){
        return http.getJSONArray(url);
    }

    public static String ajax(String url){
        return http.ajax(url);
    }

    public static String get(Ajax ajax){
        return http.get(ajax);
    }

    public static String post(Ajax ajax){
        return http.post(ajax);
    }

    public static JSONObject getJSON(Ajax ajax){
        return http.getJSON(ajax);
    }

    public static JSONObject getJSONObject(Ajax ajax) {
        return http.getJSONObject(ajax);
    }

    public static JSONArray getJSONArray(Ajax ajax) {
        return http.getJSONArray(ajax);
    }

    public static String getIp(String... name){
        return http.getIp(name);
    }

    public static InetAddress getInetAddress(String... name) {
        return http.getInetAddress(name);
    }

    public static String ajax(Ajax ajax) {
        return http.ajax(ajax);
    }

    private static String sendGet(Ajax ajax) throws Exception {
        return http.sendGet(ajax);
    }

    private static String sendPost(Ajax ajax) throws Exception {
        return http.sendPost(ajax);
    }

    public static String redirect(HttpServletResponse response, String url) throws ServletException, IOException {
        return http.redirect(response, url);
    }

    public static String reforward(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
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

    public static boolean send(HttpServletResponse response, Object object) {
        return http.send(response, object);
    }

    public static boolean sendJSONP(HttpServletResponse response, String callback, Object object) {
        return http.sendJSONP(response, callback, object);
    }

    public static boolean sendVelocity(HttpServletRequest request, HttpServletResponse response, String relPath, JSONObject object) {
        return velocity.sendVelocity(request, response, relPath, object);
    }

    public static String url(String u) {
        return url.url(u);
    }

    public static String parameterStr(Map<String, String[]>... map){
        return url.parameterStr(map);
    }

    public static String rel(String... path){
        return url.rel(path);
    }

     //* file area
    public static String read(InputStream is) {
       return file.read(is);
    }

    public static String zip(String path, String... paths){
        return file.zip(path, paths);
    }

    public static String zip(String path, List<String> paths){
        return file.zip(path, paths);
    }

    public static String zip(String path, boolean isDelete, String... paths) {
        return file.zip(path, isDelete, paths);
    }

    public static String zip(String path, boolean isDelete, List<String> paths) {
        return file.zip(path, isDelete, paths);
    }

    public static String readFile(String path) {
        return file.readFile(path);
    }

    public static String writeFile(String path, Object... object) {
        return file.writeFile(path, object);
    }

    public static String writeFile(String path, boolean isAppend, Object... object) {
        return file.writeFile(path, isAppend, object);
    }

    public static void copyFile(String sourcePath, String destPath) {
        file.copyFile(sourcePath, destPath);
    }

    public static void copyFile(String sourcePath, String destPath, boolean isReplace) {
        file.copyFile(sourcePath, destPath, isReplace);
    }

    public static boolean isFile(String path){
        return file.isFile(path);
    }

    public static boolean isExists(String path){
        return file.isExists(path);
    }

    public static File getFile(String path){
        return file.getFile(path);
    }

    public static void mkdir(String... path) {
        file.mkdir(path);
    }

    public static String getPath(String p){
        return path.getPath(p);
    }

    public static String getRootPath(){
        return path.getRootPath();
    }

    public String getRelativePath(String _path){
        return path.getRelativePath(_path);
    }

     //* date area

    public static String now(String... ns){
        return date.now(ns);
    }

    public static String format(String patten, Date... d){
        return date.format(patten, d);
    }

    public static Date parse(String d, String... patten) {
        return date.parse(d, patten);
    }

    //* Thread area

    public static void async(Runnable r){
        thread.async(r);
    }

    //* regex

    public static List<String> regex(String pattern, String... str) {
        return regex.regex(pattern, str);
    }

    public static String pickup(String pattern, String str) {
        return regex.pickup(pattern, str);
    }

    /**
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

    public static String join(Collection list, Object... u){
        return string.join(list, u);
    }

    public static String first(String... strs) {
        return string.first(strs);
    }

    public static String firstDef(String def, String... strs) {
        return string.firstDef(def, strs);
    }

    public static String last(String... strs) {
        return string.last(strs);
    }

    public static String lastDef(String def, String... strs) {
        return string.lastDef(def, strs);
    }

    public static boolean isEmptyOrNull(Object... o){
        return object.isEmptyOrNull(o);
    }

    public static List<Integer> indexOfList(String r, String str) {
        return list.indexOfList(r, str);
    }

    public static String parseJsonString(Object object){
        return json.parseJsonString(object);
    }

    public static String parseJsonString(List<Object> object){
        return json.parseJsonString(object);
    }

    public static List<Object> parseJsonArray(Object object){
        return json.parseJsonArray(object);
    }

    public static Map<String, Object> parseJsonObject(Object object){
        return json.parseJsonObject(object);
    }

    //* system

    public static String system(){
        return system.system();
    }

    //* log

    public static void exception(Exception... e){
        logger.exception(e);
    }

    public static void error(Object... o){
        logger.error(o);
    }

    public static void warn(Object... o){
        logger.warn(o);
    }

    public static void info(Object... o){
        logger.info(o);
    }

    public static void log(Object... o){
        logger.log(o);
    }

    public static void sout(Object... o){
        logger.sout(o);
    }

}
