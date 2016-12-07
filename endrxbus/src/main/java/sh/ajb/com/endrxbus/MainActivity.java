package sh.ajb.com.endrxbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sh.ajb.com.endrxbus.RxBus.RxBus;
import sh.ajb.com.endrxbus.RxBus.RxBusResult;

public class MainActivity extends AppCompatActivity {

    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = (TextView) findViewById(R.id.tv_show);

        RxBusEvent();
    }

    private void RxBusEvent() {

        RxBus.getInstance().toObserverableOnMainThread("Second", new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                tvShow.setText(o.toString());
            }
        });
    }


    public void onRxBusOne(View view) {
        RxBus.getInstance().post("First", new String("hello RxBusFirst"));
    }


    public void onRxBusTwo(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }
}
