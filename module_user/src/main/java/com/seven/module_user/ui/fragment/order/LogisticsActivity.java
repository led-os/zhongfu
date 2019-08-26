package com.seven.module_user.ui.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxxxxxH on 2019/5/15.
 */
@Route(path = RouterPath.ACTIVITY_LOGISTICS)
public class LogisticsActivity extends BaseTitleActivity {
    @Autowired(name = "orderId")
    int orderId = 0;
    @BindView(R2.id.mu_web)
    WebView muWeb;
    String url = "http://mobile.zf.fqwlkj.com.cn/express.html?order_id=";

    @Override
    protected int getLayoutId() {
        return R.layout.mu_acitivty_logistics;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        statusBar = StatusBar.LIGHT;
        setTitleText("物流查询");
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        if (intent == null) intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        muWeb.loadUrl(url + orderId);
        WebSettings settings = muWeb.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
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
    protected void onDestroy() {
        super.onDestroy();
       
    }
}
