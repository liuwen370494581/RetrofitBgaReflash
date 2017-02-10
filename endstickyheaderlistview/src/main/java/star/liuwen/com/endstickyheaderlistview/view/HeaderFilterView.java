package star.liuwen.com.endstickyheaderlistview.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import star.liuwen.com.endstickyheaderlistview.R;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderFilterView extends HeaderViewInterface<Object> implements FilterView.OnFilterClickListener {

    FilterView fvFilter;

    public HeaderFilterView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(Object obj, ListView listView) {
        View view = mInflate.inflate(R.layout.header_filter_layout, listView, false);
        fvFilter = (FilterView) view.findViewById(R.id.fv_filter);
        dealWithTheView(obj);
        listView.addHeaderView(view);
    }

    // 获得筛选View
    public FilterView getFilterView() {
        return fvFilter;
    }

    private void dealWithTheView(Object obj) {
        fvFilter.setOnFilterClickListener(this);
    }

    @Override
    public void onFilterClick(int position) {
        if (onFilterClickListener != null) {
            onFilterClickListener.onFilterClick(position);
        }
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

}
