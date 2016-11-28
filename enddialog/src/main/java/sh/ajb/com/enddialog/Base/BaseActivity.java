package sh.ajb.com.enddialog.Base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import de.greenrobot.event.EventBus;
import sh.ajb.com.enddialog.Builder;
import sh.ajb.com.enddialog.Event.NetEvent;
import sh.ajb.com.enddialog.R;
import sh.ajb.com.enddialog.network.NetUtils;
import sh.ajb.com.enddialog.receiver.NetReceiver;

/**
 * Created by liuwen on 2016/11/25.
 */
public abstract class BaseActivity extends AppCompatActivity {


    private NetReceiver mReceiver;
    private RelativeLayout no_network_rl;
    private Context mContext;
    private Builder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activityLayousRes());
        no_network_rl = (RelativeLayout) findViewById(R.id.net_view_rl);
        mContext = this;
        EventBus.getDefault().register(this);
        mBuilder = new Builder(this);
        initRecevier();
        initView();
    }

    private void initDate(Boolean b) {
        if (b.equals(false)) {
            mBuilder.noTitle().message("你的网络已经断了，是否去重新连接网络呢").SureText("设置").sureTextColor(R.color.blue).setCancelable(true).setSureOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetUtils.startToSettings(mContext);
                }
            }).builder().show();
        } else {
            mBuilder.builder().cancel();
        }

    }

    protected abstract void initView();

    public abstract int activityLayousRes();


    private void initRecevier() {
        mReceiver = new NetReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    public void onEventMainThread(NetEvent event) {
        setNetState(event.isNet());
    }

    public void setNetState(boolean netState) {

        if (no_network_rl != null) {
            no_network_rl.setVisibility(netState ? View.GONE : View.VISIBLE);
            no_network_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    NetUtils.startToSettings(mContext);
                }
            });
            initDate(netState);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(toString());
        unregisterReceiver(mReceiver);
    }
}
