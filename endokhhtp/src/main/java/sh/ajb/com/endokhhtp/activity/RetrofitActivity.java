package sh.ajb.com.endokhhtp.activity;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sh.ajb.com.endokhhtp.App;
import sh.ajb.com.endokhhtp.Engine;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.View.DefineBAGRefreshWithLoadView;
import sh.ajb.com.endokhhtp.dialog.LoadDialog;
import sh.ajb.com.endokhhtp.model.BannerModel;
import sh.ajb.com.endokhhtp.model.RefreshModel;
import sh.ajb.com.endokhhtp.model.StaggeredModel;

public class RetrofitActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private RecyclerView mContentRv;
    private BGABanner mBanner;
    private ContentAdapter mContentAdapter;
    private Engine mEngine;
    public LoadDialog loadDialog;

    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;//刷新控件
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        setTitle("RecyclerViewDemo");
        mEngine = App.getInstance().getEngine();
        initRecyclerView();
        initView();
        loadBannerData();
        loadContentData();

    }

    private void initView() {
        //得到控件
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.define_bga_refresh);
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);

        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(RetrofitActivity.this, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mContentAdapter = new ContentAdapter(mContentRv);

        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.layout_header, null);
        mBanner = (BGABanner) headerView.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.drawable.holder).error(R.drawable.holder).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });
        mBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                Toast.makeText(App.getInstance(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });

        mContentAdapter.addHeaderView(headerView);

        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(mContentAdapter.getHeaderAndFooterAdapter());
    }

    /**
     * 加载头部广告条的数据
     */
    private void loadBannerData() {
        mEngine.fetchItemsWithItemCount("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/5item.json").enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mBanner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "加载广告条数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载内容列表数据
     */
    private void loadContentData() {
        showLoadDialog("请稍等");
        mEngine.loadContentData("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/defaultdata.json").enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
                mContentAdapter.setData(response.body());
                hideLoadDialog();
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                hideLoadDialog();
                Toast.makeText(App.getInstance(), "加载内容数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class ContentAdapter extends BGARecyclerViewAdapter<RefreshModel> {

        public ContentAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_normal);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, RefreshModel model) {
            helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);
        }
    }

    //刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 4) {
            mBGARefreshLayout.endRefreshing();
            Toast.makeText(this, "没有最新的数据了", Toast.LENGTH_LONG).show();
            return;
        }
        mEngine.loadNewData(mNewPageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
                mBGARefreshLayout.endRefreshing();
                mContentAdapter.addNewData(response.body());
                mContentRv.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                mBGARefreshLayout.endRefreshing();
            }
        });

    }


    //加载更多
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 4) {
            mBGARefreshLayout.endLoadingMore();
            Toast.makeText(this, "没有更多数据了", Toast.LENGTH_LONG).show();
            return false;
        }
        mEngine.loadMoreData(mMorePageNumber).enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
                mBGARefreshLayout.endLoadingMore();
                mContentAdapter.addMoreData(response.body());
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                mBGARefreshLayout.endLoadingMore();
            }
        });
        return true;
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
