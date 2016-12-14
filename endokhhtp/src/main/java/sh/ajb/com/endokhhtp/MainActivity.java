package sh.ajb.com.endokhhtp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.activity.ImageCacheActivity;
import sh.ajb.com.endokhhtp.activity.RetrofitActivity;
import sh.ajb.com.endokhhtp.net.MyAppClient;
import sh.ajb.com.endokhhtp.net.Util.NetConfigure;

public class MainActivity extends BaseActivity {


    private int count = 0;
    private TextView textView;
    private OkHttpClient client;
    private Request request;
    private CacheControl cacheControl;
    public static int position = 1;


    //12月14日加入notification的一些基本的使用
    NotificationCompat.Builder mBuilder;
    NotificationManager notificationManager;
    int notifyId = 100;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            textView.setText(msg.what + "\n" + msg.obj.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取通知栏的系统服务
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        textView = (TextView) findViewById(R.id.show);
        client = MyAppClient.getInstance(MainActivity.this).getOkHttpClient();
        //CacheControl配置
        cacheControl = new CacheControl.Builder().maxAge(NetConfigure.MAX_AGE, TimeUnit.SECONDS).build();
        request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .header("Cache-Control", "public,max-age=" + NetConfigure.MAX_AGE)
                .build();

    }


    public void toOkhttps(View view) {
        count = 0;
        Log.d(NetConfigure.NET_LOG_TAG, "你点击了！");
        Log.d(NetConfigure.NET_LOG_TAG, "开始发送请求1：");
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d(NetConfigure.NET_LOG_TAG, "出错了" + e.toString());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d(NetConfigure.NET_LOG_TAG, "Response response:          " + response);
                Log.d(NetConfigure.NET_LOG_TAG, "Response cache response:    " + response.cacheResponse());
                Log.d(NetConfigure.NET_LOG_TAG, "Response network response:  " + response.networkResponse());
                Log.d(NetConfigure.NET_LOG_TAG, "==============================================================");
                Log.d(NetConfigure.NET_LOG_TAG, "开始发送请求2：");
                Message message = new Message();
                message.what = 0;
                message.obj = response.body().string();
                handler.sendMessageDelayed(message, 1000);

            }
        });
    }

    public void toRetrofit(View view) {
        startActivity(new Intent(this, RetrofitActivity.class));
    }

    public void toCache(View view) {
        startActivity(new Intent(this, ImageCacheActivity.class));
    }


    public void toDialog(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this).
                setTitle("移动侦测灵敏度").setIcon(R.mipmap.default_ptr_wodfan_frame1).
                setSingleChoiceItems(R.array.you, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "ddd" + which, Toast.LENGTH_SHORT).show();
                        position = which;
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
    }


    public void toNotification(View view) {
        Intent intent = new Intent(this, ImageCacheActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")
//                .setContentText("测试内容")
                .setContentIntent(pendingIntent)
                 .setTicker("年后梦454541321决定开发大控件科技大厦功能麻烦您咖啡馆")
                .setSmallIcon(R.mipmap.default_ptr_wodfan_frame1)
                .setAutoCancel(true);
        notificationManager.notify(notifyId, mBuilder.build());


    }

    public void toDesignNotification(View view) {

    }
}
