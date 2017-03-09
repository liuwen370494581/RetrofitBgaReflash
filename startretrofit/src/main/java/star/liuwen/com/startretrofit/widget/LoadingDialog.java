package star.liuwen.com.startretrofit.widget;

import android.app.Dialog;
import android.content.Context;


/**
 * 自定义透明的dialog
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}