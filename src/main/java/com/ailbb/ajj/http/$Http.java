package com.ailbb.ajj.http;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.*;

import static com.ailbb.ajj.$.*;
import static com.ailbb.ajj.$.path;

/*
 * Created by Wz on 6/20/2018.
 */
public class $Http {
    public static Map<String, String> cookies = new HashMap<>();

    public static final int $PORT = 80;

    public static final String $HTTP = "HTTP";
    public static final String $HTTPS = "HTTPS";
    public static final String $GET = "GET";
    public static final String $POST = "POST";

    public $Result redirect(HttpServletResponse response, String url)  {
        $Result rs = $.result();
        try {
            response.sendRedirect(url);
            rs.setData(url);
        } catch (IOException e) {
            rs.addError($.exception(e));
        }
        return rs;
    }

    public $Result reforward(HttpServletRequest request, HttpServletResponse response, String url)  {
        $Result rs = $.result();
        try {
            request.getRequestDispatcher(url).forward(request,response);
            rs.setData(url);
        } catch (ServletException e) {
            rs.addError($.exception(e));
        } catch (IOException e) {
            rs.addError($.exception(e));
        }
        return rs;
    }

    public String getRequestBody(HttpServletRequest request) {
        StringBuffer result = new StringBuffer();
        try {
            String str;
            BufferedReader br = null;
            br = request.getReader();

            while((str = br.readLine()) != null){
                result.append(str + "\n");
            }
        } catch (Exception e) {
            $.warn(e);
        }

        return $.isEmptyOrNull(result.toString()) ? getParameterJSONStr(request) : result.toString();
    }

    /*
     * 获取cookie
     * @param request request对象
     * @return cookie集合
     */
    public Cookie[] getCookie(HttpServletRequest request){
        return request.getCookies();
    }

    /*
     * 获取指定cookie
     * @param request request对象
     * @param key cookie的key
     * @return cookie的value
     */
    public String getCookie(HttpServletRequest request, String key){
        for(Cookie c : getCookie(request)) {
            if(c.getName().equals(key)){
                return c.getValue();
            }
        }

        return null;
    }

    /*
     * 封装发送Result对象jsonp消息
     * @param response response对象体
     * @param callback 回调方法
     * @param data 需要发送的数据对象
     * @return $Result
     */
    public $Result sendJSONP(HttpServletResponse response, String callback, Object data)  {
        return send(response, String.format("%s(%s)", callback, data));
    }

    /*
     * 发送json消息
     * @param response response对象体
     * @param data 需要发送的数据对象
     */
    public $Result send(HttpServletResponse response, Object data)  {
        $Result rs = $.result();

        response.setHeader("Content-type", String.format("text/%s;ENCODING=UTF-8", $.isBaseType($.json.tryToJsonObject(data)) ? "html" : "json"));
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(data);
            out.flush();
            rs.setData(data);
        } catch (IOException e) {
            rs.addError($.exception(e));
        } finally {
            try {
                if(null != out) out.close();
            } catch (Exception e) {
                rs.addError($.exception(e));
            }
        }

        return rs;
    }

    public $Result ajax(final Ajax ajax)  {
        return ajax(ajax, false);
    }

    public $Result ajax(final Ajax ajax, boolean isClearSession)  {
        $Result rs = $.result();

        if(null == ajax.getUrl()) {
            return rs.setSuccess(false).addMessage(error(String.format("request $url is null! [%s]", ajax)));
        }

        if(ajax.isAsync()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ajax(ajax.setAsync(false));
                }
            }).start();

            return rs;
        } else {
            Ajax.Callback callback = ajax.getCallback();

            try {
                rs.addMessage(info("-----------------------"));

                rs.addMessage(info(String.format("Send %s request：%s", ajax.getType(), ajax.getUrl())));

                rs.addMessage(info(String.format("Send %s Data：%s", ajax.getType(), $.simple(ajax.getParams()))));

                rs = ajax.getType().equals($GET) ? sendGet(ajax, isClearSession) : sendPost(ajax, isClearSession);

                rs.addMessage(info(String.format("Get %s Data：%s", ajax.getType(), $.simple(rs.getData()))));

                if(null != callback) callback.complete(rs);
                if(null != callback) callback.success(rs);
            } catch (Exception e) {
                if(null != callback) callback.error(rs.addError($.exception(e)));

                throw e;
            }

            return rs;
        }
    }

    public boolean isJSON(Object str)  {
        try {
            JSONObject.fromObject(str);
            return true;
        } catch (Exception e) {
            try {
                JSONArray.fromObject(str);
                return true;
            } catch (Exception e1) {}
        }

        return false;
    }


    public void sendFile(HttpServletResponse response, String path) {
        sendFile(response, path, null);
    }


    public void sendFile(HttpServletResponse response, String path, String fileName) {
        sendFile(response, $.getFile(path), fileName);
    }

    public void sendFile(HttpServletResponse response, File file) {
        sendFile(response, file, null);
    }

    public void sendFile(HttpServletResponse response, File file, String fileName) {
        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            //获取文件存放的路径
            if($.isEmptyOrNull(fileName)) fileName = file.getName();
            //获取到文字 数据库里对应的附件名字加上老的文件名字：filename 截取到后面的文件类型 例：txt  组成一个新的文件名字：newFileName
            if(!file.exists()) {
                //如果文件不存在就跳出
                return;
            }
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),"ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
                ips.close();
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }

        return ;
    }

    public $Result sendGet(Ajax ajax)  {
        return sendRequest(ajax.setType($GET));
    }

    public $Result sendPost(Ajax ajax)  {
        return sendRequest(ajax.setType($POST));
    }

    public $Result sendGet(Ajax ajax, boolean isClearSession)  {
        return sendRequest(ajax.setType($GET), isClearSession);
    }

    public $Result sendPost(Ajax ajax, boolean isClearSession)  {
        return sendRequest(ajax.setType($POST), isClearSession);
    }

    public String toHttpParams(JSONObject jo) {
        List<String> list = new ArrayList<>();

        for(Object o : jo.keySet()) {
            Object v = jo.get(o);
            if(null != v && !(v instanceof JSONNull)) {
                try {
                    list.add(String.format("%s=%s", o, URLEncoder.encode($.str(v), "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    list.add(String.format("%s=%s", o, $.str(v)));
                }
            }
        }

        return $.join(list, "&");
    }

    /*
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public $Result sendRequest(Ajax ajax)  {
        return sendRequest(ajax, false);
    }

    /*
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public $Result sendRequest(Ajax ajax, boolean isClearSession)  {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        $Result rs = $.result();

        try {
            String requestUrl = ajax.getUrl();

            // 在链接内添加内容
            if(ajax.getParams() != null) {
                requestUrl += "?" + toHttpParams(ajax.getParams());
            }

            URL realUrl = new URL(requestUrl);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            String baseUrl = $.url.base(conn.getURL());
            String localCookie = cookies.get(baseUrl); // 获取本地留存的 cookie

            // 设置通用的请求属性
            conn.setRequestMethod(ajax.getType().equalsIgnoreCase($GET) ? $GET : $POST);

            conn.setDoOutput(true); // 允许连接提交信息
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setInstanceFollowRedirects(isClearSession ? true : false); // 防止因为跳转引起的模拟登录异常 !! 关键 !!

            conn.setConnectTimeout(ajax.getTimeout());
            conn.setReadTimeout(ajax.getTimeout());

            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", ajax.getType().equalsIgnoreCase($POST) && !isJSON(ajax.getData()) ? "application/x-www-form-urlencoded; ENCODING=UTF-8" : "application/json; ENCODING=UTF-8" );

            if(!isClearSession && !$.isEmptyOrNull(localCookie)) {
                rs.addMessage($.info("set-cookie：" + localCookie));
                conn.setRequestProperty("Cookie", localCookie); // 设置发送的cookie
            } else {
                rs.addMessage($.info("clear cookie"));
                conn.setRequestProperty("Cookie", ""); // 设置发送的cookie，置为null会某些情况引发400错误
            }

            if(!isEmptyOrNull(ajax.getProperty()))
                for(String key : ajax.getProperty().keySet() )
                    conn.setRequestProperty(key, ajax.getProperty().get(key));


            // 建立实际的连接
            conn.connect();

            if(ajax.getType().equalsIgnoreCase($POST) && ajax.getData() != null) {
                // 获取URLConnection对象对应的输出流
                out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                // 发送请求参数
                if (!$.isEmptyOrNull(ajax.getData())) {
                    out.write($.str(ajax.getData()));
                }
                // flush输出流的缓冲
                out.flush();
            }

            // 设置返回后的通用请求
            localCookie = conn.getHeaderField("Set-Cookie");// 取到返回的Cookie
            if (localCookie != null) {
                $.info("get-cookie：" + localCookie);
                cookies.put(baseUrl, localCookie);
            }

            rs.addMessage($.info("http-status：" + conn.getResponseCode()));

            String line;
            // 定义BufferedReader输入流来读取URL的响应
            if(null != conn.getErrorStream()) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
                while ((line = in.readLine()) != null) {
                    rs.addMessage(line);
                }
            }

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            conn.disconnect(); // 断开连接
        } catch (UnsupportedEncodingException e) {
            rs.addError(exception(e));
        } catch (ProtocolException e) {
            rs.addError(exception(e));
        } catch (MalformedURLException e) {
            rs.addError(exception(e));
        } catch (IOException e) {
            rs.addError(exception(e));
        } finally {  //使用finally块来关闭输出流、输入流
            try{
                if(out!=null) out.close();
                if(in!=null) in.close();
            } catch (IOException ex){
                rs.addError(exception(ex));
            }
        }

        return rs.setData(result.toString());
    }

    public String getIp(String... name) {
        try {
            InetAddress inetAddress = getInetAddress(last(name));

            return null == inetAddress ? "localhost" : inetAddress.getHostAddress();
        } catch (Exception e) {
            $.warn(e);
            return "";
        }
    }

    public InetAddress getInetAddress(String... name) throws UnknownHostException {
        if(isEmptyOrNull(name)) {
            return InetAddress.getLocalHost();
        } else {
            return InetAddress.getByName(last(name));
        }
    }

    public $Result get(String url)  {
        return get(new Ajax(url));
    }

    public $Result get(String url, Map<String,Object> params)  {
        return get(new Ajax(url).setParams(JSONObject.fromObject(params)));
    }

    public $Result get(String url, Map<String,Object> params, boolean isClearSession)  {
        return get(new Ajax(url).setParams(JSONObject.fromObject(params)), isClearSession);
    }

    public $Result get(String url, boolean isClearSession)  {
        return get(new Ajax(url), isClearSession);
    }

    public $Result post(String url)  {
        return post(new Ajax(url));
    }

    public $Result post(String url, boolean isClearSession)  {
        return post(new Ajax(url), isClearSession);
    }

    public $Result get(Ajax ajax)  {
        return ajax(ajax.setType($GET));
    }

    public $Result get(Ajax ajax, boolean isClearSession)  {
        return ajax(ajax.setType($GET), isClearSession);
    }

    public $Result post(Ajax ajax)  {
        return ajax(ajax.setType($POST));
    }

    public $Result post(Ajax ajax, boolean isClearSession)  {
        return ajax(ajax.setType($POST), isClearSession);
    }

    public $Http login(Ajax ajax) {
        post(ajax);
        return this;
    }

    public $Http login(String url, JSONObject jo) {
        login(new Ajax(url).setData(toHttpParams(jo)));
        return this;
    }

    public $Result ajax(String url)  {
        return ajax(new Ajax(url));
    }

    public JSONObject getJSON(String url)  {
        return getJSON(new Ajax(url));
    }

    public JSONObject getJSONObject(String url)  {
        return getJSONObject(new Ajax(url));
    }

    public JSONArray getJSONArray(String url)  {
        return getJSONArray(new Ajax(url));
    }

    public JSONObject getJSON(Ajax ajax)  {
        return getJSONObject(ajax.setType($GET));
    }

    public JSONObject getJSONObject(Ajax ajax)  {
        return JSONObject.fromObject(ajax(ajax.setType($GET)).getData());
    }

    public JSONArray getJSONArray(Ajax ajax)  {
        return JSONArray.fromObject(ajax(ajax.setType($GET)).getData());
    }

    public String getParameterJSONStr(HttpServletRequest request){
        return getParameterJSONMap(request).toString();
    }

    public JSONObject getParameterJSONMap(HttpServletRequest request){
        Map<String,Object> maps = new HashMap<>();
        return getParameterJSONMap(request.getParameterMap());
    }
    public String getParameterJSONStr(Map<String, String[]>... map){
        return getParameterJSONMap(map).toString();
    }

    public JSONObject getParameterJSONMap(Map<String, String[]>... map){
        Map<String,Object> maps = new HashMap<>();
        for(Map<String, String[]> m : map) {
            for(String key : m.keySet()) {
                maps.put(key, isEmptyOrNull(m.get(key)) ? "null" : join(Arrays.asList(m.get(key))));
            }
        }
        return JSONObject.fromObject(maps);
    }
}
