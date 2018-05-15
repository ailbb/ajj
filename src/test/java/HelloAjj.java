import com.ailbb.ajj.$;
import com.ailbb.ajj.Ajax;

import java.util.Arrays;

/**
 * Created by Wz on 5/9/2018.
 */
public class HelloAjj {
    public static void main(String[] args) throws Exception {
        double q = Math.random();

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
