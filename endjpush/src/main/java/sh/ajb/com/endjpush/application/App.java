package sh.ajb.com.endjpush.application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by liuwen on 2016/12/5.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
