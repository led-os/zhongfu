package com.seven.lib_common.base.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.github.zackratos.ultimatebar.UltimateBar;
import com.seven.lib_common.R;
import com.seven.lib_common.listener.LifeCycleListener;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_common.task.ActivityStack;
import com.seven.lib_common.base.dialog.LoadingDialog;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.blurview.Binder;
import com.seven.lib_common.widget.statubar.StatusBarUtil;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.event.MessageEvent;
import com.seven.lib_router.Variable;
import com.trello.rxlifecycle2.components.RxActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/25
 */
public abstract class BaseActivity extends RxActivity implements IBaseView, EasyPermissions.PermissionCallbacks {

    public int screenWidth;
    public int screenHeight;
    public int notificationHeight;
    public int navigationBarHeight;

    public boolean transparency;

    protected Context mContext;
    protected Unbinder unBinder;

    private CheckPermListener permListener;
    protected static final int RC_PERM = 100;

    public boolean isRequest;

    private LoadingDialog loadingDialog;

    protected StatusBar statusBar;
    protected boolean knife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        ActivityStack.getInstance().push(this);
        setContentView(getContentViewId());
        mContext = this;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        notificationHeight = ScreenUtils.getStatusBarHeight(mContext);
        navigationBarHeight = ScreenUtils.getNavigationBarHeight(mContext);

        if (!knife)
            unBinder = ButterKnife.bind(this);

        initLoadingDialog();

        init(savedInstanceState);

        initBundleData(null);

        setStatusBar();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (statusBar == StatusBar.LIGHT)
                StatusBarUtil.setLightMode(this);
            else if (statusBar == StatusBar.DARK)
                StatusBarUtil.setDarkMode(this);
            else if (statusBar == StatusBar.HIDE)
                UltimateBar.Companion.with(this)
                        .applyNavigation(true)
                        .create()
                        .hideBar();
            else if (statusBar == StatusBar.PRIMARY)
                UltimateBar.Companion.with(this)
                        .statusDrawable(new ColorDrawable(ContextCompat.getColor(mContext,R.color.primary)))
                        .applyNavigation(true)
                        .navigationDrawable(new ColorDrawable(ContextCompat.getColor(mContext,R.color.primary)))
                        .create()
                        .drawableBar();
            else
                UltimateBar.Companion.with(this).applyNavigation(false)
                        .statusDark(true)
                        .statusDrawable2(new ColorDrawable(Color.parseColor("#33000000")))
                        .create()
                        .immersionBar();
        } else
            UltimateBar.Companion.with(this)
                    .statusDrawable(new ColorDrawable(ContextCompat.getColor(mContext,R.color.primary)))
                    .applyNavigation(true)
                    .navigationDrawable(new ColorDrawable(ContextCompat.getColor(mContext,R.color.primary)))
                    .create()
                    .drawableBar();
    }

    protected void isNetwork() {

        isRequest = NetWorkUtils.isNetWork();

        if (!isRequest) {
            ToastUtils.showToast(SSDK.getInstance().getContext(), ResourceUtils.getText(R.string.error_network));
        }
    }

    private void initLoadingDialog() {
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            loadingDialog = new LoadingDialog(this, R.style.Dialog, null);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            Binder.onResume();
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
        dismissLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //remove view bind
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityStack.getInstance().remove(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {

        //some permissions have been granted

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {

        //some permissions have been denied

    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initBundleData(Intent intent);

    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }

    protected <T> T getView(T view, int id) {
        if (null == view) {
            view = (T) this.findViewById(id);
        }

        return view;
    }

    protected <T> T getView(View parent, T view, int id) {
        if (null == view) {
            view = (T) parent.findViewById(id);
        }

        return view;
    }

    public void switchFragment(int id_content, Fragment from, Fragment to) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (from == null) {
            transaction.add(id_content, to);
        } else {
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(id_content, to); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to); // 隐藏当前的fragment，显示下一个
            }
        }
        transaction.commit();
    }

    public interface CheckPermListener {
        //The method of callback after permission is passed
        void agreeAllPermission();
    }

    public void checkPermission(CheckPermListener listener, String resString, String... mPerms) {
        permListener = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (permListener != null)
                permListener.agreeAllPermission();
        } else {
            EasyPermissions.requestPermissions(this, resString,
                    RC_PERM, mPerms);
        }
    }

    protected View getNetworkView(RecyclerView recyclerView) {
        View view = getLayoutInflater().inflate(R.layout.lb_empty_network_view, (ViewGroup) recyclerView.getParent(), false);
        return view;
    }


    protected boolean isLogin() {

        if (TextUtils.isEmpty(Variable.getInstance().getToken())) {

            EventBus.getDefault().post(new MessageEvent(SSDK.getInstance().getsConfig().getEventCode(), ""));

            return false;
        }
        return true;
    }

    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE;//0x00001000; // SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }

        try {
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    protected enum StatusBar {
        LIGHT, DARK, HIDE, PRIMARY
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

    public void animLeftOut() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    public void animRightOut() {
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    public void animTopOut() {
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }

    public void animBottomOut() {
        overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }

    public void animAlphaIn() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
