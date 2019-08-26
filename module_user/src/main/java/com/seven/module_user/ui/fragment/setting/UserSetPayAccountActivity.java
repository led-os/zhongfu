package com.seven.module_user.ui.fragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.user.mine.PayAccountEntity;
import com.seven.lib_router.Variable;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xxxxxxH on 2019/4/26.
 */
@Route(path = RouterPath.ACTIVITY_ACCOUNT)
public class UserSetPayAccountActivity extends BaseTitleActivity {
    @BindView(R2.id.wechat_pay)
    EditText wechatPay;
    @BindView(R2.id.alipay)
    EditText alipay;

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
    protected int getLayoutId() {
        return R.layout.mu_activity_set_pay;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
            setTitleText(R.string.user_set_pay_account);
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {

    }


    @OnClick(R2.id.ok)
    public void onViewClicked() {
        if (wechatPay.getText().toString().isEmpty() && alipay.getText().toString().isEmpty()) {
            ToastUtils.showToast(mContext, "至少设置一种收款方式");
            return;
        }
        PayAccountEntity entity = new PayAccountEntity();
        entity.setAli_account(alipay.getText().toString());
        entity.setWx_account(wechatPay.getText().toString());
        ApiManager.setPayAccount(entity)
                .subscribe(new CommonObserver<BaseResult>() {
                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getCode() == 1) {
                            ToastUtils.showToast(mContext, "设置成功");
                            Variable.getInstance().setAliAccount(alipay.getText().toString());
                            Variable.getInstance().setWxAccount(wechatPay.getText().toString());
                            finish();
                        } else {
                            ToastUtils.showToast(mContext, baseResult.getMessage());
                        }
                    }
                });
    }
}
