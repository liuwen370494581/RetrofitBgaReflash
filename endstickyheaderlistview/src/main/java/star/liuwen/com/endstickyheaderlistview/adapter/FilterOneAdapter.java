package star.liuwen.com.endstickyheaderlistview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import star.liuwen.com.endstickyheaderlistview.R;
import star.liuwen.com.endstickyheaderlistview.model.FilterEntity;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterOneAdapter extends BaseListAdapter<FilterEntity> {

    public FilterOneAdapter(Context context) {
        super(context);
    }

    public FilterOneAdapter(Context context, List<FilterEntity> list) {
        super(context, list);
    }

    public void setSelectedEntity(FilterEntity filterEntity) {
        for (FilterEntity entity : getData()) {
            entity.setSelected(filterEntity != null && entity.getKey().equals(filterEntity.getKey()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_filter_one, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FilterEntity entity = getItem(position);

        holder.tvTitle.setText(entity.getKey());
        if (entity.isSelected()) {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_3));
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;

        ViewHolder(View view) {
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
