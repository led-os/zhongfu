package com.seven.module_user.ui.fragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.stextview.TypeFaceEdit;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.model.user.mine.ResetPasswordEntity;
import com.seven.lib_model.presenter.common.ActUserPresenter;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xxxxxxH on 2019/4/26.
 */
@Route(path = RouterPath.ACTIVITY_PAY_PASSWORD)
public class UserSetPayPassWordActivity extends BaseTitleActivity {
    @BindView(R2.id.mobile_et)
    TypeFaceEdit mobileEt;
    @BindView(R2.id.sms_code_et)
    TypeFaceEdit smsCodeEt;
    @BindView(R2.id.sms_send_tv)
    TypeFaceView smsSendTv;
    @BindView(R2.id.sms_send_btn)
    RelativeLayout smsSendBtn;
    @BindView(R2.id.password_et)
    TypeFaceEdit passwordEt;
    @BindView(R2.id.password_hide_btn)
    RelativeLayout passwordHideBtn;
    @BindView(R2.id.submit_btn)
    RelativeLayout submitBtn;


    private CountDownTimer timer;

    private ActUserPresenter presenter;
    private UserEntity userEntity;

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

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_set_pay_password);
        userEntity = new Gson().fromJson(SharedData.getInstance().getUserInfo(), UserEntity.class);
        passwordHideBtn.setVisibility(View.GONE);
        mobileEt.setFocusable(false);
        mobileEt.setFocusableInTouchMode(false);
        mobileEt.setText(userEntity.getPhone());
        passwordEt.setHint("请输入6位交易密码");
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        presenter = new ActUserPresenter(this, this);

    }

    @OnClick(R2.id.sms_send_btn)
    void smsSend() {
        showLoadingDialog();
        presenter.sms(Constants.RequestConfig.SMS, mobileEt.getText().toString(), Constants.SMSConfig.PAY_PASSWORD);
    }

    private void smsSendCode() {
        smsSendBtn.setClickable(false);
        smsSendBtn.setSelected(true);
        countDown();
    }

    private void countDown() {
        if (timer == null) {
            timer = new CountDownTimer(60 * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    String mill = String.valueOf(millisUntilFinished);

                    if (mill.length() == 5) {//10s up
                        if (smsSendTv == null) return;
                        smsSendTv.setText(ResourceUtils.getFormatText(
                                R.string.surplus_time, String.valueOf(millisUntilFinished).substring(0, 2)));
                    } else if (mill.length() == 4) {//10s down
                        if (smsSendTv == null) return;
                        smsSendTv.setText(ResourceUtils.getFormatText(
                                R.string.surplus_time, String.valueOf(millisUntilFinished).substring(0, 1)));
                    }
                }

                @Override
                public void onFinish() {
                    if (smsSendBtn == null || smsSendTv == null) return;
                    smsSendBtn.setClickable(true);
                    smsSendBtn.setSelected(false);
                    smsSendTv.setText(ResourceUtils.getText(R.string.sms_code));
                }
            };
        }
        timer.start();
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        switch (code) {
            case Constants.RequestConfig.SMS:
                smsSendCode();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null)
            timer.cancel();
        super.onDestroy();
    }

    @OnClick(R2.id.submit_btn)
    void modify() {
        if (smsCodeEt.getText().toString().isEmpty()) {
            smsCodeEt.setError("请输入验证码");
            smsCodeEt.requestFocus();
            return;
        }
        if (passwordEt.getText().toString().isEmpty()) {
            passwordEt.setError("请输入支付密码");
            passwordEt.requestFocus();
            return;
        }
        ResetPasswordEntity entity = new ResetPasswordEntity();
        entity.setPhone(userEntity.getPhone());
        entity.setCode(smsCodeEt.getText().toString());
        entity.setPassword(passwordEt.getText().toString());
        ApiManager.modifyPayPassword(entity)
                .subscribe(new CommonObserver<BaseResult>() {
                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getCode() == 1) {
                            ToastUtils.showToast(mContext, "修改成功");
                            Variable.getInstance().setPayPassword(true);
                            finish();
                        }else {
                            ToastUtils.showToast(mContext, baseResult.getMessage());
                        }
                    }
                });
    }
}
