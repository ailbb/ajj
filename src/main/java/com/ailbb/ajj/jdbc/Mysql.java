package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import com.ailbb.ajj.entity.$Result;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 8/20/2018.
 */
public class Mysql extends JDBCRunner {
    /**
     * demo
     private String driver = "com.mysql.jdbc.Driver";
     private String ip = "localhost";
     private int port = 3306;
     private String database = "mysql";
     private String username;
     private String password;
     *
     */
    public static final String $DRIVER = "com.mysql.jdbc.Driver";
    public static final int $PORT = 3306;

    public JDBCRunner setConnConfiguration($JDBCConnConfiguration connConfiguration) {
        super.setConnConfiguration(connConfiguration);
        
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName($.lastDef($DRIVER, connConfiguration.getDriver()));
        dataSource.setUrl(
                !$.isEmptyOrNull(connConfiguration.getUrl()) ? connConfiguration.getUrl() :
                        String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&allowMultiQueries\\=true", $.notNull(connConfiguration.getIp()), $.notNull(connConfiguration.getPort()), $.notNull(connConfiguration.getDatabase()))
        );
        dataSource.setUsername($.notNull(connConfiguration.getUsername()));
        dataSource.setPassword($.notNull(connConfiguration.getPassword()));
        return setJdbcTemplate(new JdbcTemplate(dataSource));
    }

}
