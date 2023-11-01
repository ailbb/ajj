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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.*;
import java.util.*;

import static com.ailbb.ajj.$.*;

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

    /**
     * 获取对应的HTTPMethod
     * @param method 原始HTTPMethod
     * @return 匹配到的HTTPmethod对象
     */
    public HttpMethod getHttpMethod(String method){
        for(HttpMethod hm: HttpMethod.values())
            if(hm.name().equals(method.toUpperCase())) return hm;

        return HttpMethod.GET;
    }


    /**
     * 重定向到目标URL
     * @param response 当前响应值
     * @param url 重定向的地址
     * @return
     */
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

    /**
     * 内部Serverlet转发
     * @param request 当前请求
     * @param response 当前响应值
     * @param serlvetUrl 代理的地址
     * @return
     */
    public $Result reforward(HttpServletRequest request, HttpServletResponse response, String serlvetUrl)  {
        $Result rs = $.result();
        try {
            request.getRequestDispatcher(serlvetUrl).forward(request,response);
            rs.setData(url);
        } catch (ServletException e) {
            rs.addError($.exception(e));
        } catch (IOException e) {
            rs.addError($.exception(e));
        }
        return rs;
    }

    /**
     * 微服务代理转发
     * @param restTemplate 微服务对象
     * @param request 当前请求
     * @param response 当前响应值
     * @param serviceUrl 代理远端地址
     * @throws IOException
     */
    public void proxyUrl(RestTemplate restTemplate, HttpServletRequest request, HttpServletResponse response,
                      String serviceUrl

    ) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        Enumeration er = request.getHeaderNames();//获取请求头的所有name值
        while(er.hasMoreElements()){
            String name	=(String) er.nextElement();
            String value = request.getHeader(name);
            headers.add(name, value);
        }


        HttpEntity<String> entity = new HttpEntity<>(getRequestBody(request), headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(serviceUrl, getHttpMethod(request.getMethod()), entity, byte[].class);
        byte[] fileContent = responseEntity.getBody();

        // 将文件内容写入响应体中
        response.setContentType("multipart/form-data");
        response.setContentLength(fileContent.length);
        response.getOutputStream().write(fileContent);
    }

    /**
     * 代理地址进行转发
     * @param outputStream 输出流
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @param targetUrl 转发到目标URL
     * @throws IOException
     */
    public void proxyUrl(OutputStream outputStream, String proxyHost, int proxyPort, String targetUrl) throws IOException {
        InputStream inputStream = null; // 对端输出，本端接收流
        HttpURLConnection conn = null; // HTTTP连接

        try {
            /* 创建一个目标URL地址对象 */
            URL url = new URL(targetUrl);

            /* 设置代理 */
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            /* 打开连接 */
            conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setRequestMethod($GET);

            inputStream = conn.getInputStream();

            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while((len = inputStream.read(buffer)) != -1 ){
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            $.error("Proxy URL File error, e = {}", e);
            throw e;
        } finally {
            $.close(inputStream);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 代理地址进行转发
     * @param outputStream 输出流
     * @param targetUrl 转发到目标URL
     * @throws IOException
     */
    public void proxyUrl(OutputStream outputStream, String targetUrl) throws IOException {
        sendRequest(outputStream, targetUrl);
    }

    /**
     * 将当前请求，代理到远端地址
     * @param request 当前请求
     * @param response 当前响应
     * @param targetURL 远端地址
     * @throws IOException
     */
    public void proxyUrl(HttpServletRequest request, HttpServletResponse response, String targetURL) throws IOException {
        InputStream inputStream = null; // 对端输出，本端接收流
        ServletOutputStream outputStream = null; // 本端输出流
        HttpURLConnection conn = null; // HTTTP连接

        try {
            /* 创建一个目标URL地址对象 */
            URL url = new URL(targetURL);

            /* 打开连接 */
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(request.getMethod());

            Enumeration er = request.getHeaderNames();//获取请求头的所有name值
            while(er.hasMoreElements()){
                String name	=(String) er.nextElement();
                String value = request.getHeader(name);
                conn.setRequestProperty(name, value);
            }

            inputStream = conn.getInputStream();

            response.setContentType(conn.getContentType());
            // 文件代理时候的文件信息，为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", conn.getHeaderField("Content-Disposition"));

            outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while((len = inputStream.read(buffer)) != -1 ){
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            $.error("Proxy URL File error, e = {}", e);
            throw e;
        } finally {
            $.closeStream(inputStream);
            if (conn != null) {
                conn.disconnect();
            }
        }
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

    public $Result ajax(final $Ajax ajax)  {
        return ajax(ajax, false);
    }

    public $Result ajax(final $Ajax ajax, boolean isClearSession)  {
        $Result rs = $.result();

        if(null == ajax.getUrl()) {
            return rs.setSuccess(false).addMessage(error(String.format("request $url is null! [%s]", ajax)));
        }

        if(ajax.isAsync()) {
            $.async(new Runnable() {
                @Override
                public void run() {
                    ajax(ajax.setAsync(false));
                }
            });

            return rs;
        } else {
            $Ajax.Callback callback = ajax.getCallback();

            try {
                rs.addMessage(info("-----------------------"));

                rs.addMessage(info(String.format("Send %s request：%s", ajax.getType(), ajax.getUrl())));

                rs.addMessage(info(String.format("Send %s Data：%s", ajax.getType(), $.simple(ajax.getParams()))));

                rs = sendRequest(ajax, isClearSession);

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
            $.file.closeStream(out);
            $.file.closeStream(ips);
        }
    }

    public String sendGet(String url)  {
        return sendRequest(new $Ajax(url).setType($GET)).getDataToString();
    }

    public String sendPost(String url)  {
        return sendRequest(new $Ajax(url).setType($POST)).getDataToString();
    }

    public $Result sendGet($Ajax ajax)  {
        return sendRequest(ajax.setType($GET));
    }

    public $Result sendPost($Ajax ajax)  {
        return sendRequest(ajax.setType($POST));
    }

    public $Result sendGet($Ajax ajax, boolean isClearSession)  {
        return sendRequest(ajax.setType($GET), isClearSession);
    }

    public $Result sendPost($Ajax ajax, boolean isClearSession)  {
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
    public $Result sendRequest($Ajax ajax)  {
        return sendRequest(ajax, false);
    }

    /*
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public $Result sendRequest($Ajax ajax, boolean isClearSession)  {
        if(null != ajax.getOutputStream()) return sendRequest(ajax.getOutputStream(), ajax, isClearSession);

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
            $.closeStream(in, out);
        }

        return rs.setData(result.toString());
    }

    /*
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public $Result sendRequest(OutputStream outputStream, $Ajax ajax, boolean isClearSession)  {
        OutputStreamWriter out = null;
        BufferedReader in = null;
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

                byte[] buffer = new byte[1024];
                // 每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                // 使用一个输入流从buffer里把数据读取出来
                while((len = in.read()) != -1 ){
                    // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
            }

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                byte[] buffer = new byte[1024];
                // 每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                // 使用一个输入流从buffer里把数据读取出来
                while((len = in.read()) != -1 ){
                    // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
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
            $.closeStream(out, in);
        }

        return rs;
    }

    /*
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public void sendRequest(OutputStream outputStream, String targetUrl) throws IOException {
        InputStream inputStream = null; // 对端输出，本端接收流
        HttpURLConnection conn = null; // HTTTP连接

        try {
            /* 创建一个目标URL地址对象 */
            URL url = new URL(targetUrl);

            /* 打开连接 */
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod($GET);

            inputStream = conn.getInputStream();

            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while((len = inputStream.read(buffer)) != -1 ){
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            $.error("Send Request error, e = {}", e);
            throw e;
        } finally {
            $.file.closeStream(inputStream);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /*
     * 发送请求
     * @param ajax ajax 请求对象
     * @return $Result 结构体
     */
    public String sendRequest(String targetUrl) throws IOException {
        return sendRequest(targetUrl, $GET);
    }

    /*
     * 发送请求
     * @param targetUrl targetUrl 请求连接
     * @return String 返回数据
     */
    public String sendRequest(String targetUrl, String method) throws IOException {
        return sendRequest(new $Ajax(targetUrl).setType(method)).getDataToString();
    }


    public String getIp(HttpServletRequest request) {
        if (request == null) {
            throw (new RuntimeException("getIpAddr method HttpServletRequest Object is null"));
        }
        String ip = request.getHeader("X-Real-IP");//通过Nginx作了反向代理后的获取真实ip
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("x-forwarded-for");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")){
            ip = "127.0.0.1";
        }
        // 多个路由时，取第一个非unknown的ip
        if (ip.contains(",")) {
            ip=ip.split(",")[0];
        }
        return ip;
    }

    public String getIp(String... name) throws UnknownHostException {
        InetAddress inetAddress = getInetAddress(last(name));

        return null == inetAddress ? "localhost" : inetAddress.getHostAddress();
    }

    public InetAddress getIPAddress(NetworkInterface networkInterface) {
        for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
            InetAddress ipAddress = address.getAddress();
            if (ipAddress instanceof Inet4Address) {
                return ipAddress;
            }
        }
        return null;
    }

    public InetAddress getInetAddress(String... name) throws UnknownHostException {
        if(isEmptyOrNull(name)) {
            return InetAddress.getLocalHost();
        } else {
            return InetAddress.getByName(last(name));
        }
    }

    public $Result get(String url)  {
        return get(new $Ajax(url));
    }

    public $Result get(String url, Map<String,Object> params)  {
        return get(new $Ajax(url).setParams(JSONObject.fromObject(params)));
    }

    public $Result get(String url, Map<String,Object> params, boolean isClearSession)  {
        return get(new $Ajax(url).setParams(JSONObject.fromObject(params)), isClearSession);
    }

    public $Result get(String url, boolean isClearSession)  {
        return get(new $Ajax(url), isClearSession);
    }

    public $Result post(String url)  {
        return post(new $Ajax(url));
    }

    public $Result post(String url, boolean isClearSession)  {
        return post(new $Ajax(url), isClearSession);
    }

    public $Result get($Ajax ajax)  {
        return ajax(ajax.setType($GET));
    }

    public $Result get($Ajax ajax, boolean isClearSession)  {
        return ajax(ajax.setType($GET), isClearSession);
    }

    public $Result post($Ajax ajax)  {
        return ajax(ajax.setType($POST));
    }

    public $Result post($Ajax ajax, boolean isClearSession)  {
        return ajax(ajax.setType($POST), isClearSession);
    }

    public $Http login($Ajax ajax) {
        post(ajax);
        return this;
    }

    public $Http login(String url, JSONObject jo) {
        login(new $Ajax(url).setData(toHttpParams(jo)));
        return this;
    }

    public $Result ajax(String url)  {
        return ajax(new $Ajax(url));
    }

    public JSONObject getJSON(String url)  {
        return getJSON(new $Ajax(url));
    }

    public JSONObject getJSONObject(String url)  {
        return getJSONObject(new $Ajax(url));
    }

    public JSONArray getJSONArray(String url)  {
        return getJSONArray(new $Ajax(url));
    }

    public JSONObject getJSON($Ajax ajax)  {
        return getJSONObject(ajax.setType($GET));
    }

    public JSONObject getJSONObject($Ajax ajax)  {
        return JSONObject.fromObject(ajax(ajax.setType($GET)).getData());
    }

    public JSONArray getJSONArray($Ajax ajax)  {
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

    public boolean testTelnet(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 500); // 设置连接超时时间为500ms
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean testPing(String host) {
        try {
            InetAddress inetAddress = getInetAddress(host);
            if (inetAddress.isReachable(500)) {
                return true;
            } else {
                return false;
            }
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
