package sh.ajb.com.endokhhtp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liuwen on 2017/1/20.
 */
public class SharePrefrenceUtil {
    private static final String BG_PIC_PATH ="bg_pic_path";
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;
    public SharePrefrenceUtil(Context context) {
        mSp = context.getSharedPreferences("coolWeather",
                Context.MODE_WORLD_WRITEABLE);
        mEditor = mSp.edit();
    }

    /**
     * 保存背景皮肤图片的地址
     * @author: htq
     */
    public  void saveBgPicPath(String path)
    {
        mEditor.putString(BG_PIC_PATH, path);
        mEditor.commit();
    }
    /**
     * 获取背景皮肤图片的地址
     */
    public  String getPath() {
        return mSp.getString(BG_PIC_PATH, null);
    }

}
