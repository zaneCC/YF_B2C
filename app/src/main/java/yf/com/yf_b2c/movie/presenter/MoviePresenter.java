package yf.com.yf_b2c.movie.presenter;

import android.content.Context;

import java.util.List;

import rx.Subscription;
import yf.com.library.presenter.BasePresenter;
import yf.com.library.rxUtil.BaseSubscriber;
import yf.com.yf_b2c.movie.bean.HttpResult;
import yf.com.yf_b2c.movie.bean.Subject;
import yf.com.yf_b2c.movie.model.MovieModel;
import yf.com.yf_b2c.user.contract.MovieContract;

/**
 * Created by zhouzhan on 25/10/16.
 */
public class MoviePresenter extends BasePresenter {

    public MoviePresenter(Context context, int contractKey) {
        super(context, contractKey);
        addModel(contractKey, new MovieModel());
    }

    public void getTopMovie() {
        Subscription subscription = getModel(contractAction, MovieModel.class)
                .getTopMovie(0, 10, new BaseSubscriber<HttpResult<List<Subject>>>() {
                    @Override
                    protected void doNext(HttpResult<List<Subject>> listHttpResult) {

                        getView(contractAction, MovieContract.IUserMovieView.class)
                                .showMovies(listHttpResult.getSubjects());
                    }
                });

        addSubscription(subscription);
    }
}
