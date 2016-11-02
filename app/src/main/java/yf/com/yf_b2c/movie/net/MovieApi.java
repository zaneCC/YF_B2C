package yf.com.yf_b2c.movie.net;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import yf.com.yf_b2c.movie.bean.HttpResult;
import yf.com.yf_b2c.movie.bean.Subject;

/**
 * Created by zhouzhan on 25/10/16.
 */
public interface MovieApi {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
