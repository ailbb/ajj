package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
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

    /**
     * SQL查询结果集转List
     * @param rs 结果集
     * @return List数据
     */
    public List<Map<String,Object>> ResultSetToList(ResultSet rs){
        List<Map<String,Object>> list =new ArrayList();
        try {
            if(null == rs) return list;

            ResultSetMetaData md = rs.getMetaData();//获取键名

            int columnCount = md.getColumnCount();//获取行的数量

            while(rs.next()) {
                Map rowData = new HashMap();//声明Map

                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值

                }

                list.add(rowData);
            }
        } catch (Exception e) {
            $.error(e);
        }

        return list;
    }
}
