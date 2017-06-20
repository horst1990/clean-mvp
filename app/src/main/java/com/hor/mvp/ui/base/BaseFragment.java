package com.hor.mvp.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hor.mvp.ui.base.mvp.BaseView;

import butterknife.ButterKnife;

/**
 * Created by hor on 2016/3/26.
 */
public abstract class BaseFragment extends Fragment implements BaseView{

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private OnFragmentInteractionListener mListener;

    protected BroadcastReceiver mReceiver = null;//多界面通信
    protected IntentFilter mIntentFilter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater(savedInstanceState).inflate(getContentLayout(),null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setTitle(TextView tv) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null && mIntentFilter != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void showAlert(@StringRes int strR) {
        if (getActivity() instanceof BaseView){
            BaseView b = (BaseView) getActivity();
            b.showAlert(strR);
        }
    }

    @Override
    public void showAlert(String str) {
        if (getActivity() instanceof BaseView){
            BaseView b = (BaseView) getActivity();
            b.showAlert(str);
        }
    }

    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseView){
            BaseView b = (BaseView) getActivity();
            b.showLoading();
        }
    }

    @Override
    public void hiddenLoading() {
        if (getActivity() instanceof BaseView){
            BaseView b = (BaseView) getActivity();
            b.hiddenLoading();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    protected interface OnFragmentInteractionListener {
        @SuppressWarnings("unused")
        void onFragmentInteraction(Object o);
    }

    //region about pages event
    public void initBroadcastReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        mReceiver = receiver;
        mIntentFilter = filter;
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, mIntentFilter);
    }
    //endregion
}
