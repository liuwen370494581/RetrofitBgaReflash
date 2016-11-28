package com.ywl5320.rxjavaretrofit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.subscribers.SubscriberOnListener;

import java.util.List;

/**
 * Created by liuwen on 2016/11/25.
 */
public class NomalRecyclerAdapter extends RecyclerView.Adapter<NomalRecyclerAdapter.ViewHolder> {

    private List<wuLiuInfo> mDataList;
    private Context context;
    private LayoutInflater inflater;

    public NomalRecyclerAdapter(List<wuLiuInfo> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        wuLiuInfo wuLiuInfo = mDataList.get(position);
        holder.mTime.setText(wuLiuInfo.getTime());
        holder.mFtime.setText(wuLiuInfo.getFtime());
        holder.mContext.setText(wuLiuInfo.getContext());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTime, mFtime, mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.item_time);
            mFtime = (TextView) itemView.findViewById(R.id.item_ftime);
            mContext = (TextView) itemView.findViewById(R.id.item_context);
        }
    }

}
