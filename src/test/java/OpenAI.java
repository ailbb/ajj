import java.io.IOException;
import java.util.HashMap;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.http.$Ajax;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenAI {
    public static void main(String[] args) {

        $Result rs = $.post(new $Ajax("http://demo-api-server:8080/v1/chat/completions").setProperty(new HashMap<String,String>(){{
            put("Content-Type", "application/json");
            put("Authorization", "Bearer demo-api-key");
        }}).setData("{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [{\"role\": \"user\", \"content\": \"please say TEST!\"}],\n" +
                "  \"temperature\": 0.7\n" +
                "}"));

        String message="";

        for (String s : rs.getData().toString().split("data:")) {
            int idx = s.lastIndexOf("\"content\":");

            if($.isEmptyOrNull(s) || s.startsWith("data: [DONE]") || idx== -1) continue;

            String json = s.substring("data: ".length());

            String sb1 = s.substring(idx+"\"content\":".length()+1);
            String sb2 = sb1.substring(0, sb1.indexOf("\""));

            message+=sb2;
        }

        System.out.println(message);

    }
}