package com.hor.mvp.data.http;
/**
 * Created by zhuYiJun on 2017/3/22.
 */

public class RxFuture<T>{
    boolean isOk;
    T data;
    Object tag;

    public RxFuture(T data) {
        this.data = data;
    }

    public RxFuture(boolean isOk, T data) {
        this.isOk = isOk;
        this.data = data;
    }

    public RxFuture(boolean isOk, T data, Object tag) {
        this.isOk = isOk;
        this.data = data;
        this.tag = tag;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
