package com.ailbb.ajj.file.xml;

import com.ailbb.ajj.$;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by Wz on 8/9/2019.
 */
public class $SAXXML extends DefaultHandler {
    int bookIndex = 0;
    private List<Map<String,Object>> bookList = new ArrayList<>();
    private String value = null;

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

    }
    //遍历标签内的内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value = new String(ch,start,length);
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
