package com.ywl5320.rxjavaretrofit.httpservice.serviceapi;


import com.ywl5320.rxjavaretrofit.httpservice.beans.UserBean;
import com.ywl5320.rxjavaretrofit.httpservice.beans.WeatherBean;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.service.BaseApi;
import com.ywl5320.rxjavaretrofit.httpservice.service.HttpMethod;
import com.ywl5320.rxjavaretrofit.pjo.BannerModel;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liuwen on 2016/10/23.
 */
public class UserApi extends BaseApi {

    public static UserApi userApi;
    public UserService userService;

    public UserApi() {
        userService = HttpMethod.getInstance().createApi(UserService.class);
    }

    public static UserApi getInstance() {
        if (userApi == null) {
            userApi = new UserApi();
        }
        return userApi;
    }
    /*-------------------------------------获取的方法-------------------------------------*/

//    public void getWeather(String loaction, String output, String ak, Subscriber<WeatherBean> subscriber)
//    {
//        Observable observable = userService.getWeather(loaction, output, ak)
//                .map(new HttpResultFunc<WeatherBean>());
//
//        toSubscribe(observable, subscriber);
//    }


    public void getKuaidInfo(String type, String postid, Subscriber<wuLiuInfo> subscriber) {

        Observable observable = userService.getKuaidiInfo(type, postid)
                .map(new HttpResultFunc<wuLiuInfo>());

        toSubscribe(observable, subscriber);
    }

    public void fetchItemsWithItemCount(String url, Subscriber<BannerModel> subscriber) {
        Observable observable = userService.fetchItemsWithItemCount(url)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

}
