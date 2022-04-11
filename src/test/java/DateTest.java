import com.ailbb.ajj.$;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    // 指定格式化时间对象
    SimpleDateFormat f = new SimpleDateFormat("YYYY 年 MM 月 dd 日 HH 点 mm 分 ss 秒"); // 正则表达式

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
