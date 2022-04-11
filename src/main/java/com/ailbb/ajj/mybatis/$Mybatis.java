package com.ailbb.ajj.mybatis;

import com.ailbb.ajj.entity.$Result;
import org.apache.ibatis.session.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class $Mybatis {
    public $MybatisDynamicSQL getMybatisDynamiSQL(Configuration configuration, String[] packageSearchPaths) throws IllegalAccessException, NoSuchFieldException, IOException {
        return new $MybatisDynamicSQL(configuration, packageSearchPaths);
    }

    public $Result refreshMapperCache(Configuration configuration, String[] packageSearchPaths) throws IllegalAccessException, NoSuchFieldException, IOException {
        return new $MybatisDynamicSQL(configuration, packageSearchPaths)
            .refreshMapperCache();
    }
}
