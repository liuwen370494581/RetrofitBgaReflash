package sh.ajb.com.endrxbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sh.ajb.com.endrxbus.RxBus.RxBus;
import sh.ajb.com.endrxbus.RxBus.RxBusResult;

public class SecondActivity extends AppCompatActivity {
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvShow = (TextView) findViewById(R.id.tv_show2);
        RxBusEvent();
    }

    private void RxBusEvent() {
        RxBus.getInstance().toObserverableOnMainThread("First", new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                tvShow.setText(o.toString());
            }
        });
    }

    public void toRxBusOne(View view) {
        RxBus.getInstance().post("Second", new String("6666666"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }
}
