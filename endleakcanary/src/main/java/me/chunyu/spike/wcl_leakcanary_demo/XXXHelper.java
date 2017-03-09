package me.chunyu.spike.wcl_leakcanary_demo;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by liuwen on 2017/1/21.
 */
public class XXXHelper {

    private Context mCtx;
    private TextView mTextView;

    private static XXXHelper ourInstance = null;

    public static XXXHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new XXXHelper(context);
        }
        return ourInstance;
    }

    public void setRetainedTextView(TextView tv){
        this.mTextView = tv;
        mTextView.setText(mCtx.getString(android.R.string.ok));
    }

    private XXXHelper() {
    }

    private XXXHelper(Context context) {
        this.mCtx = context;
    }

    public void removeTextView(){
        mTextView = null;
    }
}
