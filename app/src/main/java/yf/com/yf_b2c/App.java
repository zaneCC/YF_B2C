package yf.com.yf_b2c;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by zhouzhan on 24/10/16.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /** 初始化日志*/
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
