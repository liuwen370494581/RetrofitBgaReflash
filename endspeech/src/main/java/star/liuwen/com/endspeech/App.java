package star.liuwen.com.endspeech;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by liuwen on 2017/3/7.
 */
public class App extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=58be4439");
    }
}
