import com.ailbb.ajj.$;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class BrowerTest {

    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("webdriver.edge.driver", "D:\\Code\\AILBB\\ATOOLS\\ajj\\src\\main\\resources\\edgedriver_win64\\msedgedriver.exe");

//        org.openqa.selenium.edge.EdgeDriverService.createDefaultService();
//        org.openqa.selenium.edge.EdgeDriverService
        EdgeDriver driver = new EdgeDriver();
        try {
            driver.navigate().to("https://bing.com");

            WebElement element = driver.findElement(By.id("sb_form_q"));
            element.sendKeys("WebDriver");
            element.submit();

            Thread.sleep(5000);
        } finally {
            driver.quit();
        }

//        //初始化Chrome浏览器实例
//        WebDriver driver = new EdgeDriver();
//
//        //打开一个站点
//        driver.get("https://v.douyu.com/show/0Q8mMYYz11LM49Ad");
//
//        // 打开目标网页
//        driver.get("example.com/m3u8-file");
//
//        // 搜索 m3u8 文件的链接
//        String m3u8Url = driver.findElement(By.id("m3u8-file-link")).getAttribute("href");
//
//        System.out.println(m3u8Url);
//        // 抓取 m3u8 文件
////        driver.navigate(m3u8Url);
//
//        // 设置隐形等待时间
//        Thread.sleep(3000);
//        //窗口最大化
//        driver.manage().window().maximize();
//        Thread.sleep(3000);
//        //关闭浏览器
//        driver.close();
    }
}
