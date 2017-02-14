package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sh.ajb.com.endokhhtp.App;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.MainActivity;
import sh.ajb.com.endokhhtp.R;

/**
 * Created by liuwen on 2017/1/18.
 */
public class PopWindowActivity extends BaseActivity {

    private ImageButton ibOperationMore;
    private Button ibOperationMore2;
    List<Map<String, String>> moreList;
    private PopupWindow pwMyPopWindow;// popupwindow
    private ListView lvPopupList;// popupwindow中的ListView
    private int NUM_OF_VISIBLE_LIST_ROWS = 3;// 指定popupwindow中Item的数量

    public PopWindowActivity() {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_test);

        iniData();
        iniPopupWindow();
        // 更多操作按钮
        ibOperationMore = (ImageButton) findViewById(R.id.ib_operate_more);
        //ibOperationMore2是用来测试焦点问题  这样popupwindow显示的时候其他的控件是不能点击的
        //换句话说其它控件点击时没反应
        ibOperationMore2 = (Button) findViewById(R.id.ib_operate_more2);
        ibOperationMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopWindowActivity.this, "cao", Toast.LENGTH_SHORT).show();
            }
        });
        ibOperationMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwMyPopWindow.isShowing()) {
                    pwMyPopWindow.dismiss();// 关闭
                } else {
                    pwMyPopWindow.showAtLocation(ibOperationMore,Gravity.BOTTOM,0,0);// 显示
                    backgroundAlpha(0.7f);
                }
            }
        });


    }

    private void iniData() {

        moreList = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        map = new HashMap<String, String>();
        map.put("share_key", "复制");
        moreList.add(map);
        map = new HashMap<String, String>();
        map.put("share_key", "删除");
        moreList.add(map);
        map = new HashMap<String, String>();
        map.put("share_key", "修改");
        moreList.add(map);
    }

    private void iniPopupWindow() {
        View layout = View.inflate(PopWindowActivity.this, R.layout.task_detail_popupwindow, null);
        lvPopupList = (ListView) layout.findViewById(R.id.lv_popup_list);
        pwMyPopWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pwMyPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件
        lvPopupList.setAdapter(new SimpleAdapter(PopWindowActivity.this, moreList,
                R.layout.list_item_popupwindow, new String[]{"share_key"},
                new int[]{R.id.tv_list_item}));
        lvPopupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                pwMyPopWindow.dismiss();
                Toast.makeText(PopWindowActivity.this,
                        moreList.get(position).get("share_key"),
                        Toast.LENGTH_LONG).show();
            }
        });
        // 控制popupwindow的宽度和高度自适应
    /*lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
        View.MeasureSpec.UNSPECIFIED);
	pwMyPopWindow.setWidth(lvPopupList.getMeasuredWidth());
	pwMyPopWindow.setHeight((lvPopupList.getMeasuredHeight() + 20)
		* NUM_OF_VISIBLE_LIST_ROWS);*/
        // 控制popupwindow点击屏幕其他地方消失
        pwMyPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
                R.mipmap.ic_launcher));// 设置背景图片，不能在布局中设置，要通过代码来设置
        pwMyPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
        pwMyPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                backgroundAlpha(1f);
            }
        });
        ;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
