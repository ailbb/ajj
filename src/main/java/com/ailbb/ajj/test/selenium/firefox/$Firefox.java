package com.ailbb.ajj.test.selenium.firefox;

import com.ailbb.ajj.$;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

/**
 * Created by Wz on 11/6/2018.
 */
public class $Firefox {
    private String driverPath = "";

    public $Firefox init(String driverPath){
        this.driverPath = driverPath;
        System.setProperty("webdriver.gecko.driver", this.driverPath); // 初始化路径
        return this;
    }

    public $Firefox doAs($FirefoxWorker worker) {
        if($.isEmptyOrNull(driverPath)) throw new RuntimeException("DriverPath is isEmptyOrNull！Please init it!");


        WebDriver driver = new FirefoxDriver();   //声明WebDriver

        try {
            worker.run(driver);
        } catch (Exception e) {
        } finally {
            try {
                Thread.sleep(1000*60*10); // 让线程等待10分钟后退出
                driver.close();//关闭驱动
            } catch (Exception e) {}
        }

        return this;
    }


    public interface $FirefoxWorker {
        void run(WebDriver driver) throws Exception;
    }
}
