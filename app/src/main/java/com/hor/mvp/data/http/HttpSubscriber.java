package com.hor.mvp.data.http;


import com.hor.mvp.BuildConfig;
import com.hor.mvp.R;
import com.hor.mvp.ui.base.mvp.ViewLifeCall;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by zhuYiJun on 2017/2/8.
 */

public abstract class HttpSubscriber<T> extends Subscriber<T> {
    private ViewLifeCall mViewLifeCall;
    private boolean isControlLoadView = true;
    private boolean isShowApiE = true;

    public HttpSubscriber(ViewLifeCall viewLifeCall) {
        mViewLifeCall = viewLifeCall;
    }

    public HttpSubscriber(ViewLifeCall viewLifeCall, boolean isControlLoadView) {
        mViewLifeCall = viewLifeCall;
        this.isControlLoadView = isControlLoadView;
    }

    public HttpSubscriber(ViewLifeCall viewLifeCall, boolean isControlLoadView, boolean isShowApiE) {
        mViewLifeCall = viewLifeCall;
        this.isControlLoadView = isControlLoadView;
        this.isShowApiE = isShowApiE;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (mViewLifeCall.isViewActive()) {
            mViewLifeCall.getView().hiddenLoading();
            if (e instanceof ApiException) {
                String error = ((ApiException) e).getReason();
                String code = ((ApiException) e).getCode();

                if (isShowApiE) mViewLifeCall.getView().showAlert(error);

            } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
                mViewLifeCall.getView().showAlert(R.string.net_error);
            } else {
                String error = getErrorMsg(e);
                if (BuildConfig.DEBUG) {
                    mViewLifeCall.getView().showAlert(error);
                } else {
                    mViewLifeCall.getView().showAlert(R.string.error);
                }
            }
        }
    }

    private String getErrorMsg(Throwable e) {
        if (e == null || e.getLocalizedMessage() == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(e.getLocalizedMessage());
        if (e.getStackTrace() != null) {
            for (int i = 0; i < e.getStackTrace().length; i++) {
                if (i > 1) break;
                sb.append("\n");
                StackTraceElement ele = e.getStackTrace()[i];
                sb.append(ele.getClassName()).append("-->");
                sb.append(ele.getMethodName()).append("():");
                sb.append(ele.getLineNumber());
            }
        }
        return sb.toString();
    }


    @Override
    public void onNext(T t) {
        if (mViewLifeCall.isViewActive()) {
            onData(t);
        }
    }

    public abstract void onData(T data);
}
