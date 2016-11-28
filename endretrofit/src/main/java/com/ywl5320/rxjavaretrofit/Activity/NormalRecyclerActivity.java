package com.ywl5320.rxjavaretrofit.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ywl5320.rxjavaretrofit.Adapter.NomalRecyclerAdapter;
import com.ywl5320.rxjavaretrofit.BaseActivity.BaseActivity;
import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.serviceapi.UserApi;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.HttpSubscriber;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.SubscriberOnListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liuwen on 2016/11/25.
 */
public class NormalRecyclerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<wuLiuInfo> mDataList;
    private NomalRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycler_activity);

        getDateFromService();
        initView();
    }

    private void getDateFromService() {
        UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
            @Override
            public void onSucceed(Object data) {
                mDataList.addAll((Collection<? extends wuLiuInfo>) data);
                mAdapter.notifyDataSetChanged();
                // Toast.makeText(NormalRecyclerActivity.this, mDataList.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(NormalRecyclerActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, NormalRecyclerActivity.this));
    }

    private void initView() {
        mDataList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rev_normal_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(NormalRecyclerActivity.this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new NomalRecyclerAdapter(mDataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
