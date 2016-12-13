package sh.ajb.com.endokhhtp.net.Util;

/**
 * Created by Hliu40 on 2016/12/1.
 */

public class NetConfigure {
    //可用来存放app版本号等信息
    public static final String APP_VERSION = "7.1.9";
    //自定义app标识
    public static final String APP_KEY = "";
    //打印网络信息的key
    public static final String NET_LOG_TAG = "NET_OKHTTP";

    //有网/无网络状态下，缓存的有效时间(单位为：秒)
    public static final int MAX_AGE = 120;
    public static final int MAX_STALE = 60 * 60 * 24 * 14;

    //缓存文件夹名与大小
    public static final String CACHENAME = "net_cache";
    public static final long CACHESIZE = 1024 * 1024 * 10;

    //配置连接超时/读超时/写超时  时间(单位：秒)
    public static final long CONNECTION_TIMEOUT = 120;
    public static final long READ_TIMEOUT = 120;
    public static final long WRITE_TIMEOUT = 120;
}
