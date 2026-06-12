import com.ailbb.ajj.$;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    // 指定格式化时间对象
    SimpleDateFormat f = new SimpleDateFormat("YYYY 年 MM 月 dd 日 HH 点 mm 分 ss 秒"); // 正则表达式

    public static void main(String[] args) throws InterruptedException {
        $.sout("测试计时器");
        $.sout("测试计时器"+$.timeclock());
        $.sout("测试计时器"+$.timeclock());
        $.sout("测试计时器"+$.timeclock());
        $.sout("测试计时器"+$.timeclock());
//        $.timeclock(0); // 0是启动

//        while (true) {
//            System.out.println("等待时间："+$.timeclock() +"秒");
//            System.out.println("程序已经运行了："+$.timeclock(-1) +"秒");
//            if($.timeclock(-1) > 1000*20) {
//                System.out.println("测试获取所有的值："+$.join($.getTimeclockCache(), "\t") +"");
//                System.out.println("程序结束："+$.timeclock(0) +"秒");
//                break;
//            }
//            Thread.sleep(1500);
//        }
//
//        System.out.println("测试为空的值："+$.timeclock() +"秒");
//        System.out.println("测试-1的值："+$.timeclock(-1) +"秒");
//        System.out.println("测试为0的值："+$.timeclock(0) +"秒");
    }

    @Test // 入口函数
    public void TestDateFormat(){
        Date now = new Date(); // 获取当前时间：创建一个Date对象，

        now.setDate(now.getDate() - 10); // 今天往前推10天

        String formatDate = f.format(now); // 使用时间格式化对象，将时间进行格式化

        System.out.println("今天是 " + formatDate);

        String str = yesterday(now, -1);

        System.out.println("昨天是 " + str);
    }

    /**
     * 获取昨天的当前时间
     * @param date 时间
     * @param num 偏移量
     * @return 偏移过后的时间
     */
    public String yesterday(Date date, int num){
        date.setDate(date.getDate() + num);

        String formatDate = f.format(date);

        return formatDate; // 将当前时间袼式化为指定的格式
    }
}
