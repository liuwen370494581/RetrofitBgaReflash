package sh.ajb.com.endggmm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import sh.ajb.com.endggmm.Base.BaseFragment;
import sh.ajb.com.endggmm.Engine.DataEngine;
import sh.ajb.com.endggmm.R;
import sh.ajb.com.endggmm.Utils.DateTimeUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private TimeAdapter mTimeAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_fragment_recycler);
        LinearLayoutManager line = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(line);
        mTimeAdapter = new TimeAdapter(mRecyclerView);
        if (DataEngine.getData() != null) {
            mTimeAdapter.setData(DataEngine.getData());
            mRecyclerView.setAdapter(mTimeAdapter);
        }


    }

    private void initData(View view) {

    }


    public class TimeAdapter extends BGARecyclerViewAdapter<Map<String, Object>> {

        public TimeAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_home);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, Map<String, Object> model) {
            helper.setText(R.id.title, model.get("title").toString());
            String[] times = DateTimeUtil.getCurrentTime_Today().split("-");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(times[1]).append("月").append(times[2]).append("日");
            helper.setText(R.id.show_time, stringBuffer);
        }
    }
}
