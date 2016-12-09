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

import de.greenrobot.event.EventBus;

/**
 * Created by liuwen on 2016/10/13.
 */
public class BaseActivity extends AppCompatActivity {
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
    }


    public void intent(Class clas) {
        Intent intent = new Intent(mContext, clas);
        startActivity(intent);
    }

    //在12月9日之前加入权限管理的验证
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        XPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
