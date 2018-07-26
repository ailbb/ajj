package com.ailbb.ajj.ftp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wz on 6/22/2018.
 */
public class FTPResult {
    /**成功是否*/
    private boolean success = true;
    /**请求信息*/
    private List<String> message = new ArrayList<>();
    /**错误信息*/
    private List<String> error = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public FTPResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public List<String> getMessage() {
        return message;
    }

    public FTPResult setMessage(List<String> message) {
        this.message = message;
        return this;
    }

    public List<String> getError() {
        return error;
    }

    public FTPResult setError(List<String> error) {
        this.error = error;
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
}
