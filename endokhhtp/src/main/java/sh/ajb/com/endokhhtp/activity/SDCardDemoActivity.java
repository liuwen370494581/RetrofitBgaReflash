package sh.ajb.com.endokhhtp.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;

import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.R;

/**
 * Created by liuwen on 2017/1/19.
 */
public class SDCardDemoActivity extends BaseActivity {
    private TextView tvResult;
    private EditText edResult;
    private static final String FILE_NAME = "test.txt";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcard_demo_activity);
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.txt_result);
        edResult = (EditText) findViewById(R.id.ed_result);
    }

    /**
     * public static File getDataDirectory ()
     * 获得Android下的data文件夹的目录
     * public static File getDownloadCacheDirectory ()
     * 获得Android Download/Cache 内容的目录
     * public static File getExternalStorageDirectory ()
     * 获得Android外部存储器也就是SDCard的目录
     * public static String getExternalStorageState ()
     * 获得Android外部存储器的当前状态
     * public static File getRootDirectory ()
     * 获得Android下的root文件夹的目录
     *
     * @param view
     */
    public void onWrite(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String content = edResult.getText().toString();

            try {

                //获取Sd卡中的路径
                ///storage/emulated/0
                File sdCardDir = Environment.getExternalStorageDirectory();
                ///storage/emulated/0/Music
                File sdpublic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC
                );
                //  /cache
                File downDir = Environment.getDownloadCacheDirectory();
                // /system
                File rootDir = Environment.getRootDirectory();
                Environment.getExternalStorageState();
                ///storage/emulated/0
                String sdcardPath = sdCardDir.getAbsolutePath();

                File file = new File(sdCardDir, FILE_NAME);
                RandomAccessFile raf = new RandomAccessFile(file, "rw");

                //将文件记录指针移动到最后
                raf.seek(file.length());
                //输出文件内容
                raf.write(content.getBytes());
                raf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public void onRead(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                StringBuilder sb = new StringBuilder();
                File sdCardDie = Environment.getExternalStorageDirectory();
                File file = new File(sdCardDie, FILE_NAME);
                InputStream inputStream = new FileInputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, len));
                }
                tvResult.setText(sb);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
