package com.ywl5320.rxjavaretrofit.httpservice.service;


import android.util.Log;

import com.ywl5320.rxjavaretrofit.MyApplication;
import com.ywl5320.rxjavaretrofit.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuwen on 2016/10/23.
 */
public class HttpMethod {
    //http://www.kuaidi100.com/query?type=zhongtong&postid=418271182599
    public static final String BASE_URL = "http://www.kuaidi100.com/";
    public static String token = "";//请求时用户的Token
    private static Retrofit retrofit;

    //构造方法私有
    private HttpMethod() {
        retrofit = new Retrofit.Builder()
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static <T> T createApi(Class<T> clazz) {

        return retrofit.create(clazz);
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethod INSTANCE = new HttpMethod();
    }

    //获取单例
    public static HttpMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static OkHttpClient genericClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("MainActivity", "okhttp====" + message);
            }
        });
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置缓存路径
        File httpCacheDirectory = new File(MyApplication.getInstance().getExternalCacheDir().getAbsolutePath(), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 50 * 1024 * 1024);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("token", token)//添加token
                                .build();

                        if (!NetUtil.getNetworkIsConnected(MyApplication.getInstance())) {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                        }

                        Response response = chain.proceed(request);
                        if (NetUtil.getNetworkIsConnected(MyApplication.getInstance())) {
                            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                            response.newBuilder()
                                    .addHeader("Cache-Control", "public, max-age=" + maxAge)
                                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                    .build();
                        } else {
                            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                            response.newBuilder()
                                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .removeHeader("Pragma")
                                    .build();
                        }
                        return response;
                    }

                }).addInterceptor(logging)
                .cache(cache) //绑定缓存
                .connectTimeout(10, TimeUnit.SECONDS) //连接超时
                .readTimeout(10, TimeUnit.SECONDS) //读写超时
                .writeTimeout(10, TimeUnit.SECONDS).build();//写入超时
        return httpClient;
    }

}
