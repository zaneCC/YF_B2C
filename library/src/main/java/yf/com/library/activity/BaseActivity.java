package yf.com.library.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import butterknife.ButterKnife;
import yf.com.library.presenter.BasePresenter;

/**
 * Created by zhouzhan on 24/10/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    private SparseArray<BasePresenter> presenters = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);

        context = this;
        initView();
    }

    protected <T extends BasePresenter> T addPresenter(int constractAction, T presenter){
        presenters.put(constractAction, presenter);
        return presenter;
    }

    protected <T extends BasePresenter> T getPresenter(int constractAction, Class<T> clazz){
        T retPresenter = (T)(presenters.get(constractAction)) ;
        return retPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < presenters.size(); i++){
            int keyAt = presenters.keyAt(i);
            BasePresenter basePresenter = presenters.get(keyAt);
            basePresenter.onDestroy();
        }
    }

    protected abstract @LayoutRes int setLayoutId();

    protected abstract void initView();
}
