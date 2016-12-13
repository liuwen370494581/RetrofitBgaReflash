package com.ywl5320.rxjavaretrofit.pjo;

/**
 * Created by liuwen on 2016/12/13.
 */
public class RxBusModel {

    private String from;
    private String to;
    private String content;

    public RxBusModel(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
