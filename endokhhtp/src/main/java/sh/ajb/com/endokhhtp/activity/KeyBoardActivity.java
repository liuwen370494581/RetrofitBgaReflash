package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.MainActivity;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.View.KeyboardUtil;

/**
 * Created by liuwen on 2017/2/16.
 */
public class KeyBoardActivity extends BaseActivity {
    private EditText recharge_money_et;
    private KeyboardUtil keyboardUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_board_activity);
        recharge_money_et = (EditText) findViewById(R.id.recharge_money_et);
        keyboardUtil = new KeyboardUtil(KeyBoardActivity.this, KeyBoardActivity.this, recharge_money_et);
        init();
    }
    public void init() {
        recharge_money_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int numberType = recharge_money_et.getInputType();
                recharge_money_et.setInputType(InputType.TYPE_NULL);
                keyboardUtil.showKeyboard();
                recharge_money_et.setInputType(numberType);
                return true;
            }
        });
        keyboardUtil.setOnEnterListener(new KeyboardUtil.EnterListener() {
            @Override
            public void enter() {
                Toast.makeText(KeyBoardActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
