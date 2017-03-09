package sh.ajb.com.endjpush.application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Boom on 2016/12/1.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
