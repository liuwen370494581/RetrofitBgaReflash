package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sh.ajb.com.endokhhtp.Adapter.ImageHolderAdapter;
import sh.ajb.com.endokhhtp.Adapter.LoadImageHelper;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.R;

/**
 * Created by liuwen on 2016/12/13.
 */
public class ImageCacheActivity extends BaseActivity {
    private static final String TAG = ImageCacheActivity.class.getSimpleName();
    private LoadImageHelper loadImageHelper;
    private List<String> images;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_cache_activity);
        initData();
        initView();
    }

    private void initView() {
        loadImageHelper = new LoadImageHelper(this);
        loadImageHelper.setSwipeRefreshLayout((SwipeRefreshLayout) findViewById(R.id.fragment_loadimage_refreshlayout));
        loadImageHelper.setRecyclerView((RecyclerView) findViewById(R.id.fragment_loadimage_rv));
        loadImageHelper.setAdapter(new ImageHolderAdapter(this, images));
        loadImageHelper.init();

    }

    private void initData() {
        images = new ArrayList<>();
        images.add("https://static.baydn.com/media/media_store/image/f1672263006c6e28bb9dee7652fa4cf6.jpg");
        images.add("https://static.baydn.com/media/media_store/image/8c997fae9ebb2b22ecc098a379cc2ca3.jpg");
        images.add("https://static.baydn.com/media/media_store/image/2a4616f067285b4bd59fe5401cd7106b.jpeg");
        images.add("https://static.baydn.com/media/media_store/image/b0e3ab329c8d8218d2af5c8dfdc21125.jpg");
        images.add("https://static.baydn.com/media/media_store/image/670abb28408a9a0fc3dd9666e5ca1584.jpeg");
        images.add("https://static.baydn.com/media/media_store/image/1e8d675468ab61f4e5bdebd4bcb5f007.jpeg");
        images.add("https://static.baydn.com/media/media_store/image/9b2f93cbfa104dae1e67f540ff14a4c2.jpg");
        images.add("https://static.baydn.com/media/media_store/image/f5e0631e00a09edbbf2eb21eb71b4d3c.jpeg");
    }
}
