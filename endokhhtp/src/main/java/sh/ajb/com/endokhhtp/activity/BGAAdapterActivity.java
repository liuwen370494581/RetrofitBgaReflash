package sh.ajb.com.endokhhtp.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sh.ajb.com.endokhhtp.Adapter.NormalRecyclerViewAdapter;
import sh.ajb.com.endokhhtp.App;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildCheckedChangeListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemChildTouchListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.Engine;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.View.DefineBAGRefreshWithLoadView;
import sh.ajb.com.endokhhtp.dialog.LoadDialog;
import sh.ajb.com.endokhhtp.model.BannerModel;
import sh.ajb.com.endokhhtp.model.NormalModel;
import sh.ajb.com.endokhhtp.utils.SnackbarUtil;

/**
 * Created by liuwen on 2016/12/19.
 */
public class BGAAdapterActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener, BGAOnRVItemChildTouchListener, BGAOnItemChildCheckedChangeListener {
    private RecyclerView mContentRv;
    private BGABanner mBanner;
    private NormalRecyclerViewAdapter mContentAdapter;
    private Engine mEngine;
    public LoadDialog loadDialog;

    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;//刷新控件
    private int mNewPageNumber = 1;
    private int mMorePageNumber = 1;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_activity);

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

        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(BGAAdapterActivity.this, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mContentAdapter = new NormalRecyclerViewAdapter(mContentRv);



        // 初始化拖拽排序和滑动删除
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mContentRv);

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

        mContentAdapter.setOnRVItemClickListener(this);
        mContentAdapter.setOnRVItemLongClickListener(this);
        mContentAdapter.setOnItemChildClickListener(this);
        mContentAdapter.setOnItemChildLongClickListener(this);
        mContentAdapter.setOnItemChildCheckedChangeListener(this);
        mContentAdapter.setOnRVItemChildTouchListener(this);

    }

    private RecyclerView.LayoutManager getGridLayoutManager() {
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 || position % 2 == 0) ? 1 : layoutManager.getSpanCount();
            }
        });
        return layoutManager;
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
        mEngine.getNormalModels("http://7xk9dj.com1.z0.glb.clouddn.com/adapter/api/normalModels.json").enqueue(new Callback<List<NormalModel>>() {
            @Override
            public void onResponse(Call<List<NormalModel>> call, Response<List<NormalModel>> response) {
                mContentAdapter.setData(response.body());
                hideLoadDialog();
            }

            @Override
            public void onFailure(Call<List<NormalModel>> call, Throwable t) {
                hideLoadDialog();
                Toast.makeText(App.getInstance(), "加载内容数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mNewPageNumber++;
        if (mNewPageNumber > 1) {
            mBGARefreshLayout.endRefreshing();
            Toast.makeText(this, "没有最新的数据了", Toast.LENGTH_LONG).show();
            return;
        }
    }

    //加载更多
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        if (mMorePageNumber > 1) {
            mBGARefreshLayout.endLoadingMore();
            Toast.makeText(this, "没有更多数据了", Toast.LENGTH_LONG).show();
            return false;
        }
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

    //各种子类的监听事件
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            mContentAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            SnackbarUtil.show(childView, "长按了删除 " + mContentAdapter.getItem(position).title);
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        SnackbarUtil.show(itemView, "点击了条目 " + mContentAdapter.getItem(position).title);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        SnackbarUtil.show(itemView, "长按了条目 " + mContentAdapter.getItem(position).title);
        return true;
    }


    @Override
    public void onItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked) {
        mContentAdapter.getItem(position).selected = isChecked;
        SnackbarUtil.show(childView, (isChecked ? "选中 " : "取消选中") + mContentAdapter.getItem(position).title);
    }

    @Override
    public boolean onRvItemChildTouch(cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder holder, View childView, MotionEvent event) {
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            mItemTouchHelper.startDrag(holder);
        }
        return false;
    }

    /**
     * 该类参考：https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
     */
    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        public static final float ALPHA_FULL = 1.0f;

        @Override
        public boolean isLongPressDragEnabled() {
//            return true;
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

            // 当加了 HeaderAndFooterAdapter 时需要加上下面的判断
            if (mContentAdapter.isHeaderOrFooter(viewHolder)) {
                dragFlags = swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
            }

            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            mContentAdapter.moveItem(source, target);

            for (NormalModel normalModel : mContentAdapter.getData()) {
                Log.i("MainActivity", normalModel.title);
            }

            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mContentAdapter.removeItem(viewHolder);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
                ViewCompat.setAlpha(viewHolder.itemView, alpha);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setSelected(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            viewHolder.itemView.setSelected(false);
        }
    }
}
