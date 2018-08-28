package com.ailbb.ajj.http;

import com.ailbb.ajj.$;
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
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.ailbb.ajj.$.*;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Http {
    public static Map<String, String> cookies = new HashMap<>();

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
                info("-----------------------");

                info(String.format("Send %s request：%s", ajax.getType(), ajax.getUrl()));

                info(String.format("Send %s Data：%s", ajax.getType(), $.simple(ajax.getData())));

                data = ajax.getType().equals($GET) ? sendGet(ajax) : sendPost(ajax);

                info(String.format("Get %s Data：%s", ajax.getType(), $.simple(data)));

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
        return sendRequest(ajax.setType($GET));
    }

    public String sendPost(Ajax ajax) throws Exception {
        return sendRequest(ajax.setType($POST));
    }

    /**
     * 发送请求
     * @param ajax
     * @return
     * @throws Exception
     */
    public String sendRequest(Ajax ajax) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            String requestUrl = ajax.getUrl();

            // 如果是get请求，在链接内添加内容
            if(ajax.getType().equalsIgnoreCase($GET) && ajax.getData() != null) {
                requestUrl += "?";

                for(Object o : ajax.getData().keySet()) {
                    Object ad = ajax.getData().get(o);
                    if(null != ad && !(ad instanceof JSONNull)) requestUrl += String.format("%s=%s&", o, ad);
                }
                requestUrl = requestUrl.substring(0, requestUrl.length()-1);
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
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.1.4322; .NET4.0C; .NET4.0E)");
            conn.setRequestProperty("Accept-Language","zh-CN");
            conn.setRequestProperty("Accept-Encoding","gzip, deflate");

            if(!$.isEmptyOrNull(localCookie)) {
                $.info("set-cookie：" + localCookie);
                conn.setRequestProperty("Cookie", localCookie); // 设置发送的cookie
            }

            // 建立实际的连接
            conn.connect();

            if(ajax.getType().equalsIgnoreCase($POST) && ajax.getData() != null) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                if (null != ajax.getData() && !ajax.getData().isNullObject()) {
                    out.print(ajax.getData().toString());
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

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            conn.disconnect(); // 断开连接
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
