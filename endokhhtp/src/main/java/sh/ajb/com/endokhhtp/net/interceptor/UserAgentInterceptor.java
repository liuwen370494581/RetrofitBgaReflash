package sh.ajb.com.endokhhtp.net.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sh.ajb.com.endokhhtp.net.Util.NetConfigure;

/**
 * Created by HLIU40 on 2016/11/29.
 */

public class UserAgentInterceptor implements Interceptor {
    private final String USER_AGENT_HEADER_NAME = "User-Agent";
    private String user_agent_value;

    public UserAgentInterceptor(String user_agent_value) {
        this.user_agent_value = user_agent_value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(NetConfigure.NET_LOG_TAG, "UserAgent拦截:");
        Request request = chain.request();
        Response response = chain.proceed(
                request.newBuilder()
                        .removeHeader(USER_AGENT_HEADER_NAME)
                        .addHeader(USER_AGENT_HEADER_NAME, user_agent_value).build());
        return response;
    }
}
