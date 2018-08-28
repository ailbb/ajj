package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wz on 8/20/2018.
 */
public class $Mysql {
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
    private $JDBCConnConfiguration connConfiguration; // 连接配置信息
    private JdbcTemplate jdbcTemplate; // 生成的连接
    public final String $DRIVER = "com.mysql.jdbc.Driver";
    public final int $PORT = 3306;

    /**
     * 执行sql
     * @param sql
     * @return
     */
    public $Mysql run(String sql) {
        return run(sql, new ArrayList<>());
    }

    /**
     * 执行sql
     * @param sql
     * @return
     */
    public $Mysql run(String sql, List<Object> list) {
        jdbcTemplate.update(sql,  new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                for(int i=list.size(); i-->0;) ps.setObject(i+1, list.get(i));
            }
        });

        return this;
    }

    /**
     *
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public $Mysql setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    public $JDBCConnConfiguration getConnConfiguration() {
        return connConfiguration;
    }

    public $Mysql setConnConfiguration($JDBCConnConfiguration connConfiguration) {
        this.connConfiguration = connConfiguration;
        
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName($.notNull(connConfiguration.getDriver()));
        dataSource.setUrl(
                !$.isEmptyOrNull(connConfiguration.getUrl()) ? connConfiguration.getUrl() :
                        String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&allowMultiQueries\\=true", $.notNull(connConfiguration.getIp()), $.notNull(connConfiguration.getPort()), $.notNull(connConfiguration.getDatabase()))
        );
        dataSource.setUsername($.notNull(connConfiguration.getUsername()));
        dataSource.setPassword($.notNull(connConfiguration.getPassword()));
        return setJdbcTemplate(new JdbcTemplate(dataSource));
    }

}
