package yf.com.library.rxUtil;

import rx.Subscriber;
import yf.com.library.BaseBizExceptionDeal;

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseBizExceptionDeal bizExceptionDeal;

    protected BaseSubscriber() {
        bizExceptionDeal = new BaseBizExceptionDeal();
    }

    @Override
    public void onStart() {
        // TODO 可用于判断网络是否连接
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
        doNext(t);
    }

    protected abstract void doNext(T t);

}
