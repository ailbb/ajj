package com.ailbb.ajj.jdbc.pojo.oracle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 2017/8/31.
 */
public class Table {

    private String tableName = null;

    private List<Field> fieldInfos = new ArrayList();

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public void addField(Field fieldInfo) {
        this.fieldInfos.add(fieldInfo);
    }

    public String getFNString() {
        StringBuffer sb = new StringBuffer();
        for (Field fi : this.fieldInfos) {
            sb.append("\"" + fi.getFieldName() + "\"").append(",");
        }
        String fieldsNameStr = sb.toString();
        return fieldsNameStr.substring(0, fieldsNameStr.length() - 1);
    }

    public FieldType[] getFTArray() {
        FieldType[] fieldTypeArr = new FieldType[this.fieldInfos
                .size()];
        int i = 0;
        for (Field fi : this.fieldInfos) {
            fieldTypeArr[i] = fi.getFieldType();
            i++;
        }
        return fieldTypeArr;
    }

    public List<Field> getFieldInfos() {
        return this.fieldInfos;
    }

    public String getTableName() {
        return this.tableName;
    }

    public int fieldSize() {
        return this.fieldInfos.size();
    }

    public String toString() {
        return this.tableName + ":" + this.fieldInfos.toString();
    }
}
