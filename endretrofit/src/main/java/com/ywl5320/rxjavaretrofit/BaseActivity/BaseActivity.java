package com.ywl5320.rxjavaretrofit.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.ywl5320.rxjavaretrofit.permisssion.XPermissionUtils;
import com.ywl5320.rxjavaretrofit.utils.RxBus;

import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by liuwen on 2016/10/13.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static Context mContext;
    //12月13日加入RxBus边于控件直接的联系
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化rxBus
        initRxBus(setOnNext());

    }

    //在12月9日之前加入权限管理的验证
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        XPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //在12月13日之前加入rxBus便于Activity之间传值
    protected abstract void doOnNext(Object o);

    private Action1<Object> setOnNext() {
        return new Action1<Object>() {

            @Override
            public void call(Object o) {
                doOnNext(o);
            }
        };
    }

    private void initRxBus(final Action1<Object> onNext) {
        if (onNext != null) {
            subscription = RxBus.toObserverable()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext);
        }
    }

    private void unbindRxBus() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindRxBus();
    }
}
