package com.ywl5320.rxjavaretrofit;

import android.app.Application;

/**
 * Created by liuwen on 2016/10/23.
 */
public class MyApplication extends Application {

    //保存一些static变量的值
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
