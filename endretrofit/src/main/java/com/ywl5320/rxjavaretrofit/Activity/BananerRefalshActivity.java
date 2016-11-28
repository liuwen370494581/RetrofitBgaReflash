package com.ywl5320.rxjavaretrofit.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ywl5320.rxjavaretrofit.Adapter.RecyclerViewAdapter;
import com.ywl5320.rxjavaretrofit.BaseActivity.BaseActivity;
import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.View.DefineBAGRefreshWithLoadView;
import com.ywl5320.rxjavaretrofit.dialog.LoadDialog;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.serviceapi.UserApi;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.HttpSubscriber;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.SubscriberOnListener;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by liuwen on 2016/11/28.
 */
public class BananerRefalshActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout mBGARefreshLayout;//刷新控件
    private RecyclerView mRecyclerView;
    private BGABanner mBanner;//广告头
    private ContentAdapter mContentAdapter;//适配器
    private Context mContext;


    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    public LoadDialog loadDialog;
    private int page = 0;// 表示第一页 模拟
    private int totalPage = 2;// 表示下拉刷新的总页面是3


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bananer_refalsh_activity);
        mContext = this;
        initView();
        setBgaRefreshLayout();
        initBananer();
        getDateFromService();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.define_bga_refresh_with_load_recycler);
        //得到控件
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.define_bga_refresh_with_load);
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
    }


    /**
     * 顶部导航栏初始化
     */
    private void initBananer() {
        mContentAdapter = new ContentAdapter(mRecyclerView);
        View headerView = View.inflate(BananerRefalshActivity.this, R.layout.layout_header, null);
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
                Toast.makeText(mContext, "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });

        mContentAdapter.addHeaderView(headerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mContentAdapter.getHeaderAndFooterAdapter());
        mContentAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(mContext, "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 设置 BGARefreshLayout刷新和加载
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(mContext, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
    }

    private void getDateFromService() {
        showLoadDialog("加载中");
        UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
            @Override
            public void onSucceed(Object data) {
                mContentAdapter.setData((List<wuLiuInfo>) data);
                mContentAdapter.notifyDataSetChanged();
                hideLoadDialog();
                // Toast.makeText(NormalRecyclerActivity.this, mDataList.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(BananerRefalshActivity.this, msg, Toast.LENGTH_SHORT).show();
                hideLoadDialog();
            }
        }, BananerRefalshActivity.this));
    }


    /**
     * 模拟请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mContentAdapter.clear();
                    setData();
                    mBGARefreshLayout.endRefreshing();
                    break;
                case 1:
                    setData();
                    mBGARefreshLayout.endLoadingMore();
                    break;
                case 2:
                    mBGARefreshLayout.endLoadingMore();
                    break;
                default:
                    break;

            }
        }
    };

    private void setData() {
        getDateFromService();
        if (page > 0) {
            mContentAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("物流信息获取中");
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        page = 0;
        handler.sendEmptyMessage(0);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (page == totalPage) {
            /** 设置文字 **/
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多数据");
            /** 隐藏图片 **/
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();

            handler.sendEmptyMessage(2);
            return true;
        }
        page++;
        handler.sendEmptyMessage(1);
        return true;
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

    public void showLoadDialog(String msg) {
        if (loadDialog == null) {
            loadDialog = new LoadDialog(this);
        }
        loadDialog.show();
        loadDialog.setLoadMsg(msg);
    }

    public void hideLoadDialog() {
        if (loadDialog != null)
            loadDialog.dismiss();
    }
}
