package yf.com.yf_b2c.user.presenter;

import android.content.Context;

import rx.Observable;
import rx.Subscription;
import yf.com.library.rxUtil.BaseSubscriber;
import yf.com.yf_b2c.user.contract.LoginContract;
import yf.com.yf_b2c.user.bean.User;
import yf.com.yf_b2c.user.model.UserModel;

/**
 * Created by zhouzhan on 24/10/16.
 */
public class LoginPresenter extends LoginContract.UserLoginPresenter {

    public LoginPresenter(Context context, int contractKey) {
        super(context, contractKey);
        addModel(contractKey, new UserModel());
    }

    public void doLogin(){

        Observable<User> userObservable = getModel(contractAction, UserModel.class)
                .doLogin();

        Subscription subscription = null;
        switch (contractAction){
            case LoginContract.MainAction.MAIN_LOGIN:
                subscription = doMainLogin(userObservable);
                break;

            case LoginContract.UserAction.USER_LOGIN:
                subscription = doUserLogin(userObservable);
                break;
        }

        addSubscription(subscription);
    }

    private Subscription doUserLogin(Observable<User> userObservable) {
        return toSubscribe(userObservable, new BaseSubscriber<User>() {
            @Override
            protected void doNext(User user) {
                getView(contractAction, LoginContract.IUserLoginView.class).loginSuccess(user);
            }
        });
    }

    private Subscription doMainLogin(Observable<User> userObservable) {
        return toSubscribe(userObservable, new BaseSubscriber<User>() {
            @Override
            protected void doNext(User user) {
                getView(contractAction, LoginContract.IMainLoginView.class).loginSuccess(user);
            }
        });
    }


}
