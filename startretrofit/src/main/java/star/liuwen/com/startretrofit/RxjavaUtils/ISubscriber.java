package star.liuwen.com.startretrofit.RxjavaUtils;

import star.liuwen.com.startretrofit.RxjavaUtils.Base.BaseResponse;

/**
 * Created by allen on 2016/12/21.
 * <p/>
 * 定义请求结果处理接口
 */

public interface ISubscriber<T extends BaseResponse> {

    //      void doOnError(Throwable e);

    void doOnError(String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
