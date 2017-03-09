package star.liuwen.com.startretrofit.RxjavaUtils.http;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import star.liuwen.com.startretrofit.RxjavaUtils.interceptor.CacheInterceptor;
import star.liuwen.com.startretrofit.RxjavaUtils.interceptor.OkHttpRequestInterceptor;

/**
 * Created by allen on 2016/12/20.
 * <p/>
 * Retrofit请求初始化工具类
 */

public class RetrofitClient {

    private static Retrofit.Builder retrofitBuilder;

    /**
     * 自己服务的地址
     */
    private static String baseUrl = "http://120.25.102.84:9001/";

    private static HttpLoggingInterceptor loggingInterceptor;

    private static OkHttpRequestInterceptor requestInterceptor;

    private static CacheInterceptor cacheInterceptor;
    private static Cache cache;

    /**
     * 无参数  实例化
     *
     * @return retrofitBuilder
     */
    public static Retrofit getInstance(Map<String, Object> headerMaps) {

        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("MainActivity", "okhttp====" + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            cacheInterceptor = new CacheInterceptor();

        }
        if (headerMaps != null) {
            requestInterceptor = new OkHttpRequestInterceptor(headerMaps);
        } else {
            requestInterceptor = new OkHttpRequestInterceptor();
        }

        retrofitBuilder.baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(getClient());

        return retrofitBuilder.build();
    }

    /**
     * 有参数实例化
     *
     * @param baseUrl 传入baseUrl
     * @return retrofitBuilder
     */
    public static Retrofit getInstance(String baseUrl, Map<String, Object> headerMaps) {

        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        if (headerMaps != null) {
            requestInterceptor = new OkHttpRequestInterceptor(headerMaps);
        } else {
            requestInterceptor = new OkHttpRequestInterceptor();
        }
        retrofitBuilder.baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(getClient());

        return retrofitBuilder.build();
    }


    /**
     * 初始化okHttpClient
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        if (cache == null) {
            //程序的包名 以后可命名至于
            cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath() + "/rxHttpCacheData"), 1024 * 1024 * 100);
        }

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor) //增加缓存
                .addNetworkInterceptor(cacheInterceptor) //增加缓存
                .cache(cache) //增加缓存
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }
}
