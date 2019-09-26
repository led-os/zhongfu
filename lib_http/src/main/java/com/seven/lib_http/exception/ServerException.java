package com.seven.lib_http.exception;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public class ServerException extends RuntimeException {
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
