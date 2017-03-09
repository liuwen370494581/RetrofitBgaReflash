package star.liuwen.com.startretrofit.RxjavaUtils.interceptor;



import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import star.liuwen.com.startretrofit.RxjavaUtils.Base.BaseApplication;
import star.liuwen.com.startretrofit.RxjavaUtils.utils.AppUtils;
import star.liuwen.com.startretrofit.RxjavaUtils.utils.SPUtils;

/**
 * Created by allen on 2016/12/20.
 * <p>
 * okHttp请求拦截器
 * 统一添加请求头
 */

public class OkHttpRequestInterceptor implements Interceptor {

    private Map<String, Object> headerMaps = new TreeMap<>();

    public OkHttpRequestInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    public OkHttpRequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (headerMaps == null || headerMaps.size() == 0) {
            request
                    .addHeader("Authorization", getToken())
                    .addHeader("Content-type", "application/json")
                    .addHeader("Version", getAppVersion())
                    .addHeader("Terminal", 0 + "")
                    .addHeader("X-Request-Id", getUUID())
                    .addHeader("User-Agent", System.getProperty("http.agent"));
        } else {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }

        return chain.proceed(request.build());
    }

    private String getUUID() {
        return AppUtils.getUUID();
    }

    private String getAppVersion() {
        return AppUtils.getAppVersion();
    }

    private String getToken() {
        return (String) SPUtils.get(BaseApplication.getContext(), "token", "");
    }


}
