package com.ywl5320.rxjavaretrofit.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ywl5320.rxjavaretrofit.BaseActivity.BaseActivity;
import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.pjo.RxBusModel;
import com.ywl5320.rxjavaretrofit.utils.RxBus;

import java.util.Random;

/**
 * Created by liuwen on 2016/12/12.
 */
public class AddViewActivity extends BaseActivity {

    private LinearLayout mLl_parent;
    private TextView tvShow;
    private Button btnRxBusOnclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_view_activity);
        tvShow = (TextView) findViewById(R.id.tv_addView_rxBus);
        initView();
    }

    @Override
    protected void doOnNext(Object o) {
        if (tvShow != null) {
            Gson gson = new Gson();
            RxBusModel rxbus = gson.fromJson(o.toString(), RxBusModel.class);
            if (rxbus.getTo().equals("AddViewActivity")) {
                tvShow.setText("接收到消息-->" + rxbus.getContent());
            }
        }
    }

    private void initView() {
        mLl_parent = (LinearLayout) findViewById(R.id.ly_01);
        LayoutInflater inflater = LayoutInflater.from(AddViewActivity.this);
//        for (int i = 0; i < 3; i++) {
//
//            View headView = inflater.inflate(R.layout.layout_header, null);
//            mLl_parent.addView(headView);
//        }

        for (int j = 0; j < 3; j++) {
            View view = inflater.inflate(R.layout.fml_title, null);
            mLl_parent.addView(view);
        }

        btnRxBusOnclick = new Button(this);
        btnRxBusOnclick.setText("点击发送rxBus消息");
        mLl_parent.addView(btnRxBusOnclick);
        btnRxBusOnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                RxBusModel rxBus = new RxBusModel("AddViewActivity", "MainActivity", new Random().nextFloat() + "");
                RxBus.send(gson.toJson(rxBus));
            }
        });
    }
}
