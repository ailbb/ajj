import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import com.ailbb.ajj.entity.$Result;
import net.sf.json.JSONObject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/*
 * Created by Wz on 6/30/2019.
 */
public class JDBCTest {
    public static void main(String[] args) throws IOException {}


    private static void 补建应用层表分区记录(){
        $JDBCConnConfiguration jdbcConnConfiguration = new $JDBCConnConfiguration();

        jdbcConnConfiguration.setDriver("com.mysql.jdbc.Driver");
        jdbcConnConfiguration.setPort(33306);
        jdbcConnConfiguration.setIp("127.0.01");
        jdbcConnConfiguration.setDatabase("test");
        jdbcConnConfiguration.setUsername("root");
        jdbcConnConfiguration.setPassword("123456");

        $.jdbc.mysql.setConnConfiguration(jdbcConnConfiguration);

        // 查询分区的结果
        $.jdbc.mysql.getJdbcTemplate().execute("SELECT db_name,tbl_name,load_hive,storage_hive,par_time,par_format FROM hive_tbls_conf WHERE db_name='prestat' AND tbl_name NOT LIKE 'v_%'; ");
    }

}
