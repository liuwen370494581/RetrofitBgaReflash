package sh.ajb.com.endokhhtp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import sh.ajb.com.endokhhtp.R;

/**
 * Created by ywl on 2016/3/9.
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
