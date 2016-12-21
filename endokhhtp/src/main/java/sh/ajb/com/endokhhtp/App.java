package sh.ajb.com.endokhhtp;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sh.ajb.com.endokhhtp.utils.NetUtil;

/**
 * Created by liuwen on 2016/12/19.
 * 描述：此类放一下页面所需要的数据
 */
public class App extends Application {

    private static App sInstance;
    private Engine mEngine;
    private String baseUrl = "http://7xk9dj.com1.z0.glb.clouddn.com/";
    File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");
    int cacheSize = 10 * 1024 * 1024;
    Cache cache = new Cache(httpCacheDirectory, cacheSize);

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("MainActivity", "okhttp====" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response proceed = chain.proceed(request);
                        return proceed;
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(10, TimeUnit.SECONDS) //超时时间为10s
                .cache(cache)
                .build();

        mEngine = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build().create(Engine.class);
    }

    public static App getInstance() {
        return sInstance;
    }

    public Engine getEngine() {
        return mEngine;
    }


    //缓存拦截器，统一缓存60s
    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Response response = chain.proceed(request);

            if (NetUtil.getNetworkIsConnected(App.getInstance())) {
                // String cacheControl = request.cacheControl().toString();
                int maxAge = 60 * 60 * 24 * 2;//缓存失效时间，单位为秒
                return response.newBuilder()
                        .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        //.header("Cache-Control", cacheControl)
                        .build();
            }
            return response;
        }
    };
}