package com.ailbb.ajj.jdbc;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$JDBCConnConfiguration;
import com.ailbb.ajj.entity.$Result;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.security.PrivilegedExceptionAction;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 8/20/2018.
 */
public abstract class JDBCRunner {
    private JdbcTemplate jdbcTemplate; // 生成的连接
    private DriverManagerDataSource dataSource; // 数据源信息
    private $JDBCConnConfiguration connConfiguration; // 连接配置信息

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
            rs.setData(getJdbcTemplate().update(sql,  new PreparedStatementSetter() {
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
     * 执行SQL
     * @param sql
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        return getJdbcTemplate().queryForList(sql);
    }

    /**
     *
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public JDBCRunner setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    public $JDBCConnConfiguration getConnConfiguration(){ return this.connConfiguration; };

    public JDBCRunner setConnConfiguration($JDBCConnConfiguration connConfiguration) {
        this.connConfiguration = connConfiguration;
        return this;
    };

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public JDBCRunner setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }
}
