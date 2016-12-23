package com.ywl5320.rxjavaretrofit.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by liuwen on 2016/12/19.
 */
public class SnackbarUtil {

    private SnackbarUtil(){

    }

    public static  void show(View view,CharSequence text){
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor("#71CE7E"));
        if (text.length() > 10) {
            snackbar.setDuration(Snackbar.LENGTH_LONG);
        }
        snackbar.show();
    }

}
