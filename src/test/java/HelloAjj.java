import com.ailbb.ajj.$;
import com.ailbb.ajj.http.Ajax;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Wz on 5/9/2018.
 */
public class HelloAjj {
    public static void main(String[] args) throws Exception {
        double q = Math.random();

        String text = $.readFile("/D:/Z/Code/java/java-ee/Share/sharepro/target/sharepro/module/system/control/MyApp/app/Common.js");

        if(q<1) return;

        String json = $.get("http://132.225.168.211:19888/ws/v1/history/mapreduce/jobs");
        JSONObject Jobs = JSONObject.fromObject(json);
        JSONObject job = Jobs.getJSONObject("jobs");
        JSONArray jarr = job.getJSONArray("job");

        for(Object info : jarr) {
            JSONObject data = JSONObject.fromObject(info);
            String jobId = data.getString("jobId");
        }

        $.copyFile("D:\\Z\\Work\\Project\\20171215!-【BigData】\\sharepro相关\\环境部署\\release\\broadtech\\config", "C:\\Users\\sirzh\\Desktop\\t");

        $.sout($.getRootPath());
        $.sout($.last(null));
        $.sout($.rel("C:/windows/system32/drivers/etc/HOSTS/", "../nihao/abc"));
        $.sout($.rel("C:/windows/system32/drivers/etc/HOSTS", "../nihao/abc"));
        $.sout($.rel("C:/windows/system32/drivers/etc/HOSTS", "./nihao/abc"));
        $.sout($.rel("C:/windows/system32/drivers/etc/HOSTS", "nihao/abc"));

        $.sout($.indexOfList("/", "C:/windows/system32/drivers/etc/HOSTS/"));

        $.sout($.getPath(""));
        $.sout($.getPath("."));
        $.sout($.getPath("../nihao/abc"));

        $.sout($.concat(null, "", "a", "<", "|", "nihao"));
        $.sout($.concat());
        $.copyFile("D:\\Z\\Work\\Project\\20171215!-【BigData】\\mr相关\\环境部署", "C:\\Users\\sirzh\\Desktop", true);


        $.sout($.readFile("ajj.json"));

        $.readFile("com/ailbb/ajj/$.class");
        $.sout("yes");

        $.sout($.readFile("C:/windows/system32/drivers/etc/HOSTS"));

        $.sout($.getPath("/"));
        $.sout($.getPath("/name"));
        $.sout($.getPath("C:/windows/system32/drivers/etc/HOSTS"));
        $.sout($.getPath("C:\\windows\\system32\\drivers\\etc\\HOSTS"));

        $.sout($.last(null));
        $.sout($.last("1","2","3"));
        $.sout($.lastDef("1","2","3"));
        $.sout($.lastDef("1"));

        $.sout($.first());
        $.sout($.firstDef("1","2","3"));
        $.sout($.first("1","2","3"));
        $.sout($.firstDef("1"));

        $.sout($.now());
        $.log($.now("ss"));
        $.log($.now("n"));
        $.sout($.now("ns"));

        $.log($.join(Arrays.asList(new Object[]{"ni",null, "798", "798", "798"}), "|"));

        $.log($.getIp());
        $.log($.getIp("www.microsoft.com"));

        $.writeFile("/b.json");

        String u1 = "http://www.ailbb.com/test";
        Ajax u2 = new Ajax("http://www.ailbb.com/test");
        Ajax u3 = new Ajax("/", "/test");

        $.log($.get(u1));
        Thread.sleep(1000);
        $.log($.getJSON(u1));
        Thread.sleep(1000);
        $.log($.getJSONObject(u1));
        Thread.sleep(1000);
        $.log($.getJSONArray(u1));
        Thread.sleep(1000);

        $.log($.post(u1));
        Thread.sleep(1000);
        $.log($.ajax(u1));
        Thread.sleep(1000);




        $.log($.get(u2));
        Thread.sleep(1000);
        $.log($.getJSON(u2));
        Thread.sleep(1000);
        $.log($.getJSONObject(u2));
        Thread.sleep(1000);
        $.log($.getJSONArray(u2));
        Thread.sleep(1000);

        $.log($.post(u2));
        Thread.sleep(1000);
        $.log($.ajax(u2));
        Thread.sleep(1000);



        $.log($.get(u3));
        Thread.sleep(1000);
        $.log($.getJSON(u3));
        Thread.sleep(1000);
        $.log($.getJSONObject(u3));
        Thread.sleep(1000);
        $.log($.getJSONArray(u3));
        Thread.sleep(1000);

        $.log($.post(u3));
        Thread.sleep(1000);
        $.log($.ajax(u3));
        Thread.sleep(1000);
    }
}
