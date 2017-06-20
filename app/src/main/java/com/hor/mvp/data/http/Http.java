package com.hor.mvp.data.http;

import com.hor.mvp.BuildConfig;
import com.hor.mvp.data.http.converter.FastJsonConverterFactory;
import com.hor.mvp.data.http.logger.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by liukun on 16/3/9.
 */
public class Http {
        private static String base_url = "http://119.23.31.224:8080/";

    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;

    public Http() {
        init();
    }

    public void init() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Http INSTANCE = new Http();
    }

    //获取单例
    public static Http getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static <T> T create(Class<T> service) {
        return getInstance().retrofit.create(service);
    }

    public <T> Observable<T> preThread(Observable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
