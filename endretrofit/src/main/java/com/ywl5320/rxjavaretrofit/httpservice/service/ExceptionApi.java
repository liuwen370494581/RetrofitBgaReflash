package com.ywl5320.rxjavaretrofit.httpservice.service;


/**
 * Created by liuwen on 2016/10/23.
 */
public class ExceptionApi extends RuntimeException{

    private int code;
    private String msg;

    public ExceptionApi(int resultCode, String msg) {
        this.code = resultCode;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
