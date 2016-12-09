package com.ywl5320.rxjavaretrofit.httpservice.service;


import com.ywl5320.rxjavaretrofit.httpservice.httpentity.HttpResult;
import com.ywl5320.rxjavaretrofit.httpservice.httpentity.HttppJIeGuo;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liuwen on 2016/10/23.
 */
public class BaseApi {

    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     *            Func1(I,O) 输入和输出
     */
//    public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (httpResult.getStatus() == 1) {
//                return httpResult.getData();
//            }
//            throw new ExceptionApi(httpResult.getStatus(), httpResult.getMessage());
//        }
//    }
//
    public class HttpResultFunc<T> implements Func1<HttppJIeGuo<T>, T> {

        @Override
        public T call(HttppJIeGuo<T> tHttppJIeGuo) {
            if (tHttppJIeGuo.getMessage().equals("ok")) {

                return (T) tHttppJIeGuo.getData();
            }
            throw new ExceptionApi(Integer.parseInt(tHttppJIeGuo.getState()), tHttppJIeGuo.getMessage());
        }
    }

}








