package com.hor.mvp.ui.base.mvp;

/**
 * Created by ARX on 2016/12/2.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T t);
    void detachView();
}
