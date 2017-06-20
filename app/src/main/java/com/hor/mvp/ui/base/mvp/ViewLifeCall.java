package com.hor.mvp.ui.base.mvp;

import android.util.Log;

import com.hor.mvp.data.http.Http;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhuYiJun on 2017/2/8.
 * 辅助BasePresenter持有BaseView,释放BaseView
 */

public class ViewLifeCall<T extends BaseView> {
    private static final String TAG = "ViewLifeCall";

    private CompositeSubscription mCompositeSubscription;
    public T view;

    public ViewLifeCall() {
    }

    public ViewLifeCall(T view) {
        this.view = view;
    }

    public void attachView(T t) {
        if (mCompositeSubscription==null){
            mCompositeSubscription = new CompositeSubscription();
        }
        view = t;
    }

    public void detachView() {
        mCompositeSubscription.clear();
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
        view = null;
    }

    public T getView() {
        return view;
    }

    public boolean isViewActive() {
        return view != null && view.isActive();
    }

    /**
     * 新生成的订阅者，要统一管理起来，调用detachView()时取消订阅，防止Activity等的内存泄露
     */
    public Subscription addSubscription(Subscription subscription) {
        if (mCompositeSubscription != null){
            mCompositeSubscription.add(subscription);
        }else {
            Log.e(TAG, "addSubscription: is null");
        }
        return subscription;
    }

    public <C> Observable<C> preThread(Observable<C> o) {
        return Http.getInstance().preThread(o);
    }

    protected <B> Observable.Transformer<B, B> preThread() {
        return new Observable.Transformer<B, B>() {
            @Override
            public Observable<B> call(Observable<B> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
