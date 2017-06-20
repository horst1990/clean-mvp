package com.hor.mvp.ui.user;

import com.hor.mvp.model.User;
import com.hor.mvp.ui.base.mvp.BasePresenter;
import com.hor.mvp.ui.base.mvp.BaseView;

/**
 * Created by Administrator on 2017/6/20.
 */

public class LoginContract {
    interface View extends BaseView{
        void onLoginEnd(User u);
        void test();
    }
    interface Presenter extends BasePresenter<View>{
        void login(String uid);
    }
}
