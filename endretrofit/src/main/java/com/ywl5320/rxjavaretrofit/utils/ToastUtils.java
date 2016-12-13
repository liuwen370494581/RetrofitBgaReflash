package com.ywl5320.rxjavaretrofit.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ywl5320.rxjavaretrofit.R;

/**
 * Created by liuwen on 2016/12/7.
 */
public class ToastUtils {
    private static Toast mToast;

    //不严谨 会导致内存泄漏
    public static void showToast(Context context, String title) {
        if (mToast == null) {
            mToast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(title);
        }
        mToast.show();
    }


    /**
     * 居中显示的toast，无图片
     *
     * @param context    上下文对象
     * @param contentRes 提示语
     */
    public static void centerToast(Context context, int contentRes) {
        if (mToast == null) {
            mToast = Toast.makeText(context, contentRes, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(contentRes);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 居中显示的toast ，带图片
     *
     * @param context     上下文对象
     * @param content     提示语
     * @param drawableRes 显示的图片资源
     */
    public static void centerToastWithPic(Context context, String content, int drawableRes) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            LinearLayout toastView = (LinearLayout) mToast.getView();
            toastView.setBackgroundResource(R.drawable.dialog_loading_bg);
            toastView.getBackground().setAlpha(150);
            ImageView imageCodeProject = new ImageView(context);
            imageCodeProject.setImageResource(drawableRes);
            toastView.addView(imageCodeProject, 0);
            mToast.setView(toastView);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}
