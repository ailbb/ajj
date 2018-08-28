package com.ailbb.ajj.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

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

}
