package com.ailbb.ajj.result;

import java.util.ArrayList;
import java.util.List;

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
    private Object data = new Object[]{};
    /**请求信息*/
    private List<String> message = new ArrayList<>();
    /**错误信息*/
    private List<String> error = new ArrayList<>();
    /**备注*/
    private String remark = "";

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

    public List<String> getError() {
        return error;
    }

    public $Result setError(List<String> error) {
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

    public String addMessage(String message) {
        this.message.add(message);
        return message;
    }

    public String addError(String error) {
        this.error.add(error);
        return error;
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
