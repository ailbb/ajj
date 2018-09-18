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
    private boolean success = $Status.$DEFAULT.isSuccess(); // 是否成功
    private List<String> message = new ArrayList<>(); // 请求信息
    private List<Exception> error = new ArrayList<>(); // 错误信息
    private int code = $Status.$DEFAULT.getCode(); // 状态码
    private $Status status = $Status.$DEFAULT; // 状态
    private String type = "object"; // 数据类型有默认值
    private Object data = ""; // 数据
    private String title = ""; // 标题
    private String remark = ""; // 备注

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

    public $Result addMessage(boolean isSuccess, String... messages) {
        this.setSuccess(isSuccess);

        for(String m : messages)
            this.message.add(m);

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
            return "";
        }
    }

    public List<Object> getDataToList() {
        try {
            return ((ArrayList<Object>)this.data);
        } catch (Exception e) {
            return new ArrayList<Object>();
        }
    }

    public Map<String, Object> getDataToMap() {
        try {
            return ((HashMap<String, Object>)this.data);
        } catch (Exception e) {
            return new HashMap<String, Object>();
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

    public String getType() {
        return type;
    }

    public $Result setType(String type) {
        this.type = type;
        return this;
    }

    public $Result concat($Result result) {
        this.getMessage().containsAll(result.getMessage());
        this.getError().containsAll(result.getError());

        if(!success) return this;

        setSuccess(result.isSuccess());
        setCode(result.getCode());
        setStatus(result.getStatus());
        setType(result.getType());
        setTitle(result.getTitle());
        setRemark(result.getRemark());


        Object s = getData();
        Object d = result.getData();

        try {
            if(s instanceof List) {
                if(d  instanceof List && (!$.isEmptyOrNull(s) || !$.isEmptyOrNull(d)))
                    ((List) s).containsAll((List)d);
                else if(!$.isEmptyOrNull(d))
                    ((List) s).add(d);

                setType("list");
            } else if(d instanceof List) {
                if(!$.isEmptyOrNull(s)) ((List) d).add(s);
                return setType("list").setData(d);
            } else if(s instanceof Map && d  instanceof Map) {
                if(!$.isEmptyOrNull(s) || !$.isEmptyOrNull(d))
                    ((Map) s).putAll((Map) d);
                setType("map");
            } else
                throw new RuntimeException();
        } catch (Exception e) {
            List<Object> list = new ArrayList<Object>(){{
                if(!$.isEmptyOrNull(s)) add(s);
                if(!$.isEmptyOrNull(d)) add(d);
            }};

            if(!$.isEmptyOrNull(list)) setType("list").setData(list);
        }

        return this;
    }

    @Override
    public String toString() {
        return "$Result{" +
                "success=" + success +
                ", message=" + message +
                ", error=" + error +
                ", code=" + code +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", data=" + data +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
