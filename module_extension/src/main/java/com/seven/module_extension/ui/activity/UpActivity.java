package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_model.model.extension.LevelEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.dialog.ShareDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxxxxxH on 2019/5/16.
 */
@Route(path = RouterPath.ACTIVITY_UP)
public class UpActivity extends BaseTitleActivity {

    @BindView(R2.id.me_upgrade_code)
    TypeFaceView meUpgradeCode;
    @BindView(R2.id.me_upgrade_tv)
    TypeFaceView meUpgradeTv;

    ExActivityPresenter presenter;
    private ShareDialog dialog;

    @Override
    protected int getLayoutId() {
        isRightTextBtn = true;
        setRightTextTv(R.string.me_share);
        return R.layout.me_acitivity_upgrade;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("推广升级");

        presenter = new ExActivityPresenter(this,this);
        presenter.level(1,3);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1){
            if (object != null){
                LevelEntity entity = (LevelEntity) object;
                if (entity != null){
                    meUpgradeTv.setText(entity.getContent()!=null?Html.fromHtml(entity.getContent()) :"");
                    UserEntity entity1 = new Gson().fromJson(SharedData.getInstance().getUserInfo(),UserEntity.class);
                    meUpgradeCode.setText(entity1.getInvite_code());
                }
            }
        }
    }

    @Override
    protected void rightTextBtnClick(View v) {
        if (dialog == null){
            dialog = new ShareDialog(UpActivity.this, R.style.Dialog, null);
        }
        if (!dialog.isShowing())
            dialog.showDialog(0, -screenHeight);
    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
