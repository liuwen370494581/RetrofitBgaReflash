package sh.ajb.com.endggmm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sh.ajb.com.endggmm.Base.BaseFragment;
import sh.ajb.com.endggmm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends BaseFragment implements BaseFragment.OnReloadDataListener, View.OnClickListener {


    public CircleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle, container, false);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void request(boolean isRefresh) {
    }
}
