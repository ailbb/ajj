import com.ailbb.ajj.$;
import com.ailbb.ajj.encrypt.util.AESUtil;
import com.ailbb.ajj.encrypt.util.StringEncryptorUtil;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

public class StringEncryptorTest {
    @Test
    public void Encrypt() throws IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException {

        String url1 = "4m2wEL6ip2Er485mfY6mIH+qUOQDrBqsXN82Htdf1mU1V97oe3OKWXCzoTJOd6KzjxEzHuBQXwjlQt8BkJU236YTAaQQ8ELoc1sR8eCm8kL0KeyEF6fRE60E6+kG0WBAZ5e0bRxIAMETSIwdWwzkTQ8Nb/Q4lXvdY1O1HB8WKMKHqmndZr/tjhft4pEyD6tyoczKk+AMGAs=";
        String url2 = "9hEI0RCf5d/RNqKNZvakVHW+14KDPCDL";
        String url3 = "fpV84GYAGNKqviRyiZuge/oEEb5pYA4w";

        System.out.println($.StringEncryptor("111111111111").decrypt(url1));
        System.out.println($.StringEncryptor("111111111111").decrypt(url2));
        System.out.println($.StringEncryptor("111111111111").decrypt(url3));

        System.out.println($.stringencryptor.decrypt("111111111111", url1));
        System.out.println($.stringencryptor.decrypt("111111111111", url2));
        System.out.println($.stringencryptor.decrypt("111111111111", url3));

        String master_url = "jdbc:mysql://12.0.0.1/testdb?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        String master_username = "testuser";
        String master_password = "testpassword";
        System.out.println($.StringEncryptor("111111111111").encrypt(master_url));
        System.out.println($.StringEncryptor("111111111111").encrypt(master_username));
        System.out.println($.StringEncryptor("111111111111").encrypt(master_password));

        System.out.println($.stringencryptor.encrypt("111111111111", master_url));
        System.out.println($.stringencryptor.encrypt("111111111111", master_username));
        System.out.println($.stringencryptor.encrypt("111111111111", master_password));


        new AESUtil().encrypt("","");
    }
}
