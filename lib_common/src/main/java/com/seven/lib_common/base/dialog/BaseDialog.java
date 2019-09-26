package com.seven.lib_common.base.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/30
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener, IBaseView {

    public int screenWidth;
    public int screenHeight;
    public int notificationHeight;
    public int navigationBarHeight;

    protected static final int RC_PERM = 123;

    //this context must is activity
    protected Activity activity;

    //click on the shadow region to dismiss
    protected boolean isTouch;

    protected com.seven.lib_common.listener.OnClickListener listener;

    public BaseDialog(Activity activity) {
        super(activity);
    }

    public BaseDialog(Activity activity, int theme) {
        super(activity, theme);
        this.activity = activity;

    }

    public BaseDialog(Activity activity, int theme,com.seven.lib_common.listener.OnClickListener listener) {

        this(activity, theme);

        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(getRootLayoutId());

        } catch (Exception e) {
            Logger.e(" Base dialog setContentView exception " + e);
            return;
        }

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        notificationHeight = ScreenUtils.getStatusBarHeight(SSDK.getInstance().getContext());
        navigationBarHeight = ScreenUtils.getNavigationBarHeight(SSDK.getInstance().getContext());

        setCanceledOnTouchOutside(isTouch);

        onInitRootView(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void show() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = 0;

        Point size = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            size = new Point();
            wm.getDefaultDisplay().getSize(size);
        } else {
            width = wm.getDefaultDisplay().getWidth() * 9 / 10;
        }

        if (activity != null && !activity.isFinishing())
            super.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            getWindow().setLayout((size.x * 9 / 10),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public abstract int getRootLayoutId();

    public abstract void onInitRootView(Bundle savedInstanceState);

    public abstract void initView();

    public abstract void initData();

    protected <T> T getView(T view, int id) {
        if (null == view) {
            view = (T) this.findViewById(id);
        }

        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(String msg) {

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
}
