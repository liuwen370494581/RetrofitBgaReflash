package sh.ajb.com.endokhhtp.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sh.ajb.com.endokhhtp.net.Util.NetUtil;

/**
 * Created by HLIU40 on 2016/11/29.
 */

/**
 * 拦截器压缩http请求体，但注意：许多服务器无法解析
 */
public class GzipRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest);
        }
        Request compressedRequest = originalRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(originalRequest.method(), NetUtil.gzip(originalRequest.body()))
                .build();
        return chain.proceed(compressedRequest);
    }
}
