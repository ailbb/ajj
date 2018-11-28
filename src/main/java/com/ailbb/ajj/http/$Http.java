package com.ailbb.ajj.http;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
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
                result.append(str);
            }
        } catch (Exception e) {
            $.warn(e);
        }

        return result.toString();
    }

    /**
     * 获取cookie
     * @param request request对象
     * @return cookie集合
     */
    public Cookie[] getCookie(HttpServletRequest request){
        return request.getCookies();
    }

    /**
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

    /**
     * 封装发送Result对象jsonp消息
     * @param response response对象体
     * @param callback 回调方法
     * @param data 需要发送的数据对象
     * @return $Result
     */
    public $Result sendJSONP(HttpServletResponse response, String callback, Object data)  {
        return send(response, String.format("%s(%s)", callback, data));
    }

    /**
     * 发送json消息
     * @param response response对象体
     * @param data 需要发送的数据对象
     */
    public $Result send(HttpServletResponse response, Object data)  {
        $Result rs = $.result();

        response.setHeader("Content-type", String.format("text/%s;charset=UTF-8", $.isBaseType($.json.tryToJsonObject(data)) ? "html" : "json"));
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

                rs.addMessage(info(String.format("Send %s Data：%s", ajax.getType(), $.simple(ajax.getData()))));

                rs = ajax.getType().equals($GET) ? sendGet(ajax) : sendPost(ajax);

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

    public $Result sendGet(Ajax ajax)  {
        return sendRequest(ajax.setType($GET));
    }

    public $Result sendPost(Ajax ajax)  {
        return sendRequest(ajax.setType($POST));
    }

    /**
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public $Result sendRequest(Ajax ajax)  {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        $Result rs = $.result();

        try {
            String requestUrl = ajax.getUrl();

            // 在链接内添加内容
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
            conn.setInstanceFollowRedirects(true);

            conn.setConnectTimeout(ajax.getTimeout());
            conn.setReadTimeout(ajax.getTimeout());

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", ajax.getType().equalsIgnoreCase($POST) ? "application/json; charset=UTF-8" : "application/x-www-form-urlencoded; charset=UTF-8");

            if(!$.isEmptyOrNull(localCookie)) {
                rs.addMessage($.info("set-cookie：" + localCookie));
                conn.setRequestProperty("Cookie", localCookie); // 设置发送的cookie
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
                if (null != ajax.getData() && !ajax.getData().isNullObject()) {
                    out.write(ajax.getData().toString());
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
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
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

    public String getIp(String... name) throws UnknownHostException {
        InetAddress inetAddress = getInetAddress(last(name));

        return null == inetAddress ? "localhost" : inetAddress.getHostAddress();
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

    public $Result post(String url)  {
        return post(new Ajax(url));
    }

    public $Result get(Ajax ajax)  {
        return ajax(ajax.setType($GET));
    }

    public $Result post(Ajax ajax)  {
        return ajax(ajax.setType($POST));
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

}
