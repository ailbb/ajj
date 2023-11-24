package com.ailbb.ajj.entity;

/*
 * Created by Wz on 6/22/2018.
 */
public class $Status {
    public static final $Status $DEFAULT = getStatus(0); // 0代表默认
    public static final $Status $DEFAULT_SUCCESS = getStatus(1); //（正数开头）1代表成功
    public static final $Status $DEFAULT_ERROR = getStatus(-1); // （负数开头）-1代表失败

    private boolean success = true; // 是否成功
    private int code = 0; // 状态代码
    private String message = ""; // 状态消息
    private String tag = ""; // 状态标签

    private boolean end = false; // 是否结束


    public static $Status getStatus(int i){
        $Status status = new $Status();
        boolean isFind = false;

        for(SUCCESS s: SUCCESS.values()) {
            if(s.code == i) {
                return new $Status(isFind = true, s.code, s.message, s.toString());
            }
        }

        if(!isFind)
            for(ERROR s: ERROR.values()) {
                if(s.code == i) {
                    return new $Status(isFind, s.code, s.message, s.toString());
                }
            }

        return status;
    }

    public $Status() {}

    public $Status(int code) {
        this.code = code;
    }

    public $Status(boolean success, int code, String message, String tag) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.tag = tag;
        this.end = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public $Status setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public $Status setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public $Status setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public int getCode() {
        return code;
    }

    public $Status setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public enum SUCCESS {
        DEFAULT(1, "成功");

        private int code;
        private String message;

        SUCCESS(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    public enum ERROR {
        DEFAULT(-1, "失败");

        private int code;
        private String message;

        ERROR(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @Override
    public String toString() {
        return "$Status{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
