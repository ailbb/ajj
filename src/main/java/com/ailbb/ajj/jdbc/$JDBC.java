package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 8/20/2018.
 */
public class $JDBC {
    public $Mysql mysql = new $Mysql();

    public void run(String sql) {
        mysql.run(sql);
    }

    public $JDBC closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {}
        return this;
    }

    public Connection getConnection($JDBCConnConfiguration jdbcConnConfiguration) {
        Connection conn = null;
        try {
            Class.forName(jdbcConnConfiguration.getDriver());
            return DriverManager.getConnection(jdbcConnConfiguration.getUrl(), jdbcConnConfiguration.getUsername(), jdbcConnConfiguration.getPassword());
        } catch (Exception e) {
            $.error(e);
        }
        return conn;
    }

}
