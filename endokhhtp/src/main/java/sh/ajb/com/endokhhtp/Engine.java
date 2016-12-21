package sh.ajb.com.endokhhtp;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import sh.ajb.com.endokhhtp.model.BannerModel;
import sh.ajb.com.endokhhtp.model.NormalModel;
import sh.ajb.com.endokhhtp.model.RefreshModel;
import sh.ajb.com.endokhhtp.model.StaggeredModel;

/**
 * Created by liuwen on 2016/12/19.
 */
public interface Engine {

    @GET
    Call<BannerModel> fetchItemsWithItemCount(@Url String url);

    @GET
    Call<List<RefreshModel>> loadContentData(@Url String url);

    @GET("refreshlayout/api/staggered_new{pageNumber}.json")
    Call<List<StaggeredModel>> loadNewStaggeredData(@Path("pageNumber") int pageNumber);

    //获取刷新数据的接口
    @GET("refreshlayout/api/newdata{pageNumber}.json")
    Call<List<RefreshModel>> loadNewData(@Path("pageNumber") int pageNumber);

    //获取加载更多的接口
    @GET("refreshlayout/api/moredata{pageNumber}.json")
    Call<List<RefreshModel>> loadMoreData(@Path("pageNumber") int pageNumber);

    //获取adapter的数据
    @GET
    Call<List<NormalModel>> getNormalModels(@Url String url);



}