import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import com.ailbb.ajj.entity.$Result;
import net.sf.json.JSONArray;
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
    public static void main(String[] args) throws IOException {
        ttttt();
    }

    private static void ttttt(){
        $JDBCConnConfiguration jdbcConnConfiguration = new $JDBCConnConfiguration();

        jdbcConnConfiguration.setDriver("com.mysql.jdbc.Driver");
        jdbcConnConfiguration.setPort(33306);
        jdbcConnConfiguration.setIp("127.0.0.1");
        jdbcConnConfiguration.setDatabase("");
        jdbcConnConfiguration.setUsername("");
        jdbcConnConfiguration.setPassword("");

        $.jdbc.mysql.setConnConfiguration(jdbcConnConfiguration);

        // 查询分区的结果
        List<Map<String,Object>>  list = $.jdbc.mysql.getJdbcTemplate().queryForList("sql");

        System.out.println(JSONArray.fromObject(list));
    }

}
