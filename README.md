# RetrofitBgaReflash
这个项目使用了Retrofit和Rxjava访问网络和BGAreflash下拉刷新和上拉加载

已经是完全封装好的 直接可以使用即可的






private void getDateFromService() {
          showLoadDialog("请稍等");
           UserApi.getInstance().getKuaidInfo("zhongtong", "418271182599", new HttpSubscriber<wuLiuInfo>(new SubscriberOnListener() {
            @Override
            public void onSucceed(Object data) {
                mListData.addAll((Collection<? extends wuLiuInfo>) data);
                mRecyclerViewAdapter.notifyDataSetChanged();
                hideLoadDialog();
                // Toast.makeText(NormalRecyclerActivity.this, mDataList.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String msg) {
                hideLoadDialog();
                Toast.makeText(DefineLoadWithRefreshActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, DefineLoadWithRefreshActivity.this));
    }

