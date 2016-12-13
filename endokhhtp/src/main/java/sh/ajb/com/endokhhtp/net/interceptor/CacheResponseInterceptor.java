package sh.ajb.com.endokhhtp.net.interceptor;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sh.ajb.com.endokhhtp.net.Util.NetConfigure;
import sh.ajb.com.endokhhtp.net.Util.NetUtil;

/**
 * Created by HLIU40 on 2016/11/29.
 */

public class CacheResponseInterceptor implements Interceptor {
    private Context context;

    public CacheResponseInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response response1;
        if (NetUtil.isNetworkAvailable(context)) {
            response1 = response.newBuilder()
                    .removeHeader("Authorization")
                    .removeHeader("Cache-Control")
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public,max-age=" + NetConfigure.MAX_AGE)
                    .build();
            Log.d(NetConfigure.NET_LOG_TAG, "CacheResponse拦截:" + "当前网络存在......缓存有效期为" + NetConfigure.MAX_AGE);
        } else {
            response1 = response.newBuilder()
                    .removeHeader("Authorization")
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + NetConfigure.MAX_STALE)
                    .build();
            Log.d(NetConfigure.NET_LOG_TAG, "CacheResponse拦截:" + "当前无网络......缓存有效期为" + NetConfigure.MAX_STALE);
        }
        //在响应拦截并修改响应头以后 打印响应头
        String response_log = "Response:  Time:" + NetUtil.dateToString(System.currentTimeMillis()) + "\n";
        Headers response_headers = response1.headers();
        for (String name : response_headers.names()) {
            response_log += name + ":" + response_headers.get(name) + "\n";
        }
        Log.d(NetConfigure.NET_LOG_TAG, "CacheResponse拦截：响应头：\n" + response_log);
        return response1;
    }
}
