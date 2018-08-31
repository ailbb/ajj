package com.ailbb.ajj.entity;

import com.ailbb.ajj.$;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wz on 6/22/2018.
 */
public class $Result {
    /**是否成功*/
    private boolean success = $Status.$DEFAULT.isSuccess();
    /**状态码*/
    private int code = $Status.$DEFAULT.getCode();
    /**状态*/
    private $Status status = $Status.$DEFAULT;
    /**数据*/
    private Object data = "";
    /**标题*/
    private String title = "";
    /**请求信息*/
    private List<String> message = new ArrayList<>();
    /**错误信息*/
    private List<Exception> error = new ArrayList<>();
    /**备注*/
    private String remark = "";

    public $Result() {}

    public $Result(boolean success) {
        this.setSuccess(success);
    }

    public boolean isSuccess() {
        return getStatus().isSuccess();
    }

    public $Result setSuccess(boolean success) {
        this.success = setStatus(success ? $Status.$DEFAULT_SUCCESS : $Status.$DEFAULT_ERROR).isSuccess();
        return this;
    }

    public List<String> getMessage() {
        return message;
    }

    public $Result setMessage(List<String> message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public $Result setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Exception> getError() {
        return error;
    }

    public $Result setError(List<Exception> error) {
        this.error = error;
        return this;
    }

    public int getCode() {
        return getStatus().getCode();
    }

    public $Result setCode(int code) {
        this.code = setStatus($Status.getStatus(code)).getCode();
        return this;
    }

    public $Status getStatus() {
        return status;
    }

    public $Result setStatus($Status status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public $Result setData(Object data) {
        this.data = data;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public $Result setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public $Result addMessage(String... messages) {
        for(String m : messages)
            this.message.add(m);

        return this;
    }

    public $Result addError(Exception... exceptions) {
        this.setSuccess(false);
        for(Exception e : exceptions) {
            this.error.add(e);
            this.addMessage(e.getMessage());
        }
        return this;
    }

    public String getDataToString() {
        try {
            return $.str(data);
        } catch (Exception e) {
            addError(e).setData("");
            return "";
        }
    }

    public List<Object> getDataToList() {
        try {
            return ((ArrayList<Object>)this.data);
        } catch (Exception e) {
            List<Object> edata = new ArrayList<Object>();
            addError(e).setData(edata);
            return edata;
        }
    }

    public Map<String, Object> getDataToMap() {
        try {
            return ((HashMap<String, Object>)this.data);
        } catch (Exception e) {
            Map<String, Object> edata = new HashMap<String, Object>();
            addError(e).setData(edata);
            return edata;
        }
    }

    public $Result addData(String... datas) {
        if($.isEmptyOrNull(this.data)) this.data = "";

        for(String d : datas)
            this.data += d;

        return this;
    }

    public $Result addData(Object... objects) {
        if($.isEmptyOrNull(this.data)) this.data = new ArrayList<Object>();

        for(Object o : objects)
            ((ArrayList<Object>)this.data).add(o);

        return this;
    }

    public $Result addData(String key, Object value) {
        if($.isEmptyOrNull(this.data)) this.data = new HashMap<String, Object>();

        ((HashMap<String, Object>)this.data).put(key, value);

        return this;
    }

    @Override
    public String toString() {
        return "$Result{" +
                "success=" + success +
                ", code=" + code +
                ", status=" + status +
                ", data=" + data +
                ", message=" + message +
                ", error=" + error +
                ", remark='" + remark + '\'' +
                '}';
    }
}
