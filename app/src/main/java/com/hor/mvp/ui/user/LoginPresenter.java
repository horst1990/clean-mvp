package com.hor.mvp.ui.user;

import com.hor.mvp.data.http.Http;
import com.hor.mvp.data.http.HttpSubscriber;
import com.hor.mvp.data.http.api.UserService;
import com.hor.mvp.model.User;
import com.hor.mvp.ui.base.mvp.ViewLifeCall;

/**
 * Created by Administrator on 2017/6/20.
 */

public class LoginPresenter extends ViewLifeCall<LoginContract.View> implements LoginContract.Presenter {
    private UserService userService;

    public LoginPresenter() {
        userService = Http.create(UserService.class);
    }

    public LoginPresenter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void login(String uid) {
        addSubscription(userService.signIn(uid).compose(this.<User>preThread()).subscribe(new HttpSubscriber<User>(this) {
            @Override
            public void onData(User data) {
                getView().onLoginEnd(data);
            }
        }));
    }
}
