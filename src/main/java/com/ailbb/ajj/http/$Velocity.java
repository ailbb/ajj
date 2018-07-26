package com.ailbb.ajj.http;

import com.ailbb.ajj.$;
import net.sf.json.JSONObject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Wz on 6/20/2018.
 */
public class $Velocity {

    /**
     * 将数据通过模板方式转向打印
     * @param request
     * @param response
     * @param relPath
     * @param object
     */
    public boolean sendVelocity(HttpServletRequest request, HttpServletResponse response, String relPath, JSONObject object) {
        try {
            // 初始化模板引擎
            VelocityEngine ve = new VelocityEngine();
            // 设值模板引擎上下文
            ve.setApplicationAttribute("javax.servlet.ServletContext", request.getServletContext());
            //设置velocity资源加载方式为webapp资源
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "webapp");
            //设置velocity资源加载方式为webapp时的处理类
            ve.setProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
            ve.init();

            // 获取模板文件
            Template t = ve.getTemplate(relPath, "utf-8");

            // 转换对象
            VelocityContext ctx = new VelocityContext(object);

            PrintWriter pw = response.getWriter();
            t.merge(ctx, pw);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
