package com.ailbb.ajj.tomcat;

import com.ailbb.ajj.$;
import org.apache.commons.logging.LogFactory;

import java.io.File;

/*
 * Created by Wz on 11/21/2018.
 */
public class $Tomcat {
    private String rootPath;
    private boolean inited = false;

    public $Tomcat init(){
        inited = (inited == true ? inited : initSpringBoot());
        return this;
    }

    private boolean initSpringBoot(){
        try {
            Class documentroot = Class.forName("org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory");
            this.rootPath = new $DocumentRoot(LogFactory.getLog(documentroot)).getValidDirectory().getAbsolutePath();

            if(!$.isEmptyOrNull(rootPath)) this.rootPath = $.getPath(rootPath);

            $.info(String.format("SpringBoot Webapp Root Path：[%s]", rootPath));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String getRootPath() {
        return init().rootPath;
    }

}
