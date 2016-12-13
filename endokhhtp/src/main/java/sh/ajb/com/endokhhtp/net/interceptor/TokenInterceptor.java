package sh.ajb.com.endokhhtp.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HLIU40 on 2016/11/29.
 */

public class TokenInterceptor implements Interceptor {
    private final String TOKEN_HEADER_NAME = "Authorization";
    private String token_header_value;

    public TokenInterceptor(String token_header_value) {
        this.token_header_value = token_header_value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (token_header_value == null || request.header(TOKEN_HEADER_NAME) != null) {
            return chain.proceed(request);
        }
        Response response = chain.proceed(request.newBuilder()
                .header(TOKEN_HEADER_NAME, token_header_value)
                .build());
        return response;
    }
}
