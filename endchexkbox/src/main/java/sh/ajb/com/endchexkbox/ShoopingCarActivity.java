package sh.ajb.com.endchexkbox;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShoopingCarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOSELECT_STATE = -1;// 表示未选中任何CheckBox

    private RecyclerView recyclerView;
    private Button bt_cancel, bt_delete;
    private TextView tv_sum;
    private LinearLayout linearLayout;

    private List<String> list;//数据
    private List<String> list_delete = new ArrayList<String>();// 需要删除的数据
    private boolean isMultiSelect = false;// 是否处于多选状态

    private CheckBoxAdapter adapter;// ListView的Adapter
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooping_car);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_1);
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
        recyclerViews();
    }


    public void recyclerViews() {
        adapter = new CheckBoxAdapter(list, ShoopingCarActivity.this, NOSELECT_STATE);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShoopingCarActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel:

                isMultiSelect = false;//退出多选模式
                list_delete.clear();
                //重新加载Adapter
                recyclerViews();
                linearLayout.setVisibility(View.GONE);
                break;

            case R.id.bt_delete:
                isMultiSelect = false;
                for (int i = 0; i < list.size(); i++) {
                    if (list != null && list.size() > 0 || list_delete != null && list.size() > 0) {
                        for (int j = 0; j < list_delete.size(); j++) {
                            if (list.get(i).equals(list_delete.get(j))) {
                                list.remove(i);
                            }
                        }
                    }
                }
                list_delete.clear();
                recyclerViews();
                linearLayout.setVisibility(View.GONE);
                break;

            default:
                break;
        }

    }

    //适配器CheckBoxAdapter
    class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {
        private List<String> list;
        private LayoutInflater inflater;
        private HashMap<Integer, Integer> isCheckBoxVisible; //用来记录是否显示checkBox
        private HashMap<Integer, Boolean> isChecked;//用来记录是否被选中


        public CheckBoxAdapter(List<String> data, Context context, int position) {
            this.list = data;
            inflater = LayoutInflater.from(context);
            isCheckBoxVisible = new HashMap<>();
            isChecked = new HashMap<>();
            //如果处于多选模式，则显示checkBox,否则不显示
            if (isMultiSelect) {
                for (int i = 0; i < data.size(); i++) {
                    isCheckBoxVisible.put(i, CheckBox.VISIBLE);
                    isChecked.put(i, false);
                }
            } else {
                for (int i = 0; i < data.size(); i++) {
                    isCheckBoxVisible.put(i, CheckBox.INVISIBLE);
                    isChecked.put(i, false);
                }
            }
            //如果长按item 则显示长按item的checkBox的状态
            if (isMultiSelect && position >= 0) {
                isChecked.put(position, true);
            }
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_checkbox, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final String str = list.get(position);
            holder.tvShow.setText(str);
            // 根据position设置CheckBox是否可见，是否选中
            holder.cbChooice.setChecked(isChecked.get(position));
            holder.cbChooice.setVisibility(isCheckBoxVisible.get(position));
            holder.lyShow.setOnLongClickListener(new onMyLongClick(position, list));

            holder.lyShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMultiSelect) {
                        if (holder.cbChooice.isChecked()) {
                            holder.cbChooice.setChecked(false);
                            list_delete.remove(str);
                        } else {
                            holder.cbChooice.setChecked(true);
                            list_delete.add(str);
                        }
                        tv_sum.setText("共选择了" + list_delete.size() + "项");
                    } else {
                        showToast(ShoopingCarActivity.this, "你点击了" + position);
                    }

                }
            });

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

                adapter = new CheckBoxAdapter(list, ShoopingCarActivity.this, position);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShoopingCarActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                return true;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvShow;
            CheckBox cbChooice;
            LinearLayout lyShow;


            public ViewHolder(View view) {
                super(view);
                tvShow = (TextView) view.findViewById(R.id.tv_show);
                cbChooice = (CheckBox) view.findViewById(R.id.cb_chooice);
                lyShow = (LinearLayout) view.findViewById(R.id.ll_show);

            }
        }


    }


    protected void showToast(Context context, String title) {
        if (mToast == null) {
            mToast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(title);
        }
        mToast.show();
    }
}
