package sh.ajb.com.endokhhtp.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sh.ajb.com.endokhhtp.App;
import sh.ajb.com.endokhhtp.R;

/**
 * Created by wuming on 2016/12/7.
 */

public class ImageUtil {
    private static final String TAG = ImageUtil.class.getSimpleName();

    /**
     * 先从本地加载，本地没有，再去网络加载
     *
     * @param url
     * @param imageView
     */
    public static void loadImage(final String url, final ImageView imageView) {

        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            Bitmap bitmap = BitmapFileCache.getInstance().get(url.hashCode());

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    if (bitmap == null) {
                        bitmap = Glide.with(App.getInstance())
                                .load(url)
                                .asBitmap()
                                .centerCrop()
                                .into(250, 250)
                                .get();
                        if (bitmap != null) {
                            //将bitmap保存到本地
                            BitmapFileCache.getInstance().put(url.hashCode(), bitmap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bitmap == null) {
                    bitmap = BitmapFileCache.getInstance().get(url.hashCode(), R.mipmap.default_img);
                }
                subscriber.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
    }
}
