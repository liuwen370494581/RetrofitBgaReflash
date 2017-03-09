package star.liuwen.com.enddesignview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.enddesignview.bean.PieData;
import star.liuwen.com.enddesignview.view.sofaView;

/**
 * 自定义view的学习计划 （10天之内完成）
 * 阶段1：安卓中的坐标系原点都是有屏幕的左上方 往右边乃是x轴的正方向
 * 往下面乃是y轴的正方向
 * view的坐标系都是相对于父控件而言
 */

public class MainActivity extends AppCompatActivity {

    private TextView tvName;
    private sofaView mView;
    private ArrayList<PieData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tvName = (TextView) findViewById(R.id.hello);
//        int top = tvName.getTop();
//        int left = tvName.getLeft();
//        int right = tvName.getRight();
//        int bottom = tvName.getBottom();
        mList.add(new PieData("买菜", 20, 20));
        mList.add(new PieData("吃饭", 30, 30));
        mList.add(new PieData("泡妞", 50, 50));
        mView = (sofaView) findViewById(R.id.view_01);
        mView.setStartAngle(20);
        mView.setData(mList);
    }
}
