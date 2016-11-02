package yf.com.yf_b2c.user.contract;

import android.content.Context;

import yf.com.library.presenter.BasePresenter;
import yf.com.library.view.BaseView;
import yf.com.yf_b2c.app.BaseAction;

/**
 * Created by zhouzhan on 24/10/16.
 */
public interface LoginContract extends BaseAction {

    /********************* VIEW **********************/

    interface IMainLoginView extends BaseView {
        void loginSuccess(yf.com.yf_b2c.user.bean.User user);
    }

    interface IUserLoginView extends BaseView {
        void loginSuccess(yf.com.yf_b2c.user.bean.User user);
        void loginFail(String msg);
    }

    /********************* PRESENTER **********************/

    abstract class UserLoginPresenter extends BasePresenter{

        public UserLoginPresenter(Context context, int contractKey) {
            super(context, contractKey);
        }
    }

    /********************* ACTION **********************/

    interface MainAction {
        int MAIN = LOGIN + 1000;
        int MAIN_LOGIN = MAIN + 1;
    }

    interface UserAction {
        int USER = LOGIN + 2000;
        int USER_LOGIN = USER + 1;
    }

}
