package com.ailbb.ajj.file.xml;

import com.ailbb.ajj.$;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 8/9/2019.
 */
public class $SAXXML extends DefaultHandler {
    int bookIndex = 0;
    private List<Map<String,Object>> bookList = new ArrayList<>();
    private String value = null;
    private Map<String,Object> book = null;

    @Override
    public void startDocument() throws SAXException {
        $.sout("解析开始");
    }

    //用来标志解析结束
    @Override
    public void endDocument() throws SAXException {
        $.sout("解析结束");
    }

    //用来遍历元素
    //开始标签
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        $.sout(qName);
        if(!$.isEmptyOrNull(qName)) return;
        if(qName.equals("book")) {
            bookIndex++;
            book = new HashMap<>();
            System.out.println("============================第" + bookIndex + "本书开始了=========================");
            //已知book下属性元素的名称
            //System.out.println(attributes.getValue("id"));
            for(int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                $.sout(name);
                //System.out.println(name);
                System.out.println("第" + bookIndex + "本书的" + attributes.getQName(i) + "是:" + attributes.getValue(i)/*attributes.getValue(Qname)*/);
            }
        }else if(!qName.equals("bookStore")) {
            System.out.print("第" + bookIndex + "本书的" + qName + "是:");
        }

    }
    //遍历标签内的内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value = new String(ch,start,length);
        //如果标签下没有内容，如bookstore与book之间就没有内容，将不打印
        if(!value.trim().equals("")) {
            $.sout(value);
        }

    }

    //遍历元素的结束标签
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        $.sout(qName);
    }



    public List<Map<String,Object>> getBookList() {
        return bookList;
    }

}
