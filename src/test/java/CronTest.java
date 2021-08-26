import com.ailbb.ajj.$;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class CronTest {
    public static void main(String[] args) throws Exception {
        new CronTest().cronweektest();
/*
        boolean a = true;

        Properties p = $.file.yml.getProperties("D:\\Z\\Code\\java\\java-ee\\Share\\sharepro\\code\\web-service\\sharepro\\src\\main\\resources\\cdb\\local-127\\auth-config\\auth-user.yml");

        Object bbb = p.get("auth.users");

        if(a) return;
        String now = $.now("ns"); // id
        String c = "0/0 0/0 0/1 * * ? *";
        String[] corn = c.split("\\s+");

        if(corn.length < 6) throw new Exception("时间表达式有误(用空格分隔)！请查看任务定义：" + c);
        if(corn.length >= 6) corn = $.list.subCollection(corn, 0, 6);

        List<Date> dates = $.date.cron.init($.string.join(corn, " ")).ranges($.date.parse("2019-12-17 00:00:00"), $.date.parse("2019-12-18 00:00:00"));

        if(dates.size() > 100) throw new Exception("生成执行任务数量"+dates.size()+"条，超过最大限制（100条），请重新选取时间段，或者分次插入。");


        String[] strings = new String[] {"1","2","3","4","5","6","7"};

        strings = $.list.subCollection(strings, 0, 6);

        System.out.println(strings);
*/

    }



    public void cronweektest() throws ParseException {
        String cron = "0 00 1 ? * 5";

        List<Date> dates = $.date.cron.init(cron, false).ranges($.date.parse("2021-08-26 00:00:00"), $.date.parse("2021-10-26 00:00:00"));
        for(Date d : dates) {
            System.out.println($.date.format(d));
        }
    }

}
