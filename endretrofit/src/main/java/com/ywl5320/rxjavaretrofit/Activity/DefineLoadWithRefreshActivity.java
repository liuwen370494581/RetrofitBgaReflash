package com.ywl5320.rxjavaretrofit.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ywl5320.rxjavaretrofit.Adapter.RecyclerViewAdapter;
import com.ywl5320.rxjavaretrofit.BaseActivity.BaseActivity;
import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.View.DefineBAGRefreshWithLoadView;
import com.ywl5320.rxjavaretrofit.dialog.LoadDialog;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.serviceapi.UserApi;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.HttpSubscriber;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.SubscriberOnListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by fml on 2015/12/4 0004.
 */
public class DefineLoadWithRefreshActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mBGARefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;
    /**
     * title
     */
    private TextView mTitle;

    private List<wuLiuInfo> mListData = new ArrayList<wuLiuInfo>();//模拟数据

    /**
     * 数据填充adapter
     */
    private RecyclerViewAdapter mRecyclerViewAdapter = null;
    /**
     * 设置一共请求多少次数据
     */
    private int ALLSUM = 0;
    /**
     * 设置刷新和加载
     */
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    public LoadDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_refresh_with_load);
        mContext = this;
        initView();
        setBgaRefreshLayout();
        setRecyclerView();
        getDateFromService();

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

    private void getDateFromService() {
        showLoadDialog("请稍等");
        UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
            @Override
            public void onSucceed(Object data) {
                mListData.addAll((Collection<? extends wuLiuInfo>) data);
                mRecyclerViewAdapter.notifyDataSetChanged();
                hideLoadDialog();
                // Toast.makeText(NormalRecyclerActivity.this, mDataList.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String msg) {
                hideLoadDialog();
                Toast.makeText(DefineLoadWithRefreshActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, DefineLoadWithRefreshActivity.this));
    }

//    /**
//     * 进入页面首次加载数据
//     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (mListData.size() == 0) {
//            mBGARefreshLayout.beginRefreshing();
//            onBGARefreshLayoutBeginRefreshing(mBGARefreshLayout);
//        } else {
//            mBGARefreshLayout.endRefreshing();
//        }
//    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.fml_title);
        mTitle.setText("自定义刷新和加载更多样式");
        mRecyclerView = (RecyclerView) findViewById(R.id.define_bga_refresh_with_load_recycler);
        //得到控件
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.define_bga_refresh_with_load);
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext, mListData);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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

    /**
     * 设置RecyclerView的布局方式
     */
    private void setRecyclerView() {
        //垂直listview显示方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
                    mListData.clear();
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
        if (ALLSUM == 0) {
            setRecyclerCommadapter();
        } else {
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 数据填充
     */
    private void setRecyclerCommadapter() {
        //点击事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(mContext, "onclick  " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(mContext, "onlongclick  " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 刷新
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        ALLSUM = 0;
        handler.sendEmptyMessage(0);
    }

    /**
     * 加载
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (ALLSUM == 2) {
            /** 设置文字 **/
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多数据");
            /** 隐藏图片 **/
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();
            handler.sendEmptyMessage(2);
            return true;
        }
        ALLSUM++;
        handler.sendEmptyMessage(1);
        return true;
    }
}
