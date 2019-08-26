package com.seven.mall.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.base.activity.BaseAppCompatTitleActivity;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.mall.R;
import com.seven.mall.ui.fragment.WebFragment;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/12
 */
@Route(path = RouterPath.ACTIVITY_WEB)
public class WebActivity extends BaseAppCompatActivity {

    @Autowired(name = Constants.BundleConfig.URL)
    public String url;

    private WebFragment webFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initBundleData(Intent intent) {

        if (TextUtils.isEmpty(url)) return;

        webFragment = new WebFragment(url);

        switchFragment(R.id.web_container, null, webFragment);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webFragment.getView().canGoBack()) {
            webFragment.getView().goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
