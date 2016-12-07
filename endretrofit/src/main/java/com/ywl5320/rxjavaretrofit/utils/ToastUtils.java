package com.ywl5320.rxjavaretrofit.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liuwen on 2016/12/7.
 */
public class ToastUtils {
    private static Toast toast;

    //不严谨 会导致内存泄漏
    public static void showToast(Context context, String title) {
        if (toast == null) {
            toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        } else {
            toast.setText(title);
        }
        toast.show();
    }
}
