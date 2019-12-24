package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import com.ailbb.ajj.entity.$Result;
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
    public static final String $DRIVER = "com.mysql.jdbc.Driver";
    public static final int $PORT = 3306;

    /**
     * 执行sql
     * @param sql
     * @return
     */
    public $Result run(String sql) {
        return run(sql, new ArrayList<>());
    }

    /**
     * 执行sql
     * @param sql 执行sql
     * @return
     */
    public $Result run(String sql, List<Object> list)  {
        $Result rs = $.result();

        try {
            rs.setData(jdbcTemplate.update(sql,  new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    for(int i=0; i<list.size(); i++) ps.setObject(i+1, list.get(i));
                }
            }));
        } catch (Exception e) {
            rs.addError($.exception(e));
        }

        return rs;
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
