package com.ailbb.ajj.file.xml;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.ailbb.ajj.$;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.*;

/*
 * Created by Wz on 8/9/2019.
 */
public class $JDOMXML {
    Map<String, Integer> mapCount = new HashMap<>();

    /*
     * 通过文件名解析xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Element parse(String fileName) throws IOException {
        $.info("JDOM开始解析XML文件：" + fileName);
        return parse($.file.getInputStream(fileName));
    }

    /*
     * 通过文件名解析xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Element parse(File file) throws IOException {
        $.info("JDOM开始解析XML文件：" + file.getName());
        return parse($.file.getInputStream(file));
    }

    /*
     * 通过文件名解析xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Map<String,String> ParseMap(File file, String name, String key) throws IOException {
        $.info("JDOM开始解析XML文件：" + file.getName());
        return ParseMap($.file.getInputStream(file), name, key);
    }

    /*
     * 通过文件名解析xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Map<String,String> ParseMap(InputStream inputStream, String name, String key) throws IOException {
        Map<String,String> map = new LinkedHashMap<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Element rootElement = null;

        try {
            Document document = saxBuilder.build(inputStream); // 解析XML文件流
            rootElement = document.getRootElement(); // 根节点

            map = parseElementMap(rootElement, name, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /*
     * 通过流解析xml
     * @param inputStream
     * @return
     */
    public Element parse(InputStream inputStream) {
        SAXBuilder saxBuilder = new SAXBuilder();
        Element rootElement = null;

        try {
            Document document = saxBuilder.build(inputStream); // 解析XML文件流
            rootElement = document.getRootElement(); // 根节点
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootElement;
    }

    /*
     * 统计xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Map<String, Integer> count(String fileName) throws IOException {
        return count(parse(fileName));
    }

    /*
     * 统计xml流
     * @param element
     * @return
     */
    public Map<String, Integer> count(Element element) {
        parseElement(element); // 解析根节点

        for(String key: mapCount.keySet()) {
            $.sout(String.format("内容：[%s]，出现次数：[%s]", key, mapCount.get(key)));
        }

        return mapCount;
    }

    /*
     * 解析关键为数组
     * @param element
     */
    Map<String,String> parseElementMap( Element element, String name, String key){
        Map<String,String> map = new LinkedHashMap<>();
        List<Element> childs = element.getChildren(); // 当前节点子节点

        if(element.getName().equals(name)) {
            map.put(element.getAttributeValue(key), element.getValue());
        }

        if(!$.isEmptyOrNull(childs)) { // 继续循环
            for(Element e : childs) map.putAll(parseElementMap(e, name, key));
        }

        return map;
    }

    /*
     * 解析节点
     * @param element
     */
    void parseElement(Element element){
        String name = element.getName(); // 节点名
        List<Element> childs = element.getChildren(); // 子节点
        //获取根节点的子节点，返回子节点的数组

        if(!$.isEmptyOrNull(childs)) {
            int count = 0;
            String key = String.format("节点：[%s]，拥有子节点：[%s]", name, childs.size());

            if(!$.isEmptyOrNull(mapCount.get(key))) count = mapCount.get(key);

            if(count == 0) $.sout(key);

            mapCount.put(key, count+1);

            for(Element e : childs) parseElement(e);
        }

    }

}
