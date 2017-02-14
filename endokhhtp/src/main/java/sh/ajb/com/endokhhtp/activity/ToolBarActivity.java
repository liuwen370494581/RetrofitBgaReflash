package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewScrollHelper;
import sh.ajb.com.endokhhtp.BGAAdapter.BGAOnRVItemClickListener;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.DataEngine;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.model.IndexModel;
import sh.ajb.com.endokhhtp.utils.SnackbarUtil;
import sh.ajb.com.endokhhtp.widget.IndexView;

/**
 * Created by liuwen on 2017/1/16.
 */
public class ToolBarActivity extends BaseActivity {
    private RecyclerIndexAdapter mAdapter;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;
    private BGARecyclerViewScrollHelper mRecyclerViewScrollHelper;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_bar_activity);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setLogo(R.mipmap.ic_launcher);
//        mToolbar.setTitle("主播");
//        mToolbar.setSubtitle("副主编");
//
//        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerindexview);
        mAdapter = new RecyclerIndexAdapter(mDataRv);
        mIndexView = (IndexView) findViewById(R.id.indexview);
        mTipTv = (TextView) findViewById(R.id.tv_recyclerindexview_tip);

        mAdapter.setOnRVItemClickListener(new cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                SnackbarUtil.show(parent, "选择的银行卡" + mAdapter.getItem(position).name);
            }
        });


        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
                }
            }
        });
        mRecyclerViewScrollHelper = BGARecyclerViewScrollHelper.newInstance(mDataRv);

        mIndexView.setTipTv(mTipTv);

        mDataRv.addItemDecoration(BGADivider.newBitmapDivider()
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(new BGADivider.SuspensionCategoryDelegate() {
                    @Override
                    protected boolean isCategory(int position) {
                        return mAdapter.isCategory(position);
                    }

                    @Override
                    protected String getCategoryName(int position) {
                        return mAdapter.getItem(position).topc;
                    }

                    @Override
                    protected int getFirstVisibleItemPosition() {
                        return mLayoutManager.findFirstVisibleItemPosition();
                    }
                }));

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);

        mAdapter.setData(DataEngine.loadXykData());
        mDataRv.setAdapter(mAdapter);
    }


    private void initData() {

    }

    public class RecyclerIndexAdapter extends BGARecyclerViewAdapter<IndexModel> {

        public RecyclerIndexAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_index_city);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
            helper.setText(R.id.tv_item_index_city, model.name);
        }

        public boolean isCategory(int position) {
            int category = getItem(position).topc.charAt(0);
            return position == getPositionForCategory(category);
        }

            public int getPositionForCategory(int category) {
                for (int i = 0; i < getItemCount(); i++) {
                    String sortStr = getItem(i).topc;
                    char firstChar = sortStr.toUpperCase().charAt(0);
                    if (firstChar == category) {
                        return i;
                    }
                }
                return -1;
            }
    }

}
