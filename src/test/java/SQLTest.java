import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLTest {

    public static void main(String[] args) throws IOException {
        $JDBCConnConfiguration jdbcConnConfiguration = new $JDBCConnConfiguration();

        jdbcConnConfiguration.setDriver("com.mysql.jdbc.Driver");
        jdbcConnConfiguration.setPort(3306);
        jdbcConnConfiguration.setIp("demo-mysql-server");
        jdbcConnConfiguration.setDatabase("testdb");
        jdbcConnConfiguration.setUsername("testuser");
        jdbcConnConfiguration.setPassword("Test@123456");

        $.jdbc.mysql.setConnConfiguration(jdbcConnConfiguration);

        List<Map<String,Object>> list = $.jdbc.mysql.getJdbcTemplate().queryForList(" select * from sys_user where user_name != 'admin'");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        $.jdbc.mysql.getJdbcTemplate().batchUpdate("update sys_user set password = ? where user_id=?", new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, passwordEncoder.encode(list.get(i).get("user_name")+"@123456"));
                preparedStatement.setInt(2, Integer.parseInt(list.get(i).get("user_id").toString()));
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }

}
