package com.seven.mall.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.ui.dialog.CommonDialog;
import com.seven.lib_common.utils.PermissionUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.mall.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseAppCompatActivity {

    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private CommonDialog permissionDialog;

    @Override
    protected int getContentViewId() {
        independent=true;
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ScreenUtils.hideBar(this);
    }

    @Override
    protected void initBundleData(Intent intent) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initPermission();
            }
        }, Constants.TimeConfig.SPLASH_TIME);
    }

    private void initPermission() {

        if (!EasyPermissions.hasPermissions(mContext, perms)) {
            requestPermission(perms, Constants.PermissionConfig.SPLASH);
            return;
        }

        intentHome();
    }

    private void intentHome() {
        RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_HOME);
    }

    private void permissionDialog() {

        if (permissionDialog == null) {
            permissionDialog = new CommonDialog(this, R.style.Dialog,
                    new OnClickListener() {
                        @Override
                        public void onCancel(View v, Object... objects) {

                        }

                        @Override
                        public void onClick(View v, Object... objects) {
                            try {
                                PermissionUtils.GoToSetting(SplashActivity.this);
                                finish();
                            } catch (Exception e) {
                                ToastUtils.showToast(mContext, getString(R.string.hint_auto_jump_error));
                            }
                        }

                        @Override
                        public void dismiss() {

                        }
                    }, ResourceUtils.getText(R.string.permission));
        }

        if (!permissionDialog.isShowing())
            permissionDialog.show();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        super.onPermissionsGranted(requestCode, list);
        if (requestCode == Constants.PermissionConfig.SPLASH)
            intentHome();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        super.onPermissionsDenied(requestCode, list);
        if (requestCode == Constants.PermissionConfig.SPLASH)
            permissionDialog();
    }

    @Override
    public void onClick(View v) {

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
}
