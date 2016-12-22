package sh.ajb.com.endokhhtp.utils;

import android.app.Activity;
import android.view.View;

/**
 * Created by liuwen on 2016/12/22.
 */
public class V {

    public static <T extends View> T f(Activity context, int id) {
        return (T) context.findViewById(id);
    }


    public static <T extends View> T f(View rootView, int id) {
        return (T) rootView.findViewById(id);
    }
}
