package sh.ajb.com.enddialog;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by liuwen on 2016/11/25.
 */
public class Builder {

    private Context context;
    private View mLayout;
    private TextView sureButton;
    private TextView cancelButton;
    private boolean mCancelable;
    private View.OnClickListener sureClickListener, cancelClickListener;
    Dialog dialog;


    public Builder(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mLayout = inflater.inflate(R.layout.base_dialog, null);

    }

    //能否返回键取消
    public Builder setCancelable(Boolean boolean1) {
        this.mCancelable = boolean1;
        return this;
    }

    public Builder title(int title) {
        ((TextView) mLayout.findViewById(R.id.title)).setText(title);
        return this;
    }

    public Builder title(String title) {
        ((TextView) mLayout.findViewById(R.id.title)).setText(title);
        return this;
    }

    public Builder titleColor(int color) {
        ((TextView) mLayout.findViewById(R.id.title)).setTextColor(context.getResources().getColor(color));

        return this;
    }

    public Builder titleBackColor(int color) {
        mLayout.findViewById(R.id.title_back).setBackgroundColor(context.getResources().getColor(color));
        return this;
    }

    public Builder setSureOnClickListener(View.OnClickListener listener) {
        this.sureClickListener = listener;
        return this;
    }

    public Builder message(String message) {
        ((TextView) mLayout.findViewById(R.id.message)).setText(message);
        return this;
    }

    public Builder message(int message) {
        ((TextView) mLayout.findViewById(R.id.message)).setText(message);
        return this;
    }
    public Builder sureTextColor(int color) {
        ((TextView) mLayout.findViewById(R.id.sure)).setTextColor(context.getResources().getColor(color));
        return this;
    }

    public Builder cancelTextColor(int color) {
        ((TextView) mLayout.findViewById(R.id.cancel)).setTextColor(context.getResources().getColor(color));
        return this;
    }

    //切换内容view
    public Builder setView(View v) {
        ((LinearLayout) mLayout.findViewById(R.id.content)).removeAllViews();
        ((LinearLayout) mLayout.findViewById(R.id.content)).addView(v);
        return this;
    }
    //确定按钮文本
    public Builder SureText(String str) {
        ((TextView) mLayout.findViewById(R.id.sure)).setText(str);
        return this;
    }

    public Builder setView(View v, LinearLayout.LayoutParams params) {
        ((LinearLayout) mLayout.findViewById(R.id.content)).removeAllViews();
        ((LinearLayout) mLayout.findViewById(R.id.content)).addView(v, params);
        return this;
    }

    public Builder showCancelButton(boolean isShow) {
        if (!isShow)
            mLayout.findViewById(R.id.cancel).setVisibility(View.GONE);
        return this;
    }

    public Builder noTitle() {
        mLayout.findViewById(R.id.title_back).setVisibility(View.GONE);
        return this;
    }

    public Builder justMessageDialog() {
        sureClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
        return this;
    }

    public Dialog builder() {
        dialog = new Dialog(context, R.style.base_dialog);
        dialog.setCancelable(mCancelable);
        dialog.addContentView(mLayout, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        if (sureClickListener != null) {
            mLayout.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sureClickListener.onClick(v);
                    dialog.dismiss();
                }
            });
        }
        mLayout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelClickListener != null) {
                    cancelClickListener.onClick(v);
                }
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
