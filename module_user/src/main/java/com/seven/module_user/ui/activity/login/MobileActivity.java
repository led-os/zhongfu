package com.seven.module_user.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.stextview.TypeFaceEdit;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/28
 */
@Route(path = RouterPath.ACTIVITY_MOBILE)
public class MobileActivity extends BaseTitleActivity {

    @BindView(R2.id.mobile_et)
    public TypeFaceEdit mobileEt;
    @BindView(R2.id.sms_code_et)
    public TypeFaceEdit smsCodeEt;
    @BindView(R2.id.sms_send_btn)
    public RelativeLayout smsSendBtn;
    @BindView(R2.id.sms_send_tv)
    public TypeFaceView smsSendTv;

    private CountDownTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_mobile;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText(R.string.mobile_bind);

    }

    @Override
    protected void initBundleData(Intent intent) {

    }

    public void btClick(View view) {

        if (view.getId() == R.id.sms_send_btn) {
            //todo presenter
            smsSendCode();
        } else if (view.getId() == R.id.submit_btn) {

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
