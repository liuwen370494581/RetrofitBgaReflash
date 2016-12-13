package sh.ajb.com.endokhhtp.net.Util;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by HLIU40 on 2016/11/28.
 */

public class NetUtil {
    //判断字符串是否有效
    public static boolean isValidateString(String s) {
        if (s != null && !s.equals("")) {
            return true;
        }
        return false;
    }

    //gzip压缩请求体的方法
    public static RequestBody gzip(final RequestBody requestBody) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            @Override
            public long contentLength() throws IOException {
                return -1;//无法知道压缩后数据大小
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink bufferedSink = Okio.buffer(new GzipSink(sink));
                requestBody.writeTo(bufferedSink);
                bufferedSink.close();
            }
        };
    }

    //日期的格式化输出
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        return format.format(date);
    }

    public static String dateToString(long l) {
        Date date = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        return format.format(date);
    }

    //判断当前手机 网络连接是否可用的方法
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}
