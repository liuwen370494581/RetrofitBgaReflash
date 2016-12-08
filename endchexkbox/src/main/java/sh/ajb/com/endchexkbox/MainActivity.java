package sh.ajb.com.endchexkbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int NOSELECT_STATE = -1;// 表示未选中任何CheckBox

    private ListView listView;
    private Button bt_cancel, bt_delete;
    private TextView tv_sum;
    private LinearLayout linearLayout;

    private List<String> list = new ArrayList<String>();// 数据
    private List<String> list_delete = new ArrayList<String>();// 需要删除的数据
    private boolean isMultiSelect = false;// 是否处于多选状态

    private MyAdapter adapter;// ListView的Adapter
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        tv_sum = (TextView) findViewById(R.id.tv_sum);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        bt_cancel.setOnClickListener(this);
        bt_delete.setOnClickListener(this);

        // 设置初始数据
        list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            String str = "Item" + i;
            list.add(str);
        }

        // 未选中任何Item，position设置为-1
        adapter = new MyAdapter(MainActivity.this, list, NOSELECT_STATE);
        listView.setAdapter(adapter);
    }

    public void toShoopingCar(View view) {
        startActivity(new Intent(MainActivity.this, ShoopingCarActivity.class));
    }


    private class MyAdapter extends BaseAdapter {

        private List<String> list;
        private LayoutInflater inflater;

        private HashMap<Integer, Integer> isCheckBoxVisible;// 用来记录是否显示checkBox
        private HashMap<Integer, Boolean> isChecked;// 用来记录是否被选中

        @SuppressLint("UseSparseArrays")
        public MyAdapter(Context context, List<String> list, int position) {
            inflater = LayoutInflater.from(context);
            this.list = list;
            isCheckBoxVisible = new HashMap<Integer, Integer>();
            isChecked = new HashMap<Integer, Boolean>();
            // 如果处于多选状态，则显示CheckBox，否则不显示
            if (isMultiSelect) {
                for (int i = 0; i < list.size(); i++) {
                    isCheckBoxVisible.put(i, CheckBox.VISIBLE);
                    isChecked.put(i, false);
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    isCheckBoxVisible.put(i, CheckBox.INVISIBLE);
                    isChecked.put(i, false);
                }
            }

            // 如果长按Item，则设置长按的Item中的CheckBox为选中状态
            if (isMultiSelect && position >= 0) {
                isChecked.put(position, true);
            }
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_main, null);
                viewHolder.tv_Name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cb_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final String str = list.get(position);
            viewHolder.tv_Name.setText(str);
            // 根据position设置CheckBox是否可见，是否选中
            viewHolder.cb.setChecked(isChecked.get(position));
            viewHolder.cb.setVisibility(isCheckBoxVisible.get(position));
            // ListView每一个Item的长按事件
            convertView.setOnLongClickListener(new onMyLongClick(position, list));
            /*
             * 在ListView中点击每一项的处理
             * 如果CheckBox未选中，则点击后选中CheckBox，并将数据添加到list_delete中
             * 如果CheckBox选中，则点击后取消选中CheckBox，并将数据从list_delete中移除
             */
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 处于多选模式
                    if (isMultiSelect) {
                        if (viewHolder.cb.isChecked()) {
                            viewHolder.cb.setChecked(false);
                            list_delete.remove(str);
                        } else {
                            viewHolder.cb.setChecked(true);
                            list_delete.add(str);
                        }
                        tv_sum.setText("共选择了" + list_delete.size() + "项");
                    } else {
                        showToast(MainActivity.this, "点击了" + position);
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            public TextView tv_Name;
            public CheckBox cb;
        }

        // 自定义长按事件
        class onMyLongClick implements View.OnLongClickListener {

            private int position;
            private List<String> list;

            // 获取数据，与长按Item的position
            public onMyLongClick(int position, List<String> list) {
                this.position = position;
                this.list = list;
            }

            // 在长按监听时候，切记将监听事件返回ture
            @Override
            public boolean onLongClick(View v) {
                isMultiSelect = true;
                list_delete.clear();
                // 添加长按Item到删除数据list中
                list_delete.add(list.get(position));
                linearLayout.setVisibility(View.VISIBLE);
                tv_sum.setText("共选择了" + list_delete.size() + "项");
                for (int i = 0; i < list.size(); i++) {
                    adapter.isCheckBoxVisible.put(i, CheckBox.VISIBLE);
                }
                // 根据position，设置ListView中对应的CheckBox为选中状态
                adapter = new MyAdapter(MainActivity.this, list, position);
                listView.setAdapter(adapter);
                return true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 取消选择
            case R.id.bt_cancel:
                isMultiSelect = false;// 退出多选模式
                list_delete.clear();// 清楚选中的数据
                // 重新加载Adapter
                adapter = new MyAdapter(MainActivity.this, list, NOSELECT_STATE);
                listView.setAdapter(adapter);
                linearLayout.setVisibility(View.GONE);
                break;
            // 删除
            case R.id.bt_delete:
                isMultiSelect = false;
                // 将数据从list中移除
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list_delete.size(); j++) {
                        if (list != null && list.size() > 0 || list_delete != null && list.size() > 0) {
                            if (list.get(i).equals(list_delete.get(j))) {
                                list.remove(i);
                            }
                        }
                    }
                }
                list_delete.clear();
                // 重新加载Adapter
                adapter = new MyAdapter(MainActivity.this, list, NOSELECT_STATE);
                listView.setAdapter(adapter);
                linearLayout.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }


    public void showToast(Context context, String title) {
        if (mToast == null) {
            mToast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(title);
        }
        mToast.show();
    }
}
