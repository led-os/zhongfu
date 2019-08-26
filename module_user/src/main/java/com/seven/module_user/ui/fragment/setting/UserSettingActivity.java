package com.seven.module_user.ui.fragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserSettingActivity extends BaseTitleActivity {


    @BindView(R2.id.update_password)
    TextView updatePassword;
    @BindView(R2.id.lp_payment_password)
    TextView lpPaymentPassword;
    @BindView(R2.id.payment_account)
    TextView paymentAccount;
    @BindView(R2.id.cancel_account)
    TextView cancelAccount;
    @BindView(R2.id.invite_code)
    TextView invite_code;

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
        return R.layout.mu_activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_setting);
        UserEntity entity = new Gson().fromJson(SharedData.getInstance().getUserInfo(),UserEntity.class);
        invite_code.setText(entity.getInvite_code());
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

    @OnClick({R2.id.update_password, R2.id.lp_payment_password, R2.id.payment_account, R2.id.cancel_account,R2.id.invite_code})
    public void onViewClicked(View view) {
        if (view == updatePassword) {
            startActivity(new Intent(mContext, UserModifyPassWordActivity.class));
        } else if (view == lpPaymentPassword) {
            startActivity(new Intent(mContext, UserSetPayPassWordActivity.class));
        } else if (view == paymentAccount) {
            startActivity(new Intent(mContext, UserSetPayAccountActivity.class));
        } else if (view == cancelAccount) {
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
        }else if (view == invite_code){
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_UP);
        }
    }


}
