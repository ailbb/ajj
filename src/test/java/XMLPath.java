import com.ailbb.ajj.$;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class XMLPath {
    @Test
    public void XMLParseTest(){
        String path = "D:\\Z\\Code\\BRD\\BRD&META\\Code\\Trunk\\LIN6\\micro-service\\timer\\src\\main\\java\\com\\broadtech\\job\\DPIshangbao\\ConfigSQL.xml";
        Map<String,String>  json = $.file.xml.ParseMap(new File(path), "sql", "id");
    }
}
