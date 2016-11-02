package yf.com.library.presenter;

import android.content.Context;
import android.util.SparseArray;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import yf.com.library.model.BaseModel;
import yf.com.library.view.BaseView;

/**
 * Created by zhouzhan on 24/10/16.
 */
public abstract class BasePresenter {

    private CompositeSubscription compositeSubscription;
    protected int contractAction;

    protected Context context;
    protected SparseArray<BaseView> views = new SparseArray<>();
    protected SparseArray<BaseModel> models = new SparseArray<>();

    public BasePresenter(Context context, int contractAction) {
        this.context = context;
        this.contractAction = contractAction;
    }

    public void addView(int viewKey, BaseView view){
        views.put(viewKey,view);
    }

    protected <T extends BaseView> T getView(int viewKey, Class<T> clazz){
        return (T)(views.get(viewKey)) ;
    }

    protected void addModel(int modelKey, BaseModel model){
        models.put(modelKey, model);
    }

    protected <T extends BaseModel> T getModel(int modelKey, Class<T> clazz){
        return (T)(models.get(modelKey)) ;
    }

    protected void addSubscription(Subscription s) {
        if (s == null)
            return;

        if (null == this.compositeSubscription) {
            this.compositeSubscription = new CompositeSubscription();
        }
        this.compositeSubscription.add(s);
    }

    public void unsubcrible() {
        Timber.d("BaseApiPresenter::unsubscribe");
        if (this.compositeSubscription != null) {
            Timber.d("BaseApiPresenter::unsubscribe action");
            this.compositeSubscription.unsubscribe();
        }
    }

    public Subscription toSubscribe(Observable o, Subscriber s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public CompositeSubscription getCompositeSubscription() {
        if (this.compositeSubscription == null) {
            this.compositeSubscription = new CompositeSubscription();
        }

        return this.compositeSubscription;
    }

    public void onDestroy(){
        unsubcrible();
    }
}
