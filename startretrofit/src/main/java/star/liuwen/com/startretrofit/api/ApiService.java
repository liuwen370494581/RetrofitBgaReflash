package star.liuwen.com.startretrofit.api;



import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import star.liuwen.com.startretrofit.RxjavaUtils.Base.BaseResponse;
import star.liuwen.com.startretrofit.bean.Banner;

/**
 * Created by allen on 2016/12/26.
 *
 */

public interface ApiService {

    @GET("/banners")
    Observable<Banner> getBanner();

    @POST("/upload")
    Observable<BaseResponse> upLoadImg(@Body String jsonString);
}
