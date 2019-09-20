package com.seven.module_user.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/7/5
 */

@Route(path = "")
public class CustomerServiceActivity extends BaseTitleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_customer_service;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText(R.string.user_customer_service);

    }

    @Override
    protected void initBundleData(Intent intent) {

    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

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
