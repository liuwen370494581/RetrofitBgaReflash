package sh.ajb.com.endokhhtp.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import sh.ajb.com.endokhhtp.R;

/**
 * Created by liuwen on 2016/12/22.
 */
public class DaemonService extends Service {
    public static final String TAG = "MyService";
    private static boolean sPower = true;
    private Handler handler = new Handler();
    private MyBinder myBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public void onCreate() {
        //这里判断的是Android 4.3版本 如果不是4.3版本的 就不能构成一个空Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.default_ptr_wodfan_frame1);
            startForeground(250, builder.build());
            startService(new Intent(this, CancelService.class));
        } else {
            startForeground(250, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (sPower) {
                    if (System.currentTimeMillis() > 123456789000000L) {
                        sPower = false;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DaemonService.this, "你来姨妈了" + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    SystemClock.sleep(3000);
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy executed");

    }

    public class MyBinder extends Binder {
        public void startDownLoad() {
            Log.v(TAG, "startDownLoad executed");
        }
    }
}
