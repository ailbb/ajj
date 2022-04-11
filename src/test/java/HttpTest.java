import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.http.Ajax;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/*
 * Created by Wz on 6/30/2019.
 */
public class HttpTest {
    public static void main(String[] args) throws IOException {
        String app_id = "brd-app-01";
        String timestamp = "2022/03/29 09:20:51 241";
        String trans_id = "1648191363805";
        String body = "{\"type\":\"normal-hbase\",\"limit\":100,\"param\":{\"table_name\":\"zb_zhw_yw_broadtech:xdr_msisdn\",\"day\":\"20220321\",\"msisdn\":\"18602333818\",\"starttime\":\"1\",\"endtime\":\"18\"}}";
        String secret = "1E2F3FAA7346835928D5D9B13BC7CE3B";
        String p1=null;
        String p2=null;

        String sign = encodeSHA256(app_id+"#"+timestamp+"#"+trans_id+"#"+p1+"#"+p2+"#"+body+"#"+secret);

        System.out.println(sign);

    }

    private static String encodeSHA256(String s) {

        System.out.println(s);
        System.out.println(getSHA256StrJava(s));

        return "23f0be3665dd1bbbe0dbe3d5758de87dc5e84bd29e352e406d1f68c825bca670";
    }

    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}