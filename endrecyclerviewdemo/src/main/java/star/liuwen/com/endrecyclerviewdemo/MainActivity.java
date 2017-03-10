package star.liuwen.com.endrecyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import star.liuwen.com.endrecyclerviewdemo.Bean.AnimalModel;
import star.liuwen.com.endrecyclerviewdemo.Bean.ModelBase;
import star.liuwen.com.endrecyclerviewdemo.Bean.PersonModel;

/**
 * RecyclerViewDemo 实现了两组不同的数据源（不同的pjo对象）构成的数据 用recyclerView显示
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView rvModel;
    private ArrayList<ModelBase> list;

    private enum BASE_TYPE {
        TYPE_PERSON, TYPE_ANIMAL
    }

    private ModelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvModel = (RecyclerView) findViewById(R.id.recycler_view);
        list = new ArrayList<ModelBase>();
        rvModel.setLayoutManager(new LinearLayoutManager(this));
        bindData();
        adapter = new ModelAdapter();
        rvModel.setAdapter(adapter);
    }

    private void bindData() {
        PersonModel person1 = new PersonModel(1, "大白", 25, "2017-02-13");
        list.add(person1);
        PersonModel person2 = new PersonModel(2, "二白", 25, "2018-06-21");
        list.add(person2);
        AnimalModel animal1 = new AnimalModel(1, "兔子", 2, "", "2016-03-15");
        list.add(animal1);
        AnimalModel animal2 = new AnimalModel(1, "兔子2", 2, "森林", "2019-02-14");
        list.add(animal2);
        PersonModel person3 = new PersonModel(3, "三白", 25, "2017-03-18");
        list.add(person3);
        AnimalModel animal3 = new AnimalModel(1, "兔子3", 2, "森林", "2015-03-15");
        list.add(animal3);
        PersonModel person4 = new PersonModel(4, "四白", 25, "2015-03-28");
        list.add(person4);
        PersonModel person5 = new PersonModel(5, "小白", 25, "2016-05-15");
        list.add(person5);
        AnimalModel animal4 = new AnimalModel(1, "兔子4", 2, "森林", "2014-03-14");
        list.add(animal4);
        Collections.sort(list, new Comparator<ModelBase>() {
            @Override
            public int compare(ModelBase model1, ModelBase model2) {
                return model2.data.compareTo(model1.data);
            }
        });  // 排序
    }

    class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == BASE_TYPE.TYPE_PERSON.ordinal()) {
                return new PersonViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_person, parent, false));
            } else if (viewType == BASE_TYPE.TYPE_ANIMAL.ordinal()) {
                return new AnimalViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_animal, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof PersonViewHolder) {
                ((PersonViewHolder) holder).tvPerson.setText(((PersonModel) list.get(position)).getName());
            }
            if (holder instanceof AnimalViewHolder) {
                ((AnimalViewHolder) holder).tvAnimal.setText(((AnimalModel) list.get(position)).getName());
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (list.get(position).BEAN_TYPE == 1) {
                return BASE_TYPE.TYPE_PERSON.ordinal();
            } else if (list.get(position).BEAN_TYPE == 2) {
                return BASE_TYPE.TYPE_ANIMAL.ordinal();
            }
            return 0;
        }

        class PersonViewHolder extends RecyclerView.ViewHolder {
            TextView tvPerson;

            public PersonViewHolder(View itemView) {
                super(itemView);
                tvPerson = (TextView) itemView.findViewById(R.id.tv_person);
            }
        }

        class AnimalViewHolder extends RecyclerView.ViewHolder {
            TextView tvAnimal;

            public AnimalViewHolder(View itemView) {
                super(itemView);
                tvAnimal = (TextView) itemView.findViewById(R.id.tv_animal);
            }
        }
    }
}
