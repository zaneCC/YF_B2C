package yf.com.yf_b2c.user.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;
import yf.com.library.activity.BaseActivity;
import yf.com.yf_b2c.R;
import yf.com.yf_b2c.movie.presenter.MoviePresenter;
import yf.com.yf_b2c.movie.bean.Subject;
import yf.com.yf_b2c.user.bean.User;
import yf.com.yf_b2c.user.contract.LoginContract;
import yf.com.yf_b2c.user.contract.MovieContract;
import yf.com.yf_b2c.user.presenter.LoginPresenter;

/**
 * Created by zhouzhan on 25/10/16.
 */
public class UserActivity extends BaseActivity implements LoginContract.IUserLoginView, MovieContract.IUserMovieView{

    @BindView(R.id.back)
    Button back;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView)
    TextView textView;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, UserActivity.class));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        addPresenter(LoginContract.UserAction.USER_LOGIN, new LoginPresenter(context, LoginContract.UserAction.USER_LOGIN))
                .addView(LoginContract.UserAction.USER_LOGIN, this);

        addPresenter(MovieContract.User.USER_MOVIE, new MoviePresenter(context, MovieContract.User.USER_MOVIE))
                .addView(MovieContract.User.USER_MOVIE, this);
    }

    @OnClick({
            R.id.button,
            R.id.back
    })
    public void click(View view){
        int id = view.getId();
        switch (id){
            case R.id.button:
                getPresenter(LoginContract.UserAction.USER_LOGIN, LoginPresenter.class).doLogin();
//                getPresenter(MovieContract.User.USER_MOVIE, MoviePresenter.class).getTopMovie();
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }

    @Override
    public void loginSuccess(User user) {
        textView.setText("welcome: " + user.firstName + user.lastName);
    }

    @Override
    public void loginFail(String msg) {
        textView.setText("error: " + msg);
    }

    @Override
    public void showMovies(List<Subject> movies) {
        for ( Subject subject : movies){
            Timber.e(subject.toString());
        }
    }
}
