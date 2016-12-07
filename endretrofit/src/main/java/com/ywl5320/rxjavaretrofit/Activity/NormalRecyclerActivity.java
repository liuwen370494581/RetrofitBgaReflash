package com.ywl5320.rxjavaretrofit.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ywl5320.rxjavaretrofit.BaseActivity.BaseActivity;
import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.serviceapi.UserApi;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.HttpSubscriber;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.SubscriberOnListener;
import com.ywl5320.rxjavaretrofit.utils.ToastUtils;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by liuwen on 2016/11/25.
 */
public class NormalRecyclerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private BGABanner mBanner;
    private ContentAdapter mContentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycler_activity);
        initView();
        initBananer();
        getDateFromService();
    }

    /**
     * 顶部导航栏初始化
     */
    private void initBananer() {
        mContentAdapter = new ContentAdapter(mRecyclerView);
        View headerView = View.inflate(NormalRecyclerActivity.this, R.layout.layout_header, null);
        mBanner = (BGABanner) headerView.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.mipmap.holder).error(R.mipmap.holder).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });
        mBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                ToastUtils.showToast(NormalRecyclerActivity.this, "点击了第" + (position + 1) + "页");
            }
        });

        mContentAdapter.addHeaderView(headerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mContentAdapter.getHeaderAndFooterAdapter());
        mContentAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                ToastUtils.showToast(NormalRecyclerActivity.this, "点击了第" + (position + 1) + "页");
            }
        });
        mContentAdapter.setOnRVItemLongClickListener(new BGAOnRVItemLongClickListener() {
            @Override
            public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
                return true;
            }
        });
    }

    private void getDateFromService() {
        UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
            @Override
            public void onSucceed(Object data) {
                mContentAdapter.setData((List<wuLiuInfo>) data);
                mContentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.showToast(NormalRecyclerActivity.this, "获取内容失败");
            }
        }, NormalRecyclerActivity.this));
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rev_normal_recyclerview);
    }


    private class ContentAdapter extends BGARecyclerViewAdapter<wuLiuInfo> {
        public ContentAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, wuLiuInfo model) {
            helper.setText(R.id.item_time, model.getTime())
                    .setText(R.id.item_ftime, model.getFtime())
                    .setText(R.id.item_context, model.getContext());
        }
    }
}
