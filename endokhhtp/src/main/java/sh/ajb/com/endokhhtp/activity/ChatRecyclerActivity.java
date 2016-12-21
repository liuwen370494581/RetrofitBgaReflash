package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import sh.ajb.com.endokhhtp.Adapter.RecyclerChatAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.DataEngine;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.model.ChatModel;

/**
 * Created by liuwen on 2016/12/21.
 */
public class ChatRecyclerActivity extends BaseActivity implements BGAOnItemChildClickListener {
    private RecyclerView chatRecyclerView;
    private RecyclerChatAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_recycler_activity);
        initView();
        initData();
    }

    private void initData() {
        mAdapter = new RecyclerChatAdapter(chatRecyclerView);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setData(DataEngine.loadChatModelData());
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        chatRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerview_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {

        mAdapter.getItem(position).mSendStatus = ChatModel.SendStatus.Success;
        mAdapter.moveItem(position, mAdapter.getItemCount() - 1);
    }
}




