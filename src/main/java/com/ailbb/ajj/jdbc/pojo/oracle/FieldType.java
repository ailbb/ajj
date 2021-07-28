package com.ailbb.ajj.jdbc.pojo.oracle;

/**
 * Created by steven on 2017/12/19.
 */
public enum FieldType {
    NUMBER, VARCHAR2, DATE, FLOAT, TIMESTAMP;

    public static FieldType[] StrArr2EnumArr(String[] ft) {
        FieldType[] fieldTypes = new FieldType[ft.length];
        for (int i = 0; i < ft.length; i++) {
            fieldTypes[i] = valueOf(ft[i]);
        }
        return fieldTypes;
    }

}
