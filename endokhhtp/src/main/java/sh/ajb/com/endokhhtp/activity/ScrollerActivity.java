package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.utils.V;

/**
 * Created by liuwen on 2016/12/22.
 * viewStub的显示
 */
public class ScrollerActivity extends BaseActivity {
    private ViewStub mViewStub;
    private Switch mSwitch;
    private boolean flag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroller_activity);
        initView();
        initData();
    }

    private void initView() {
        mViewStub = (ViewStub) findViewById(R.id.stub_import);
        mSwitch = (Switch) findViewById(R.id.switch1);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!flag) {
                        mViewStub.inflate();
                        flag = true;
                    } else {
                        mViewStub.setVisibility(View.VISIBLE);
                    }
                } else {
                    mViewStub.setVisibility(View.GONE);
                }
            }
        });

    }

    public void initData() {

    }


}
