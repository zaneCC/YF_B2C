package yf.com.yf_b2c.movie.model;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;
import yf.com.library.model.BaseModel;
import yf.com.yf_b2c.movie.net.ApiManager;
import yf.com.yf_b2c.movie.net.MovieApi;
import yf.com.yf_b2c.movie.bean.HttpResult;
import yf.com.yf_b2c.movie.bean.Subject;

/**
 * Created by zhouzhan on 25/10/16.
 */
public class MovieModel extends BaseModel {

    private MovieApi movieApi;

    public MovieModel() {
        this.movieApi = ApiManager.getInstence().getConfigureApiService();
    }

    public Subscription getTopMovie(
            final int start,
            final int count,
            Subscriber<HttpResult<List<Subject>>> subscriber){
        Timber.e("getTopMovie");
        Observable<HttpResult<List<Subject>>> topMovie = movieApi.getTopMovie(start, count);
        return toSubscribe(topMovie, subscriber);
    }
}
