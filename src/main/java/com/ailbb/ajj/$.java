package com.ailbb.ajj;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ailbb.ajj.Ajax.Callback;

/**
 * Created by Wz on 5/9/2018.
 */
public class $ {
    protected static final String $HTTP = "HTTP";
    protected static final String $GET = "GET";
    protected static final String $POST = "POST";
    protected static final int $PORT = 80;
    protected static final long $TIMEOUT = 100000;
    protected static final String $PROXY_PATH = "ajj.json";
    protected static Map<String, Proxy> $PROXY = new HashMap<String, Proxy>();

    static {
        initProxy($PROXY_PATH);
    }

    private static boolean initProxy(final String $PATH) {
        try {
            JSONArray ja = JSONArray.fromObject(readFile($PATH));

            $PROXY.clear();

            for(Object o : ja) {
                Proxy proxy = (Proxy)JSONObject.toBean(JSONObject.fromObject(o), Proxy.class);
                $PROXY.put(proxy.getServerName(), proxy);
            }
        } catch (Exception e) {
            $.warn(e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(initProxy($PATH)) {
                    try {
                        Thread.sleep($TIMEOUT);
                    } catch (InterruptedException e) {
                        $.warn(e);
                        break;
                    }
                }
            }
        });

        return true;
    }

     //* ajax area

    public static String get(String url){
        return get(new Ajax(url));
    }

    public static String post(String url){
        return post(new Ajax(url));
    }

    public static JSONObject getJSON(String url){
        return getJSON(new Ajax(url));
    }

    public static JSONObject getJSONObject(String url){
        return getJSONObject(new Ajax(url));
    }

    public static JSONArray getJSONArray(String url){
        return getJSONArray(new Ajax(url));
    }

    public static String ajax(String url){
        return ajax(new Ajax(url));
    }

    public static String get(Ajax ajax){
        return ajax(ajax.setType($GET));
    }

    public static String post(Ajax ajax){
        return ajax(ajax.setType($POST));
    }

    public static JSONObject getJSON(Ajax ajax){
        return getJSONObject(ajax.setType($GET));
    }

    public static JSONObject getJSONObject(Ajax ajax) {
        try {
            return JSONObject.fromObject(ajax(ajax.setType($GET)));
        } catch (Exception e) {
            $.error(e);
            return null;
        }
    }

    public static JSONArray getJSONArray(Ajax ajax) {
        try {
            return JSONArray.fromObject(ajax(ajax.setType($GET)));
        } catch (Exception e) {
            $.error(e);
            return null;
        }
    }

    public static String getIp(String... name){
        InetAddress inetAddress = getInetAddress($.last(name));

        return null == inetAddress ? "localhost" : inetAddress.getHostAddress();
    }

    public static InetAddress getInetAddress(String... name) {
        try {
            if(null == name) {
                return InetAddress.getLocalHost();
            } else {
                return InetAddress.getByName($.last(name));
            }
        } catch (Exception e) {
            $.error(e);
        }

        return null;
    }

    public static String ajax(final Ajax ajax) {
        String data = null;

        if(null == ajax.getUrl()) {
            $.error(String.format("request url is null! [%s]", ajax));
            return data;
        }

        if(ajax.isAsync()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ajax(ajax.setAsync(false));
                }
            }).start();

            return data;
        } else {
            Callback callback = ajax.getCallback();

            try {
                $.info(String.format("Send %s request：%s", ajax.getType(), ajax.getUrl()));

                data = ajax.getType().equals($GET) ? sendGet(ajax) : sendPost(ajax);

                if(null != callback) callback.complete(data);
                if(null != callback) callback.success(data);

            } catch (Exception e) {
                $.error(e);

                if(null != callback) callback.error(e.toString());
            }

            return data;
        }
    }

    private static String sendGet(Ajax ajax) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            String requestUrl = ajax.getUrl();

            if(ajax.getData() != null) {
                requestUrl += "?";

                for(Object o : ajax.getData().keySet()) {
                    Object ad = ajax.getData().get(o);
                    if(null != ad && !(ad instanceof JSONNull)) requestUrl += String.format("%s=%s&", o, ad);
                }
                requestUrl = requestUrl.substring(0, requestUrl.length()-1);
            }

            URL realUrl = new URL(requestUrl);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//               $.sout(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {  //使用finally块来关闭输出流、输入流
            try{
                if(in!=null) in.close();
            } catch(IOException ex){
                $.warn(ex);
            }
        }

        return result;
    }

    private static String sendPost(Ajax ajax) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(ajax.getUrl());
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            if(null != ajax.getData() && !ajax.getData().isNullObject()) out.print(ajax.getData().toString());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {  //使用finally块来关闭输出流、输入流
            try{
                if(out!=null) out.close();
                if(in!=null) in.close();
            } catch(IOException ex){
                $.warn(ex);
            }
        }

        return result;
    }

     //* file area

    public static String readFile(String path) {

        String content = "";
        InputStream is;
        String $JAR_PATH = ".jar!";

        try {
            path = getPath(path);

            $.info(String.format("Read file：%s", path));

            if($.test("\\.jar!", path)) { // 如果内容在jarb包内，则用流去读取
                is = $.class.getResourceAsStream(path.substring(path.lastIndexOf($JAR_PATH) + $JAR_PATH.length(), path.length()));
            } else {
                is = new FileInputStream(new File(path));
            }

            if(null == is) throw new FileNotFoundException(path);

            BufferedInputStream bis = new BufferedInputStream(is);
            //自己定义一个缓冲区
            byte[] buffer=new byte[10240];
            int flag=0;
            while((flag=bis.read(buffer))!=-1){
                content+=new String(buffer, 0, flag);
            }
            bis.close();
        } catch (Exception e) {
            $.error(e);
        }

        return content;
    }

    public static void writeFile(String path, Object... object) {
        if(null == path) return;

        path = getPath(path);
        File file = new File(path);
        FileWriter fw = null;
        try {
            $.info(String.format("Write file：%s", path));

            $.mkdir(path.substring(0, path.lastIndexOf("/")));
            fw = new FileWriter(file);
            if(null != object) for(Object o : object) fw.write(o.toString());
            fw.flush();
        } catch (Exception e) {
            $.error(e);
        } finally {
            try {
                if(null != fw) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mkdir(String... path) {
        for(String p : path) {
            File file = new File(getPath(p));
            if(!file.exists()) {
                $.info(String.format("Make directory：%s", p));
                file.mkdirs();
            }
        }
    }

    public static String getPath(String path){
        String p = $.class.getResource("").getPath().replaceFirst("file:", "");

        if($.system().equals("windows")) {
            if($.test("^[A-Za-z]:", path)) return $.rel("/", path);
        } else if($.system().equals("linux")) {
            if(path.startsWith("/")) return $.rel(path);
        } else {
            return path;
        }

        return $.rel(p.substring(0, p.lastIndexOf("com")) + path);
    }

    public static String rel(String... path){
        if($.isEmptyOrNull(path)) return "/";
        String p = "";
        for(String pa : path) p += pa;
        return p.replaceAll("\\\\+|/+", "/");
    }

     //* Thread area

    public static void async(Runnable r){
        new Thread(r).start();
    }

     //* date area

    public static String now(String... ns){
        String n = $.lastDef("s", ns);
        if(n.equals("s")) return format("YYYY-MM-dd HH:mm:ss"); // stand
        if(n.equals("ss")) return format("YYYY-MM-dd HH:mm:ss.S"); // stand millisecond
        if(n.equals("n")) return format("YYYYMMddHHmmss"); // number
        if(n.equals("ns")) return format("YYYYMMddHHmmssS"); // number millisecond

        return format("YYYY-MM-dd HH:mm:ss");
    }

    public static String format(String patten, Date... date){
        return new SimpleDateFormat(patten).format($.isEmptyOrNull(date) ? new Date() : date[date.length-1]);
    }

    public static Date parse(String date, String... patten) {
        try {
            return new SimpleDateFormat($.lastDef("YYYY-MM-dd HH:mm:ss", patten)).parse(date);
        } catch (Exception e) {
            return null;
        }
    }

     //* util area

    public static List<String> regex(String pattern, String... str) {
        List<String> list = new ArrayList<String>();

        for(String s: str) {
            Pattern r = Pattern.compile(pattern);

            // 现在创建 matcher 对象
            Matcher m = r.matcher(s);

            if (m.find( ))
                list.add(m.group());
        }

        return list;
    }

    public static boolean test(String pattern, String... str) {
        return regex(pattern, str).size() != 0;
    }

    public static boolean match(String pattern, String... str) {
        for(String s: str) {
            boolean isMatch = Pattern.matches(pattern, s);
            if(isMatch) return isMatch;
        }

        return false;
    }

    public static String join(Collection list, Object... u){
        if(null == list) return null;

        int i=0;
        StringBuffer sb = new StringBuffer();

        for(Object l : list) {
            if(null != l) sb.append(l);
            if(++i != list.size())  if(!$.isEmptyOrNull(u)) {
                for (Object ui: u) sb.append(ui);
            }
        }

        return sb.toString();
    }

    public static String first(String... strs) {
        return lastDef(null, strs);
    }

    public static String firstDef(String def, String... strs) {
        return $.isEmptyOrNull(strs) ? def : strs[0];
    }

    public static String last(String... strs) {
        return lastDef(null, strs);
    }

    public static String lastDef(String def, String... strs) {
        return $.isEmptyOrNull(strs) ? def : strs[strs.length-1];
    }

    public static boolean isEmptyOrNull(Object... o){
        if(null == o || o.length == 0) return true;
        for(Object oi : o)
            if(null == oi || oi.toString().trim().length() == 0 || "null".equals(oi.toString().trim().toLowerCase())) return true;
        return false;
    }

    public static String system(){
        return System.getProperty("os.name").toLowerCase().startsWith("windows") ? "windows" : "linux";
    }

     //* system out print area

    public static void exception(Exception... e){
        for(Exception ei : e) ei.printStackTrace();
    }

    public static void error(Object... o){
        for(Object oi : o) exception(new $Exception(String.format("[ERROR]\t%s", oi)));
    }

    public static void warn(Object... o){
        for(Object oi : o) sout(String.format("[WARNING]\t%s", oi));
    }

    public static void info(Object... o){
        for(Object oi : o) sout(String.format("[INFO]\t%s", oi));
    }

    public static void log(Object... o){
        for(Object oi : o) sout(String.format("[LOG]\t%s", oi));
    }

    public static void sout(Object... o){
        for(Object oi : o)
            System.out.println(String.format($.now("s") + "\t%s", oi));
    }

    private static class $Exception extends Exception {
        public $Exception() {
            super();
        }
        public $Exception(String msg){ super(msg); }
    }
}
