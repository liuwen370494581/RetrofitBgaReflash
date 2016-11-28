package com.ywl5320.rxjavaretrofit.httpservice.serviceapi;


import com.ywl5320.rxjavaretrofit.httpservice.beans.WeatherBean;
import com.ywl5320.rxjavaretrofit.httpservice.beans.wuLiuInfo;
import com.ywl5320.rxjavaretrofit.httpservice.httpentity.HttpResult;
import com.ywl5320.rxjavaretrofit.httpservice.httpentity.HttppJIeGuo;


import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ywl on 2016/5/19.
 */
public interface UserService {

    /**
     * 获取百度天气数据
     *
     * @param location
     * @param output
     * @param ak
     * @return
     */
    @POST("telematics/v3/weather")
    Observable<HttpResult<WeatherBean>> getWeather(@Query("location") String location, @Query("output") String output, @Query("ak") String ak);


    //http://www.kuaidi100.com/query?type=zhongtong&postid=418271182599
    @POST("query")
    Observable<HttppJIeGuo<wuLiuInfo>> getKuaidiInfo(@Query("type") String type, @Query("postid") String postid);

}
