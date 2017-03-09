package xzh.com.billview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import xzh.com.billview.view.StatisticsView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private StatisticsView mView;
    private int total = 100;
    private float[] items = {1200,220,57,101,210};
    private String[] type = {"第一项","第二项","第三项","第四项","第五项"};


    public TestFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_layout, container, false);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        mView = new StatisticsView(getActivity(), items, total, type);
        mView.setCurrDate(year, month);
        return view;
    }

}
