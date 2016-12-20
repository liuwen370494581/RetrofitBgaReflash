# RetrofitBgaReflash
* 1.这里的几个Demo都是平常项目中拉出来的 避免重复造轮子 记录下来 感谢GIthub 

* 1.这个项目使用了Retrofit和Rxjava访问网络和BGAreflash下拉刷新和上拉加载

* 2 已经是完全封装好的 直接可以使用即可的

###
 
	btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoadDialog("正在获取快递物流信息");


                UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
                    @Override
                    public void onSucceed(Object data) {
                        hideLoadDialog();
                        ToastUtils.showToast(MainActivity.this, data.toString());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showToast(MainActivity.this, "获取内容失败");
                        hideLoadDialog();

                    }
                }, MainActivity.this));




				
# 可能是最精简的Android6.0运行时权限处理方式 
* 在BaseActivity中封装 具体工具类在Utils 
* 使用方式

* 以拨打电话为例

* 1、首先AndroidManifest中配置必要的权限

* <uses-permission android:name="android.permission.CALL_PHONE"/>

* 2、在基类即是BaseActivity中加上回调方法 



####  
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        XPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
* 3、调用方法

* XPermissionUtils.requestPermissions(Context context, int requestCode, String[] permissions, OnPermissionListener listener)
* 这里主要注意这个Context必需是一个Activity

* 如果在Activity中可以传this;

* 如果在Fragment中传getActivity();

* 如果在View中传getContext();

* 代码调用

###  
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


# EndOkhttp 加入王浩大神的BGAAdapter 万能适配器 


* 写适配器只要简单的几行代码即可了


### 
    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_normal_1);
    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
        helper.setRVItemChildTouchListener(R.id.iv_item_normal_avatar);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, NormalModel model) {
        Glide.with(mContext).load(model.avatorPath).placeholder(R.mipmap.holder_avatar).error(R.mipmap.holder_avatar).into(helper.getImageView(R.id.iv_item_normal_avatar));
        helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);

        helper.setChecked(R.id.cb_item_normal_status, model.selected);
    }







  # 特别感谢 
   
  https://github.com/AndSync/XPermissionUtils
 
  https://github.com/bingoogolapple
  
  https://github.com/wanliyang1990/RxjavaRetrofit
  
    
