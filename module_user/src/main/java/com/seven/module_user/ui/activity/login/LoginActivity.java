package com.seven.module_user.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.stextview.TypeFaceEdit;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.presenter.common.ActUserPresenter;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/26
 */
@Route(path = RouterPath.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity {

    @BindView(R2.id.mobile_et)
    public TypeFaceEdit mobileEt;
    @BindView(R2.id.password_et)
    public TypeFaceEdit passwordEt;
    @BindView(R2.id.password_hide_btn)
    public RelativeLayout passwordHide;

    private ActUserPresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.mu_activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initBundleData(Intent intent) {

        presenter = new ActUserPresenter(this, this);

    }

    public void btClick(View view) {

        if (view.getId() == R.id.close_btn)
            onBackPressed();
        else if (view.getId() == R.id.password_hide_btn) {
            passwordEt.setTransformationMethod(passwordHide.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
            passwordHide.setSelected(!passwordHide.isSelected());
            passwordEt.setSelection(passwordEt.getText().toString().length());
        } else if (view.getId() == R.id.login_btn)
            login();
        else if (view.getId() == R.id.forget_btn)
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_PASSWORD);
        else if (view.getId() == R.id.register_btn)
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_REGISTER);
    }

    private void login() {
//        RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MOBILE);

        if (TextUtils.isEmpty(mobileEt.getText().toString())) {
            ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_mobile));
            return;
        }

        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_password));
            return;
        }

        showLoadingDialog();
        presenter.login(Constants.RequestConfig.LOGIN, mobileEt.getText().toString(), passwordEt.getText().toString());

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {

            case Constants.RequestConfig.LOGIN:

                if(object==null)return;

                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.LOGIN, object));

                finish();

                break;

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

        dismissLoadingDialog();

    }

    @Override
    public void showToast(String msg) {

    }
}
