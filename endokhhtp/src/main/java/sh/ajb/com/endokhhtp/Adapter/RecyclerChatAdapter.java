package sh.ajb.com.endokhhtp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.model.ChatModel;

/**
 * Created by liuwen on 2016/12/21.
 */
public class RecyclerChatAdapter extends BGARecyclerViewAdapter<ChatModel> {
    public RecyclerChatAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.iv_item_chat_me_avatar);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).mUserType == ChatModel.UserType.Other) {
            return R.layout.item_chat_other;
        } else {
            return R.layout.item_chat_me;
        }
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ChatModel model) {
        if (model.mUserType == ChatModel.UserType.Other) {
            helper.setHtml(R.id.tv_item_chat_other_msg, String.format(mContext.getString(R.string.color_msg_from), model.mMsg));
        } else {
            helper.setHtml(R.id.tv_item_chat_me_msg, String.format(mContext.getString(R.string.color_msg_to), model.mMsg));
            helper.setVisibility(R.id.iv_item_chat_me_status, model.mSendStatus == ChatModel.SendStatus.Success ? View.GONE : View.VISIBLE);
        }
    }
}
