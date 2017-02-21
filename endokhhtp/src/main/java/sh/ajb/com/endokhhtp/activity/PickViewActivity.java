package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.widget.PickValueView;

/**
 * Created by liuwen on 2017/2/20.
 */
public class PickViewActivity extends BaseActivity implements  PickValueView.onSelectedChangeListener {
    PickValueView pickValues;
    LinearLayout pvLayout;
    private TextView tvSelected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_view_activity);

        pvLayout = (LinearLayout) findViewById(R.id.Main_pvLayout);
        pickValues = (PickValueView) findViewById(R.id.pickValues);
        tvSelected = (TextView) findViewById(R.id.Main_tvSelected);

        Integer value[] = new Integer[20];
        for (int i = 0; i < value.length; i++) {
            value[i] = i + 1;
        }
        Integer middle[] = new Integer[15];
        for (int i = 0; i < middle.length; i++) {
            middle[i] = i + 1;
        }
        Integer right[] = new Integer[10];
        for (int i = 0; i < right.length; i++) {
            right[i] = i;
        }

        pickValues.setValueData(value, value[0], middle, middle[0], right, right[0]);


    }

    public void open(View view) {
        showView(pickValues);
    }

    private void showView(View view) {
        for (int i = 0; i < pvLayout.getChildCount(); i++) {
            pvLayout.getChildAt(i).setVisibility(View.GONE);
        }
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSelected(PickValueView view, Object leftValue, Object middleValue, Object rightValue) {
        if(view==pickValues){
            int left = (int) leftValue;
            int middle = (int) middleValue;
            int right = (int) rightValue;
            tvSelected.setText("selected: left:" + left + "  middle:" + middle + "  right:" + right);
        }
    }
}
