package sh.ajb.com.endokhhtp.net;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import sh.ajb.com.endokhhtp.net.Util.NetConfigure;
import sh.ajb.com.endokhhtp.net.interceptor.CacheRequestInterceptor;
import sh.ajb.com.endokhhtp.net.interceptor.CacheResponseInterceptor;
import sh.ajb.com.endokhhtp.net.interceptor.LoggingInterceptor;
import sh.ajb.com.endokhhtp.net.interceptor.UserAgentInterceptor;

/**
 * Created by Hliu40 on 2016/12/1.
 */

//住：此处，如果服务器有Token的处理，又可能导致从缓存中获取数据失败的问题。。

public class MyAppClient {
    private static MyAppClient myAppClient;
    private Context context;
    private OkHttpClient okHttpClient;
    private Cache cache;
    private String url;
    private String cache_control;
    private String token;


    private MyAppClient(Context context) {
        this.context = context;
        //为okhttp配置缓存位置
        File cachefile = new File(Environment.getExternalStorageDirectory(), NetConfigure.CACHENAME);
        cache = new Cache(cachefile, NetConfigure.CACHESIZE);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(NetConfigure.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetConfigure.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NetConfigure.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new UserAgentInterceptor(NetConfigure.APP_VERSION))
                .addInterceptor(new CacheRequestInterceptor(context))
                .addNetworkInterceptor(new CacheResponseInterceptor(context))
                .addNetworkInterceptor(new LoggingInterceptor())
                .cache(cache)
                .build();
    }

    public static MyAppClient getInstance(Context context) {
        if (myAppClient == null) {
            myAppClient = new MyAppClient(context);
        }
        return myAppClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
