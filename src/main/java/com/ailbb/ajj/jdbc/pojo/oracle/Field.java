package com.ailbb.ajj.jdbc.pojo.oracle;

/**
 * Created by steven on 2017/12/19.
 */
public class Field {
    private String fieldName;
    private FieldType fieldType;

    public String getFieldName() {
        return this.fieldName;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = FieldType.valueOf(fieldType);
    }

    public String toString() {
        return this.fieldName + " :" + this.fieldType;
    }
}
