package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Wz on 8/20/2018.
 */
public class $JDBC {
    private JDBCRunner jdbcRunner = null;
    public Mysql mysql = new Mysql();

    public void run(String sql) {
        getJDBCRunner().run(sql);
    }

    public JDBCRunner getJDBCRunner(){
        return getJDBCRunner(null);
    }

    public JDBCRunner getJDBCRunner($JDBCConnConfiguration jdbcConnConfiguration){
        if(null != jdbcRunner) return jdbcRunner;

        return mysql.setConnConfiguration(jdbcConnConfiguration);
    }

    public JdbcTemplate getJdbcTemplate(){
        return getJDBCRunner().getJdbcTemplate();
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

    public JdbcTemplate getJdbcTemplate($JDBCConnConfiguration connConfiguration){
        return (null == jdbcRunner ? mysql : jdbcRunner). setConnConfiguration(connConfiguration).getJdbcTemplate();
    }
}
