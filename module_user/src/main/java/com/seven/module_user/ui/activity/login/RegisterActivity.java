package com.seven.module_user.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.stextview.TypeFaceEdit;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.task.ActivityStack;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.presenter.common.ActUserPresenter;
import com.seven.lib_opensource.application.SevenApplication;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/28
 */

@Route(path = RouterPath.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseTitleActivity {

    @BindView(R2.id.mobile_et)
    public TypeFaceEdit mobileEt;
    @BindView(R2.id.sms_code_et)
    public TypeFaceEdit smsCodeEt;
    @BindView(R2.id.sms_send_btn)
    public RelativeLayout smsSendBtn;
    @BindView(R2.id.sms_send_tv)
    public TypeFaceView smsSendTv;
    @BindView(R2.id.invite_et)
    public TypeFaceEdit inviteEt;
    @BindView(R2.id.password_et)
    public TypeFaceEdit passwordEt;
    @BindView(R2.id.password_hide_btn)
    public RelativeLayout passwordHide;
    @BindView(R2.id.agreement_tv)
    public TypeFaceView agreementTv;

    private CountDownTimer timer;

    private ActUserPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText(R.string.register);

        initAgreement();
    }

    @Override
    protected void initBundleData(Intent intent) {

        presenter = new ActUserPresenter(this, this);

    }

    private void initAgreement() {

        String content = ResourceUtils.getText(R.string.hint_agreement_all);
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);

        String service = mContext.getString(R.string.hint_agreement);
        int serviceIndex = content.indexOf(service);
        try {
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    //todo 商城协议链接

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ContextCompat.getColor(SevenApplication.getInstance(), R.color.primary));
                    ds.setUnderlineText(false);
                }
            }, serviceIndex, serviceIndex + service.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        agreementTv.setText(spannable);
        agreementTv.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        agreementTv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
    }

    public void btClick(View view) {

        if (view.getId() == R.id.sms_send_btn) {

            if (TextUtils.isEmpty(mobileEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_mobile));
                return;
            }

            showLoadingDialog();
            presenter.sms(Constants.RequestConfig.SMS, mobileEt.getText().toString(), Constants.SMSConfig.REGISTER);
        }

        if (view.getId() == R.id.password_hide_btn) {
            passwordEt.setTransformationMethod(passwordHide.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
            passwordHide.setSelected(!passwordHide.isSelected());
            passwordEt.setSelection(passwordEt.getText().toString().length());
        }

        if (view.getId() == R.id.register_btn) {

            if (TextUtils.isEmpty(mobileEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_mobile));
                return;
            }

            if (TextUtils.isEmpty(smsCodeEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_sms_code));
                return;
            }

            if (TextUtils.isEmpty(inviteEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_invite_code));
                return;
            }

            if (TextUtils.isEmpty(passwordEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_password));
                return;
            }

            showLoadingDialog();
            presenter.register(Constants.RequestConfig.REGISTER, mobileEt.getText().toString(),
                    smsCodeEt.getText().toString(), passwordEt.getText().toString(), inviteEt.getText().toString());

        }

    }

    private void smsSendCode() {
        smsSendBtn.setClickable(false);
        smsSendBtn.setSelected(true);
        countDown();//todo 短信发送成功回调里面
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

            case Constants.RequestConfig.REGISTER:

                if (object == null) return;

                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.REGISTER, object));

                ActivityStack.getInstance().finishActivity(LoginActivity.class);
                onBackPressed();

                break;
        }
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

        dismissLoadingDialog();

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected void onDestroy() {
        if (timer != null)
            timer.cancel();
        super.onDestroy();
    }
}