import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.http.Ajax;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Created by Wz on 6/30/2019.
 */
public class HttpTest {
    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while( true ) {
                    $Result rs = $.get("https://activity.lagou.com/activityapi/employer/signUp?companyId=150192", true);

                    $.sout(rs.getData());
                    
                    try {
                        Thread.sleep( Math.round(Math.random()*2000) );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
