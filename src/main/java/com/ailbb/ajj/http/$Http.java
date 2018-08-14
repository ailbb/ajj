package com.ailbb.ajj.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Http {

    public static final int $PORT = 80;

    public static final String $HTTP = "HTTP";
    public static final String $HTTPS = "HTTPS";
    public static final String $GET = "GET";
    public static final String $POST = "POST";

    public String redirect(HttpServletResponse response, String url) throws ServletException, IOException {
        response.sendRedirect(url);
        return url;
    }

    public String reforward(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        request.getRequestDispatcher(url).forward(request,response);
        return url;
    }

    public Object getRequestBody(HttpServletRequest request) {
        StringBuffer result = new StringBuffer();
        String str;
        BufferedReader br = null;
        try {
            br = request.getReader();

            while((str = br.readLine()) != null){
                result.append(str);
            }
        } catch (Exception e) {
            exception(e);
        }

        return result.toString();
    }

    /**
     * 获取cookie
     * @param request
     * @return
     */
    public Cookie[] getCookie(HttpServletRequest request){
        try {
            return request.getCookies();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定cookie
     * @param request
     * @param key
     * @return
     */
    public String getCookie(HttpServletRequest request, String key){
        for(Cookie c : getCookie(request)) {
            if(c.getName().equals(key)){
                return c.getValue();
            }
        }

        return null;
    }

    /**
     * 封装发送Result对象jsonp消息
     * @param response
     * @param object
     */
    public boolean sendJSONP(HttpServletResponse response, String callback, Object object) {
        return send(response, String.format("%s(%s)", callback, object));
    }

    /**
     * 发送json消息
     * @param response
     * @param object
     */
    public boolean send(HttpServletResponse response, Object object) {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(object);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(null != out) out.close();
        }

        return true;
    }

    public String ajax(final Ajax ajax) {
        String data = null;

        if(null == ajax.getUrl()) {
            error(String.format("request $url is null! [%s]", ajax));
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
            Ajax.Callback callback = ajax.getCallback();

            try {
                info(String.format("Send %s request：%s", ajax.getType(), ajax.getUrl()));

                data = ajax.getType().equals($GET) ? sendGet(ajax) : sendPost(ajax);

                if(null != callback) callback.complete(data);
                if(null != callback) callback.success(data);

            } catch (Exception e) {
                error(e);

                if(null != callback) callback.error(e.toString());
            }

            return data;
        }
    }

    public String sendGet(Ajax ajax) throws Exception {
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
//               sout(key + "--->" + map.get(key));
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
                warn(ex);
            }
        }

        return result;
    }

    public String sendPost(Ajax ajax) throws Exception {
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
                warn(ex);
            }
        }

        return result;
    }

    public String getIp(String... name){
        InetAddress inetAddress = getInetAddress(last(name));

        return null == inetAddress ? "localhost" : inetAddress.getHostAddress();
    }

    public InetAddress getInetAddress(String... name) {
        try {
            if(isEmptyOrNull(name)) {
                return InetAddress.getLocalHost();
            } else {
                return InetAddress.getByName(last(name));
            }
        } catch (Exception e) {
            error(e);
        }

        return null;
    }

    public String get(String url){
        return get(new Ajax(url));
    }

    public String post(String url){
        return post(new Ajax(url));
    }

    public JSONObject getJSON(String url){
        return getJSON(new Ajax(url));
    }

    public JSONObject getJSONObject(String url){
        return getJSONObject(new Ajax(url));
    }

    public JSONArray getJSONArray(String url){
        return getJSONArray(new Ajax(url));
    }

    public String ajax(String url){
        return ajax(new Ajax(url));
    }

    public String get(Ajax ajax){
        return ajax(ajax.setType($GET));
    }

    public String post(Ajax ajax){
        return ajax(ajax.setType($POST));
    }

    public JSONObject getJSON(Ajax ajax){
        return getJSONObject(ajax.setType($GET));
    }

    public JSONObject getJSONObject(Ajax ajax) {
        try {
            return JSONObject.fromObject(ajax(ajax.setType($GET)));
        } catch (Exception e) {
            error(e);
            return null;
        }
    }

    public JSONArray getJSONArray(Ajax ajax) {
        try {
            return JSONArray.fromObject(ajax(ajax.setType($GET)));
        } catch (Exception e) {
            error(e);
            return null;
        }
    }

}
