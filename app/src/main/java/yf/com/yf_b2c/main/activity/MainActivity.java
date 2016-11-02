package yf.com.yf_b2c.main.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;
import yf.com.library.activity.BaseActivity;
import yf.com.yf_b2c.R;
import yf.com.yf_b2c.user.activity.UserActivity;
import yf.com.yf_b2c.user.contract.LoginContract;
import yf.com.yf_b2c.user.bean.User;
import yf.com.yf_b2c.user.presenter.LoginPresenter;

public class MainActivity extends BaseActivity implements LoginContract.IMainLoginView{

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.go_user)
    Button go_user;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        addPresenter(LoginContract.MainAction.MAIN_LOGIN, new LoginPresenter(context, LoginContract.MainAction.MAIN_LOGIN))
                .addView(LoginContract.MainAction.MAIN_LOGIN, this);

        textView.setText("HELLO");
    }

    @OnClick({R.id.button, R.id.go_user})
    public void click(View view){
        int id = view.getId();
        switch (id){
            case R.id.button:
                getPresenter(LoginContract.MainAction.MAIN_LOGIN, LoginPresenter.class).doLogin();
                break;

            case R.id.go_user:
                UserActivity.startActivity(context);
                break;

            default:{
                Timber.e("default");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        textView.setText("hello");
    }

    @Override
    public void loginSuccess(User user) {
        textView.setText("login success: " + user.firstName + user.lastName);
    }
}
