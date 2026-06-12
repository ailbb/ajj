import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Created by Wz on 6/30/2019.
 */
public class HttpTest {
    public static void main(String[] args) throws IOException {
        String app_id = "demo-app-01";
        String timestamp = "2022/03/29 09:20:51 241";
        String trans_id = "1648191363805";
        String body = "{\"type\":\"normal-hbase\",\"limit\":100,\"param\":{\"table_name\":\"demo_table:test_data\",\"day\":\"20220321\",\"msisdn\":\"13800000000\",\"starttime\":\"1\",\"endtime\":\"18\"}}";
        String secret = "DEMO_SECRET_KEY_FOR_TEST_ONLY";
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