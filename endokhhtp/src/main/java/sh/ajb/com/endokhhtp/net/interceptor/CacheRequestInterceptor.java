package sh.ajb.com.endokhhtp.net.interceptor;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sh.ajb.com.endokhhtp.net.Util.NetConfigure;
import sh.ajb.com.endokhhtp.net.Util.NetUtil;

/**
 * Created by HLIU40 on 2016/11/30.
 */

public class CacheRequestInterceptor implements Interceptor {
    private Context context;

    public CacheRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //根据有网与否，判断请求是走缓存还是走网络
        //通过CacheControl两个常量控制，决定请求是走缓存(FORCE_CACHE)，还是走网络(FORCE_NETWORK)
        Log.d(NetConfigure.NET_LOG_TAG, "CacheRequest拦截:");
        Request request = chain.request();
        if (!NetUtil.isNetworkAvailable(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.d(NetConfigure.NET_LOG_TAG, "CacheRequest拦截:" + "当前无网络......从缓存获取信息");
        }
        Response response = chain.proceed(request);
        return response;
    }
}
