package sh.ajb.com.endokhhtp.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sh.ajb.com.endokhhtp.Adapter.BgPicGridAdapter;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.model.BgPicModel;
import sh.ajb.com.endokhhtp.utils.SharePrefrenceUtil;

/**
 * Created by liuwen on 2017/1/20.
 */
public class ChangeSkinActivity extends BaseActivity {
    private GridView mGridView;
    private List<BgPicModel> mList;
    private SharePrefrenceUtil shareUtil;
    private BgPicGridAdapter mAdapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_skin_activity);
        initData();
        initView();


    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.change_background_grid);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mAdapter = new BgPicGridAdapter(this, mList);
        shareUtil = new SharePrefrenceUtil(ChangeSkinActivity.this);
        if (!shareUtil.getPath().isEmpty()) {
            Bitmap bitmap = getBitmapByPath(shareUtil.getPath(), false);
            mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = ((BgPicModel) mAdapter.getItem(position)).path;
                shareUtil.saveBgPicPath(path);
                Drawable drawable = Drawable.createFromPath(path);
                mDrawerLayout.setBackgroundDrawable(drawable);
                Bitmap bitmap = getBitmapByPath(path, false);
                if (bitmap != null) {
                    mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                }
            }
        });

        mGridView.setAdapter(mAdapter);
    }

    private void initData() {
        AssetManager am = getAssets();
        try {

            String[] drawableList = am.list("bkgs");
            mList = new ArrayList<>();
            for (String path : drawableList) {
                BgPicModel model = new BgPicModel();
                InputStream is = am.open("bkgs/" + path);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                model.path = path;
                model.bitmap = bitmap;
                mList.add(model);
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Bitmap getBitmapByPath(String path, boolean auto) {
        //	Log.i("weather", "getbitmap is called");
        AssetManager am = this.getAssets();
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            if (auto == false) {
                is = am.open("bkgs/" + path);
            } else if (auto == true) {
                is = am.open("autobkgs/" + path);

            }
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
