package com.ailbb.ajj;

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
import com.ailbb.ajj.regex.$Regex;
import com.ailbb.ajj.sys.$System;
import com.ailbb.ajj.thread.$Thread;
import com.ailbb.ajj.unit.$Charset;
import com.ailbb.ajj.unit.$Unit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Wz on 5/9/2018.
 */
public class $ {
    //* 存储全局变量
    public static String $NAME = "";
    public static String $ROOT = "/" + $NAME;
    
    public static final int $TIMEOUT = 100000;
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
    public static $Integer integer = new $Integer();
    public static $Object object = new $Object();
    public static $List list = new $List();
    public static $Bean bean = new $Bean();

    // system
    public static $System system = new $System();

    // jdbc
    public static $JDBC jdbc = new $JDBC();

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

     //* Http area

    public static $Result get(String url)  {
        return http.get(url);
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

    public static $Result get(Ajax ajax)  {
        return http.get(ajax);
    }

    public static $Result post(Ajax ajax)  {
        return http.post(ajax);
    }

    public static JSONObject getJSON(Ajax ajax)  {
        return http.getJSON(ajax);
    }

    public static JSONObject getJSONObject(Ajax ajax)  {
        return http.getJSONObject(ajax);
    }

    public static JSONArray getJSONArray(Ajax ajax)  {
        return http.getJSONArray(ajax);
    }

    public static $Result ajax(Ajax ajax)  {
        return http.ajax(ajax);
    }

    private static $Result sendGet(Ajax ajax)  {
        return http.sendGet(ajax);
    }

    private static $Result sendPost(Ajax ajax)  {
        return http.sendPost(ajax);
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

    public static String parameterStr(Map<String, String[]>... map){
        return url.parameterStr(map);
    }

    public static String parameterStr(Object... objects){
        return url.parameterStr(objects);
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

    public static $Result readFile(String path)  {
        return file.readFile(path);
    }

    public static $Result writeFile(String path, Object... object)  {
        return file.writeFile(path, object);
    }

    public static $Result writeFile(String path, boolean isAppend, Object... object)  {
        return file.writeFile(path, isAppend, object);
    }

    public static void copyFile(String sourcePath, String destPath)  {
        file.copyFile(sourcePath, destPath);
    }

    public static void copyFile(String sourcePath, String destPath, boolean isReplace)  {
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

    public static String getPath(){
        return path.getPath("");
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

    public static Date parse(String d, String... patten) throws ParseException {
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

    public static int toInt(Object object){
        return integer.toInt(object);
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

    public static String last(Object... strs) {
        return string.last(strs);
    }

    public static String lastDef(String def, String... strs) {
        return string.lastDef(def, strs);
    }

    public static String lastDef(Object def, Object... strs) {
        return string.lastDef(def, strs);
    }

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

    public static Map<String, Object> toJsonObject(Object object){
        return json.toJsonObject(object);
    }

    public static Object toBean(Object object, Class cLass){
        return json.toJsonObject(object);
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

    public static Exception exception(Exception... e){
        return logger.exception(e);
    }

    public static String error(Object... o){
        return logger.error(o);
    }

    public static String warn(Object... o){
        return logger.warn(o);
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

    public static String simple(Object data) {
        return string.simple(data);
    }
}
