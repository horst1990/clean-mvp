package com.hor.mvp.ui.user;

import com.hor.mvp.RxJavaTestHelper;
import com.hor.mvp.data.http.api.UserService;
import com.hor.mvp.model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Administrator on 2017/6/20.
 */
public class LoginPresenterTest {
    @Mock
    LoginContract.View mView;
    @Mock
    UserService mUserService;

    LoginContract.Presenter mPresenter;

    @Before
    public void setUp() throws Exception {
        RxJavaTestHelper.setSynchronize();

        MockitoAnnotations.initMocks(this);

        when(mView.isActive()).thenReturn(true);

        mPresenter = new LoginPresenter(mUserService);
        mPresenter.attachView(mView);
    }

    @Test
    public void testLogin(){
        User user = new User();

        when(mView.isActive()).thenReturn(true);
        when(mUserService.signIn(anyString())).thenReturn(Observable.just(user));

        mPresenter.login("");
        mPresenter.login("1234512345");
        mPresenter.login("1234512345**");

        verify(mView,new Times(3)).onLoginEnd(user);//验证View层onLoginEnd()方法会调用3次
        verify(mView,never()).test();//验证test()方法不会给调用
    }
}