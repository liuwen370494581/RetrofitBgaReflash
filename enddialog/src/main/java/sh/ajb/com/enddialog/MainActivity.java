package sh.ajb.com.enddialog;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import sh.ajb.com.enddialog.Base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void initView() {

    }

    @Override
    public int activityLayousRes() {
        return R.layout.activity_main;
    }

    public void Dialog(View view) {
        new Builder(this).title("300速贷").message("联系车300,业务员立即上门办理贷款业务").setCancelable(true).SureText("拨号").sureTextColor(R.color.colorAccent).
                setSureOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).builder().show();

    }
}
