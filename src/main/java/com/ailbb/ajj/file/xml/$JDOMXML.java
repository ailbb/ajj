package com.ailbb.ajj.file.xml;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailbb.ajj.$;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.*;

/**
 * Created by Wz on 8/9/2019.
 */
public class $JDOMXML {
    Map<String, Integer> mapCount = new HashMap<>();

    /**
     * 通过文件名解析xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Element parse(String fileName) throws IOException {
        $.info("JDOM开始解析XML文件：" + fileName);
        return parse($.file.getInputStream(fileName));
    }

    /**
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

    /**
     * 统计xml
     * @param fileName
     * @return
     * @throws IOException
     */
    public Map<String, Integer> count(String fileName) throws IOException {
        return count(parse(fileName));
    }

    /**
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

    /**
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
