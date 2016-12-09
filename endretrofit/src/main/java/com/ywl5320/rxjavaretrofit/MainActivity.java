package com.ywl5320.rxjavaretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ywl5320.rxjavaretrofit.Activity.BananerRefalshActivity;
import com.ywl5320.rxjavaretrofit.Activity.DefineLoadWithRefreshActivity;
import com.ywl5320.rxjavaretrofit.Activity.ManagerPermissionActivity;
import com.ywl5320.rxjavaretrofit.Activity.NormalRecyclerActivity;
import com.ywl5320.rxjavaretrofit.dialog.LoadDialog;
import com.ywl5320.rxjavaretrofit.httpservice.beans.UserBean;
import com.ywl5320.rxjavaretrofit.httpservice.beans.WeatherBean;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.serviceapi.UserApi;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.HttpSubscriber;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.SubscriberOnListener;
import com.ywl5320.rxjavaretrofit.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnAndroidEvent;
    public LoadDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnAndroidEvent = (Button) findViewById(R.id.btn_android_event);


        //安卓的的事件分发机制
        /**
         * 可以先确定的是setOnTouch事件优先于setOnClick
         * 当setonTouch事件的返回值为true时 setOnClick便不会再执行了
         * 看下面的源码即可得知 当OnTouchListener的监听事件不为空和当次
         * 按钮为点击状态时和onTouch事件的返回值 都为true的情况 就直接返回
         * true。只要一个条件为false的情况下 都会去执行onTouchEvent方法
         *
         */
//        public boolean dispatchTouchEvent(MotionEvent event) {
//            if (mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED &&
//                    mOnTouchListener.onTouch(this, event)) {
//                return true;
//            }
//            return onTouchEvent(event);
//        }


        btnAndroidEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "onClick" + "点击了");
                ToastUtils.showToast(MainActivity.this, "onClick");
            }
        });
        btnAndroidEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("MainActivity", "OnTouch点击了" + event.getAction());
                ToastUtils.showToast(MainActivity.this, "onTouch" + event.getAction());
                return true;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoadDialog("正在获取快递物流信息");


                UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
                    @Override
                    public void onSucceed(Object data) {
                        hideLoadDialog();
                        ToastUtils.showToast(MainActivity.this, data.toString());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showToast(MainActivity.this, "获取内容失败");
                        hideLoadDialog();

                    }
                }, MainActivity.this));

//                UserApi.getInstance().getWeather("成都", "json", "5NImrCXDE8hR05Yc49Bgs5QG", new HttpSubscriber<WeatherBean>(new SubscriberOnListener<WeatherBean>() {
//                    @Override
//                    public void onSucceed(WeatherBean data) {
//                        hideLoadDialog();//这个可以在回调里面添加到complete里面，关闭对话框只写到这个里面就可以了
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        Toast.makeText(MainActivity.this, "status:" + code + "," + msg, Toast.LENGTH_LONG).show();
//                        hideLoadDialog();
//                    }
//                }, MainActivity.this));
            }
        });

    }

    public void toReflash(View view) {
        startActivity(new Intent(MainActivity.this, DefineLoadWithRefreshActivity.class));
    }

    public void toRecyclerView(View view) {
        startActivity(new Intent(MainActivity.this, NormalRecyclerActivity.class));
    }

    public void toBananerReflash(View view) {
        startActivity(new Intent(MainActivity.this, BananerRefalshActivity.class));
    }

    public void toPermissionManager(View view) {
        startActivity(new Intent(MainActivity.this, ManagerPermissionActivity.class));
    }


    public void showLoadDialog(String msg) {
        if (loadDialog == null) {
            loadDialog = new LoadDialog(this);
        }
        loadDialog.show();
        loadDialog.setLoadMsg(msg);
    }

    public void hideLoadDialog() {
        if (loadDialog != null)
            loadDialog.dismiss();
    }
}
