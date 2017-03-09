package star.liuwen.com.startretrofit.RxjavaUtils.http;

import android.app.Dialog;

import star.liuwen.com.startretrofit.RxjavaUtils.Base.BaseResponse;
import star.liuwen.com.startretrofit.RxjavaUtils.Base.BaseSubscriber;

/**
 * Created by allen on 2016/12/22.
 * <p>
 * 通用的订阅者
 */

public abstract class CommonSubscriber<T extends BaseResponse> extends BaseSubscriber<T> {


    protected CommonSubscriber(Dialog mLoadingDialog) {
        super(mLoadingDialog);
    }

    public CommonSubscriber() {
        super();
    }

    protected abstract void onError(String errorMsg);

    protected abstract void onSuccess(T t);

    protected void onFinish() {
    }

    @Override
    public void doOnError(String errorMsg) {
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        onFinish();
    }
}
