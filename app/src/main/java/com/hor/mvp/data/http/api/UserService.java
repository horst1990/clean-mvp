package com.hor.mvp.data.http.api;

import com.hor.mvp.model.User;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static com.hor.mvp.data.http.params.HttpParams.UID;

/**
 * Created by ARX on 2017/2/6.
 */

@SuppressWarnings("SpellCheckingInspection")
public interface UserService {
    @POST("health")
    Observable<String> debug();

    //登陆
    @POST("user")
    Observable<User> signIn(@Query(UID) String uid);

}
