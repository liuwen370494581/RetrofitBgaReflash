package sh.ajb.com.endokhhtp.net.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sh.ajb.com.endokhhtp.net.Util.NetConfigure;
import sh.ajb.com.endokhhtp.net.Util.NetUtil;

/**
 * Created by HLIU40 on 2016/11/28.
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //打印请求头信息
        Request request = chain.request();
        String request_log = "Requset:  Time:" + NetUtil.dateToString(System.currentTimeMillis()) + "\t"
                + "URL:" + request.url().toString() + "\t"
                + "Method:" + request.method() + "\n"
                + "Tag:" + request.tag()
                + "\n";
        Headers headers = request.headers();
        for (String name : headers.names()) {
            request_log += name + ":" + headers.get(name) + "\n";
        }
        Log.d(NetConfigure.NET_LOG_TAG, "Log拦截：请求头：\n" + request_log);
        //打印回应头等信息
        Response response = chain.proceed(request);
        String response_log = "Response:  Time:" + NetUtil.dateToString(System.currentTimeMillis()) + "\n";
        Headers response_headers = response.headers();
        for (String name : response_headers.names()) {
            response_log += name + ":" + response_headers.get(name) + "\n";
        }
        Log.d(NetConfigure.NET_LOG_TAG, "Log拦截：响应头：\n" + response_log);
        return response;
    }
}
