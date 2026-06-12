import com.ailbb.ajj.$;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

public class XMLPath {
    @Test
    public void XMLParseTest(){
        String path = "D:\\Demo\\Project\\demo-service\\src\\main\\resources\\demo-config.xml";
        Map<String,String>  json = $.file.xml.ParseMap(new File(path), "sql", "id");
//        $.now()
    }
}
