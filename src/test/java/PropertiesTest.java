import com.ailbb.ajj.$;

import java.util.Properties;

/*
 * Created by Wz on 6/30/2019.
 */
public class PropertiesTest {
    public static void main(String[] args) {

        System.out.println($.file.properties.getProperty("D:\\Demo\\Project\\demo-config\\src\\main\\resources\\config\\demo-config.yml", "cmf.login"));
    }
}
