package com.hor.mvp.ui.user;

import android.os.Bundle;
import android.widget.TextView;

import com.hor.mvp.R;
import com.hor.mvp.model.User;
import com.hor.mvp.ui.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Action1;


public class MainActivity extends BaseActivity implements LoginContract.View {

    @Bind(R.id.textView)
    TextView textView;
    private LoginContract.Presenter mPresenter;

    @Override
    public void setTitle(TextView tv) {
        tv.setText(R.string.app_name);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

        showLoading();
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                mPresenter.login("1");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onLoginEnd(User u) {
        hiddenLoading();
        textView.setText(u.getName());
    }

    @Override
    public void test() {

    }

}
