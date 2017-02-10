package star.liuwen.com.endstickyheaderlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.endstickyheaderlistview.manager.ImageManager;

public abstract class BaseListAdapter<E> extends BaseAdapter {

    private List<E> mList = new ArrayList<E>();
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ImageManager mImageManager;

    public BaseListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageManager = new ImageManager(context);
    }

    public BaseListAdapter(Context context, List<E> list) {
        this(context);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void clearAll() {
        mList.clear();
    }

    public List<E> getData() {
        return mList;
    }

    public void addALL(List<E> list){
        if(list==null||list.size()==0) return;
        mList.addAll(list);
    }
    public void add(E item){
        mList.add(item);
    }

    @Override
    public E getItem(int position) {
        return (E) mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeEntity(E e){
        mList.remove(e);
    }

}
