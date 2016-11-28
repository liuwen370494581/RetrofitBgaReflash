package com.ywl5320.rxjavaretrofit.httpservice.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuwen on 2016/11/17.
 */
public class wuLiuInfo implements Serializable {


    private String time;
    private String ftime;
    private String context;


    @Override
    public String toString() {
        return "wuLiuInfo{" +
                "time='" + time + '\'' +
                ", ftime='" + ftime + '\'' +
                ", context='" + context + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
