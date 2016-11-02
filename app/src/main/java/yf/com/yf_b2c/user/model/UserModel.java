package yf.com.yf_b2c.user.model;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import yf.com.library.model.BaseModel;
import yf.com.yf_b2c.user.bean.User;

/**
 * Created by zhouzhan on 24/10/16.
 */
public class UserModel extends BaseModel {

    public Observable<User> doLogin(){
        Timber.e("doLogin");

        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                // 模拟执行网络操作
                try {
                    User user = new User("zhou", "zhan");
                    Thread.sleep(3000);
                    subscriber.onNext(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
