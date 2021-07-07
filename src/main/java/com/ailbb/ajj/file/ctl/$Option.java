package com.ailbb.ajj.file.ctl;

/*
 * Created by Wz on 8/2/2018.
 */
class $Option {
    String key;
    Object value;
    String remark;

    protected $Option(String key, Object value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public $Option setKey(String key) {
        this.key = key;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public $Option setValue(Object value) {
        this.value = value;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public $Option setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
