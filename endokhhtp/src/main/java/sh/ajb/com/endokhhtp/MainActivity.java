package sh.ajb.com.endokhhtp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


}
