package com.ywl5320.rxjavaretrofit.Activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.ywl5320.rxjavaretrofit.BaseActivity.BaseActivity;
import com.ywl5320.rxjavaretrofit.MainActivity;
import com.ywl5320.rxjavaretrofit.R;
import com.ywl5320.rxjavaretrofit.permisssion.XPermissionUtils;
import com.ywl5320.rxjavaretrofit.utils.DialogUtil;
import com.ywl5320.rxjavaretrofit.utils.LocationUtils;
import com.ywl5320.rxjavaretrofit.utils.PermissionHelper;
import com.ywl5320.rxjavaretrofit.utils.RequestCode;
import com.ywl5320.rxjavaretrofit.utils.ToastUtils;

/**
 * Created by liuwen on 2016/12/9.
 */
public class ManagerPermissionActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_permission_activity);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_phone_test:
                callPhone();
                break;
            case R.id.btn_video_test:
                playVideo();
                break;
            case R.id.btn_readWrite_test:
                doOpenCamera();
                break;
            case R.id.btn_address_test:
                doOpenAddress();
                break;
        }
    }

    /**
     * 拨打电话
     */
    public void callPhone() {
        XPermissionUtils.requestPermissions(this, RequestCode.PHONE, new String[]{Manifest.permission
                .CALL_PHONE}, new XPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }

            @Override
            public void onPermissionDenied() {
                DialogUtil.showAlertDialog(ManagerPermissionActivity.this, "拨打电话");
            }
        });
    }


    public void playVideo() {
        XPermissionUtils.requestPermissions(this, RequestCode.AUDIO, new String[]{Manifest.permission.RECORD_AUDIO}, new
                XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (PermissionHelper.isAudioEnable()) {
                            ToastUtils.showToast(ManagerPermissionActivity.this, "录音操作开始");
                        } else {
                            DialogUtil.showAlertDialog(ManagerPermissionActivity.this, "录音或麦克风");
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        DialogUtil.showAlertDialog(ManagerPermissionActivity.this, "录音或麦克风");
                    }
                }
        );

    }


    public void doOpenCamera() {
        XPermissionUtils.requestPermissions(this, RequestCode.CAMERA, new String[]{Manifest.permission.CAMERA},
                new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (PermissionHelper.isCameraEnable()) {
                            ToastUtils.showToast(ManagerPermissionActivity.this, "打开相机操作");
                        } else {
                            DialogUtil.showAlertDialog(ManagerPermissionActivity.this, "相机");
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        DialogUtil.showAlertDialog(ManagerPermissionActivity.this, "相机");
                    }
                });
    }


    public void doOpenAddress() {
        if (!PermissionHelper.isLocServiceEnable(this)) {
            DialogUtil.showLocServiceDialog(this);
            return;
        }
        LocationUtils.requestLocation(this);
    }

}
