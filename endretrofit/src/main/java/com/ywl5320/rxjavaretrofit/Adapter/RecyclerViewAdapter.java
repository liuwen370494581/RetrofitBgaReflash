package com.ywl5320.rxjavaretrofit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;

import java.util.List;

/**
 *  Created by liuwen on 2016/10/13.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    protected List<wuLiuInfo> mListData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewAdapter(Context context, List<wuLiuInfo> datas) {
        this.mListData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        wuLiuInfo wuLiuInfo = mListData.get(position);
        //holder.mTime.setText(mListData.get(position));
        holder.mTime.setText("开始时间：" + wuLiuInfo.getTime());
        holder.mFtime.setText("结束时间：" + wuLiuInfo.getFtime());
        holder.mContext.setText("物流信息：" + wuLiuInfo.getContext());
        setOnListtener(holder);
    }

    //触发
    protected void setOnListtener(final RecyclerView.ViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTime, mFtime, mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.item_time);
            mFtime = (TextView) itemView.findViewById(R.id.item_ftime);
            mContext = (TextView) itemView.findViewById(R.id.item_context);
        }
    }
}
