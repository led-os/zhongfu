package com.seven.lib_common.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.lib_common.R;
import com.seven.lib_common.listener.LifeCycleListener;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_common.base.dialog.LoadingDialog;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.event.MessageEvent;
import com.seven.lib_router.Variable;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/27
 */

public abstract class BaseFragment extends RxFragment implements IBaseView {

    public int screenWidth;
    public int screenHeight;

    public int notificationHeight;
    public int navigationBarHeight;

    protected View mRoot;
    protected Unbinder unBinder;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = LayoutInflater.from(getActivity()).inflate(getContentViewId(), container, false);
        unBinder = ButterKnife.bind(this, mRoot);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        notificationHeight = ScreenUtils.getStatusBarHeight(getActivity());
        navigationBarHeight = ScreenUtils.getNavigationBarHeight(getActivity());

        initLoadingDialog();

        init(savedInstanceState);
        return mRoot;
    }

    private void initLoadingDialog() {
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            loadingDialog = new LoadingDialog(getActivity(), R.style.Dialog, null);
        }
    }

    protected void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing() && NetWorkUtils.isNetWork())
            loadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }


    public abstract int getContentViewId();

    public abstract void init(Bundle savedInstanceState);

    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //remove view bind
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    protected <T> T getView(T view, int id) {
        if (null == view) {
            view = (T) this.mRoot.findViewById(id);
        }

        return view;
    }

    protected <T> T getView(View parent, T view, int id) {
        if (null == view) {
            view = (T) parent.findViewById(id);
        }

        return view;
    }

    @Override
    public void result(int code, Object object) {

    }

    @Override
    public void result(int code, Boolean hasNextPage, Object object) {

    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint())
            onVisible();
        else
            onInvisible();

    }

    /**
     * 可见
     */
    protected void onVisible() {
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    protected boolean isLogin() {

        if (TextUtils.isEmpty(Variable.getInstance().getToken())) {

            EventBus.getDefault().post(new MessageEvent(SSDK.getInstance().getsConfig().getEventCode(), ""));

            return false;
        }
        return true;
    }

}
