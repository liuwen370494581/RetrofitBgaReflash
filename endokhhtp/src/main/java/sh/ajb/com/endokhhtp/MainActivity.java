package sh.ajb.com.endokhhtp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.Service.DaemonService;
import sh.ajb.com.endokhhtp.activity.ChangeSkinActivity;
import sh.ajb.com.endokhhtp.activity.ChatRecyclerActivity;
import sh.ajb.com.endokhhtp.activity.BGAAdapterActivity;
import sh.ajb.com.endokhhtp.activity.ImageCacheActivity;
import sh.ajb.com.endokhhtp.activity.KeyBoardActivity;
import sh.ajb.com.endokhhtp.activity.RxJavaActivity;
import sh.ajb.com.endokhhtp.activity.PopWindowActivity;
import sh.ajb.com.endokhhtp.activity.RetrofitActivity;
import sh.ajb.com.endokhhtp.activity.SDCardDemoActivity;
import sh.ajb.com.endokhhtp.activity.ScrollerActivity;
import sh.ajb.com.endokhhtp.activity.ToolBarActivity;

public class MainActivity extends BaseActivity {


    private int count = 0;
    private TextView textView;
    private OkHttpClient client;
    private Request request;
    private CacheControl cacheControl;
    public static int position = 1;


    //12月14日加入notification的一些基本的使用
    NotificationCompat.Builder mBuilder;
    NotificationManager notificationManager;
    int notifyId = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取通知栏的系统服务
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void toRetrofit(View view) {
        startActivity(new Intent(this, RetrofitActivity.class));
    }

    public void toCache(View view) {
        startActivity(new Intent(this, ImageCacheActivity.class));
    }


    public void toDialog(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this).
                setTitle("移动侦测灵敏度").setIcon(R.mipmap.default_ptr_wodfan_frame1).
                setSingleChoiceItems(R.array.you, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "ddd" + which, Toast.LENGTH_SHORT).show();
                        position = which;
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
    }


    public void toNotification(View view) {
        Intent intent = new Intent(this, ImageCacheActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setNumber(5) //显示数量  这个在魅族手机中测试没有效果
                .setWhen(System.currentTimeMillis())//设置时间 有的机器自动帮你设置了 魅族就已经自动设置了
                .setOngoing(true) //ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setContentIntent(pendingIntent)//设定要跳转的Activity
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setTicker("年后梦454541321决定开发大控件科技大厦功能麻烦您咖啡馆")////通知首次出现在通知栏，带上升动画效果的
                .setSmallIcon(R.mipmap.default_ptr_wodfan_frame1)
                .setAutoCancel(true);
        notificationManager.notify(notifyId, mBuilder.build());

    }

    public void toDesignNotification(View view) {
        final String[] singleList = {getString(R.string.level_1), getString(R.string.level_2), getString(R.string.level_3)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("移动灵敏度设置");
        builder.setSingleChoiceItems(singleList, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "你点击了" + singleList[which], Toast.LENGTH_SHORT).show();
                position = which;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void toAdapter(View view) {
        startActivity(new Intent(MainActivity.this, BGAAdapterActivity.class));
    }

    public void toChart(View view) {
        startActivity(new Intent(MainActivity.this, ChatRecyclerActivity.class));
    }

    public void toScroller(View view) {
        startActivity(new Intent(MainActivity.this, ScrollerActivity.class));
    }

    public void toService(View view) {
        startService(new Intent(MainActivity.this, DaemonService.class));
    }

    public void toToolbar(View view) {
        startActivity(new Intent(MainActivity.this, ToolBarActivity.class));
    }

    public void popWindow(View view) {
        startActivity(new Intent(MainActivity.this, PopWindowActivity.class));
    }

    AlarmManager aManager;
    Calendar currentTime = Calendar.getInstance();

    public void toAlarmManager(View view) {
        //获取AlarmManager对象
        aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        Calendar currentTime = Calendar.getInstance();
        //创建一个TimePickerDialog实例，并把它显示出来
        new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Intent intent = new Intent(MainActivity.this, PopWindowActivity.class);
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                //根据用户选择时间来设置Calender对象
                c.set(Calendar.HOUR, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                Toast.makeText(MainActivity.this, "闹铃设置成功啦！", Toast.LENGTH_SHORT).show();
            }
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();

    }


    public void toSDK(View view) {
        startActivity(new Intent(MainActivity.this, SDCardDemoActivity.class));
    }

    public void toSkin(View view) {
        startActivity(new Intent(MainActivity.this, ChangeSkinActivity.class));
    }

    public void toKeyBoard(View view) {
        startActivity(new Intent(MainActivity.this, KeyBoardActivity.class));
    }

    public void toRxJava(View view) {
        startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
    }
}
