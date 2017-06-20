package com.hor.mvp.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hor.mvp.R;
import com.hor.mvp.ui.base.mvp.BaseView;

import butterknife.ButterKnife;

/**
 * Created by hor on 2016/3/25
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, BaseFragment.OnFragmentInteractionListener {

    private ProgressBar mProgressBar;

    protected BroadcastReceiver mReceiver = null;//多界面通信
    protected IntentFilter mIntentFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentLayout());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before);
        setTitle((TextView) toolbar.findViewById(R.id.toolbar_title));

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mProgressBar = (ProgressBar) getLayoutInflater().inflate(R.layout.view_progressbar, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
        contentView.addView(mProgressBar,layoutParams);
        hiddenLoading();

        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null && mIntentFilter != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm != null && fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showAlert(@StringRes int strR) {
        new AlertDialog.Builder(this).setMessage(strR).setPositiveButton(R.string.sure,null).show();
    }

    @Override
    public void showAlert(String str) {
        new AlertDialog.Builder(this).setMessage(str).setPositiveButton(R.string.sure,null).show();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    //region about pages event
    public void initBroadcastReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        mReceiver = receiver;
        mIntentFilter = filter;
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mIntentFilter);
    }
    //endregion

    @Override
    public boolean isActive() {
        return !isDestroyed();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    //fragment 通信 Activity
    public void onFragmentInteraction(Object o){}
}
