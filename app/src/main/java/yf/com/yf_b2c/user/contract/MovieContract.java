package yf.com.yf_b2c.user.contract;

import java.util.List;

import yf.com.library.view.BaseView;
import yf.com.yf_b2c.movie.bean.Subject;

/**
 * Created by zhouzhan on 25/10/16.
 */

public interface MovieContract {

    interface IUserMovieView extends BaseView{
        void showMovies(List<Subject> movies);
    }

    interface User{
        int USER = 10000;
        int USER_MOVIE = USER + 1;
    }

}
