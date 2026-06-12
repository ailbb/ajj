import com.ailbb.ajj.$;
import com.ailbb.ajj.encrypt.util.AESUtil;
import com.ailbb.ajj.encrypt.util.StringEncryptorUtil;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

public class StringEncryptorTest {
    @Test
    public void Encrypt() throws IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException {


//        System.out.println($.StringEncryptor("sUqN*E98F2%&").encrypt(""));
//        System.out.println($.StringEncryptor("sUqN*E98F2%&").encrypt(""));
//        System.out.println($.StringEncryptor("sUqN*E98F2%&").encrypt(""));
        System.out.println($.StringEncryptor("demo_password1234").decrypt("$2a$10$BhBY2wSjwQc2gcLxsHV4U.Y53B1Ojc5DjHEAXkYGa126vz1dODPXe"));

        if(Math.random()*100 < 100) return;



        System.out.println($.StringEncryptor("demo_key_1234%&").encrypt("demo_val1"));
        System.out.println($.StringEncryptor("demo_key_1234%&").encrypt("demo_val2"));

        String url1 = "demo_encrypted_value_1";
        String url2 = "demo_encrypted_value_2";
        String url3 = "demo_encrypted_value_3";

        System.out.println($.StringEncryptor("demo_key_111111").decrypt(url1));
        System.out.println($.StringEncryptor("demo_key_111111").decrypt(url2));
        System.out.println($.StringEncryptor("demo_key_111111").decrypt(url3));

        System.out.println($.stringencryptor.decrypt("demo_key_111111", url1));
        System.out.println($.stringencryptor.decrypt("demo_key_111111", url2));
        System.out.println($.stringencryptor.decrypt("demo_key_111111", url3));

        String master_url = "jdbc:mysql://demo-db-server/demodb?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        String master_username = "demouser";
        String master_password = "demopassword";
        System.out.println($.StringEncryptor("demo_key_111111").encrypt(master_url));
        System.out.println($.StringEncryptor("demo_key_111111").encrypt(master_username));
        System.out.println($.StringEncryptor("demo_key_111111").encrypt(master_password));

        System.out.println($.stringencryptor.encrypt("demo_key_111111", master_url));
        System.out.println($.stringencryptor.encrypt("demo_key_111111", master_username));
        System.out.println($.stringencryptor.encrypt("demo_key_111111", master_password));


        new AESUtil().encrypt("","");
    }
}
