package com.ywl5320.rxjavaretrofit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ywl5320.rxjavaretrofit.R;

/**
 * Created by liuwen on 2016/11/17
 */
public class LoadDialog extends Dialog{

    public Context context;
    private TextView mtvLoadmsg;
    private ImageView mImageView;

    public LoadDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        mtvLoadmsg = (TextView) findViewById(R.id.tv_load_msg);
        mImageView=(ImageView)findViewById(R.id.img_show);
        mImageView.setBackgroundResource(R.drawable.loading_animal);
        AnimationDrawable animationDrawable= (AnimationDrawable) mImageView.getBackground();
        animationDrawable.start();

    }

    public void setLoadMsg(String msg)
    {
        if(mtvLoadmsg != null)
        {
            mtvLoadmsg.setText(msg);
        }
    }
}
