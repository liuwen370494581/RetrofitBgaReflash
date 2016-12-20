package sh.ajb.com.endokhhtp.Adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.model.NormalModel;

/**
 * Created by liuwen on 2016/12/19.
 */
public class NormalRecyclerViewAdapter extends BGARecyclerViewAdapter<NormalModel> {


    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_normal_1);
    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
        helper.setRVItemChildTouchListener(R.id.iv_item_normal_avatar);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, NormalModel model) {
        Glide.with(mContext).load(model.avatorPath).placeholder(R.mipmap.holder_avatar).error(R.mipmap.holder_avatar).into(helper.getImageView(R.id.iv_item_normal_avatar));
        helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);

        helper.setChecked(R.id.cb_item_normal_status, model.selected);
    }
}
