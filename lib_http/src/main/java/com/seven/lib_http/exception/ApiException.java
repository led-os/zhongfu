package com.seven.lib_http.exception;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public class ApiException extends Exception {
    private int code;
    private String msg;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
