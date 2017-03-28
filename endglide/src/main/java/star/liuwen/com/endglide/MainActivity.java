package star.liuwen.com.endglide;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView imageShow;
    private String imageStatic = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    private String imageDynamic = "http://p1.pstatp.com/large/166200019850062839d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageShow = (ImageView) findViewById(R.id.image_view);

//        // 加载本地图片
//        File file = new File(getExternalCacheDir() + "/image.jpg");
//        Glide.with(this).load(file).into(imageShow);
//
//       // 加载应用资源
//        int resource = R.mipmap.ic_launcher;
//        Glide.with(this).load(resource).into(imageShow);
//
//       // 加载二进制流
//        byte[] image = getImageBytes();
//        Glide.with(this).load(image).into(imageView);
//
//       // 加载Uri对象
//        Uri imageUri = getImageUri();
//        Glide.with(this).load(imageUri).into(imageView);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.glide_static:
                Glide.with(this).load(imageStatic)//静态网址
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//设置glide缓存为空 这样可以显示占位图
                        .error(R.mipmap.ic_launcher)//网络不好 加载失败的图
                        .placeholder(R.mipmap.ic_launcher)//占位图
                        .into(imageShow);
                break;
            case R.id.glide_dynamic:
                Glide.with(this).load(imageDynamic)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//动态图片一定要设置这个属性
                        .placeholder(R.mipmap.ic_launcher)//占位图
                        .error(R.mipmap.ic_launcher)
                        .into(imageShow);
                break;
        }
    }
}
