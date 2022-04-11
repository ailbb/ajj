package com.ailbb.ajj.mybatis;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.io.Resource;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class $MybatisDynamicSQL {
    public static HashMap<String, Long> fileLastCache = new HashMap<>();// 记录文件是否变化

    // MyBatis配置对象
    private Configuration configuration;
    // 需要扫描的包
    public static String[] packageSearchPaths = new String[]{};

    public $MybatisDynamicSQL(Configuration configuration) {
        this.configuration = configuration;
    }

    public $MybatisDynamicSQL(Configuration configuration, String[] _packageSearchPaths) {
        this.configuration = configuration;
        packageSearchPaths = _packageSearchPaths;
    }

    /**
     * 刷新缓存
     * @throws IOException 文件异常
     * @throws IllegalAccessException 异常
     * @throws NoSuchFieldException 异常
     * @return 刷新结果
     */
    public $Result refreshMapperCache() throws NoSuchFieldException, IllegalAccessException, IOException {
        return refreshMapperCache(packageSearchPaths);
    }

    /**
     * 刷新缓存
     * @param _packageSearchPaths 刷新的路径信息
     * @throws IOException 文件异常
     * @throws IllegalAccessException 异常
     * @throws NoSuchFieldException 异常
     * @return  刷新结果
     */
    public $Result refreshMapperCache(String... _packageSearchPaths) throws IllegalAccessException, NoSuchFieldException, IOException {
        $Result result = $.result();

        if($.isEmptyOrNull(_packageSearchPaths)) throw new NullPointerException("PackageSearchPaths is NullPointerException!"); // 抛出空异常

        checkArgs(); // 检查配置项是否为空

        // step.1 扫描文件列表
        Resource[] resourceList = $.file.scanFilesResource(_packageSearchPaths);

        // step.2 清理原有资源，更新为自己的StrictMap方便，增量重新加载
        deleteMapField();

        // step.3 清理已加载的资源标识，方便让它重新加载。
        deleteLoaderResources();

        // step.4 刷新对应文件
        for(Resource resource : resourceList) result.addMessage(refreshMapperCache(resource).getMessage().get(0)); // 刷新文件

        return result.addMessage("刷新文件完成！");
    }

    /**
     * 刷新缓存
     * @param resource 需要刷新的资源
     * @throws IOException 文件异常
     * @return 刷新结果
     */
    public $Result refreshMapperCache(Resource resource) throws IOException {
        $Result result = $.result();
        File file = resource.getFile();

        // 如果文件没改动，则直接返回
//        if(!isChanged(resource.getFile())) return result.addMessage("["+resource.getFile().getName()+"] 未刷新！");

        //重新编译加载资源文件。
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(), configuration,
                resource.toString(), configuration.getSqlFragments());
        xmlMapperBuilder.parse();

        $.debug("Refresh filename: " + file.getName());

        // 更新文件缓存的时间
        fileLastCache.put(file.getName(), System.currentTimeMillis());

        return result.addMessage("["+file.getName()+"] 刷新文件完成！");
    }

    /**
     * 写动态文件
     */
    private $MybatisDynamicSQL writeDynamicFile(String dynamicFile, List<Map<String,Object>> dynamicSQLs) {
        if($.isEmptyOrNull(dynamicFile) || !$.isExists(dynamicFile) || $.isEmptyOrNull(dynamicSQLs)) return this;
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\" \"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">\n");
        sb.append("<mapper namespace=\"DynamicSQL\">");

        for(Map<String,Object> map : dynamicSQLs) sb.append(map.get("sql_context"));

        sb.append("</mapper>");

        $.info("将数据库内容写入动态SQL...");
        try {
            $.file.writeFile(dynamicFile, sb.toString());
        } catch (Exception e) {
            $.warn("写入失败...");
        }

        return this;
    }

    /**
     * 清理原有资源，更新为自己的StrictMap方便，增量重新加载
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void deleteMapField() throws NoSuchFieldException, IllegalAccessException {
        String[] mapFieldNames = new String[]{ "mappedStatements", "caches", "resultMaps", "parameterMaps", "keyGenerators", "sqlFragments" };

        for (String fieldName : mapFieldNames){
            Field field = configuration.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Map mapConfig = (Map) field.get(configuration);
            mapConfig.clear();
//            field.set(configuration, map);
        }
    }

    /**
     * 清理已加载的资源标识，方便让它重新加载。
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void deleteLoaderResources() throws NoSuchFieldException, IllegalAccessException {
        Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
        loadedResourcesField.setAccessible(true);
        Set loadedResourcesSet = ((Set) loadedResourcesField.get(configuration));
        loadedResourcesSet.clear();
    }

    /**
     * 清理已加载的资源标识，方便让它重新加载。
     * @param resource 资源信息
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void deleteLoaderResources(Resource resource) throws NoSuchFieldException, IllegalAccessException {
        Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
        loadedResourcesField.setAccessible(true);
        Set loadedResourcesSet = ((Set) loadedResourcesField.get(configuration));
        loadedResourcesSet.remove(resource);
    }

    /**
     * 检查配置项是否正常
     */
    private void checkArgs() {
        if(null == configuration) throw new NullPointerException("Configuration is NullPointerException!");
    }

    /**
     * 判断文件是否改动
     * @param file 文件
     * @return 需要刷新返回true，否则返回false
     */
    private boolean isChanged(File file) {
        return file.lastModified() >  (null == fileLastCache.get(file.getName()) ? fileLastCache.put(file.getName(), file.lastModified()) : fileLastCache.get(file.getName()));
    }

}
