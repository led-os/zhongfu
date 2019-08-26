package com.seven.module_extension.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.pay.PhoneUtils;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xxxxxxH on 2019/5/14.
 */
@Route(path = RouterPath.ACTIVITY_BIND)
public class BindActivity extends BaseTitleActivity {
    @Autowired(name = "id")
    String id = "";
    @BindView(R2.id.me_bind_et)
    EditText meBindEt;
    @BindView(R2.id.me_bind_btn)
    Button meBindBtn;
    ExActivityPresenter presenter;
    AlertDialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_bind;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("名额设置");
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
        id = intent.getStringExtra("id");
        presenter = new ExActivityPresenter(this, this);
    }


    private void request(String phone, String id) {
        presenter.bind(1, phone, id);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (dialog.isShowing())
                dialog.dismiss();
            ToastUtils.showToast(mContext, "绑定成功");
        }
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

    private void confirmDialog(final String phoneNum){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("一旦设置成功不可修改，请确认手机号为" + "\n" + phoneNum);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request(phoneNum,id);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @OnClick(R2.id.me_bind_btn)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(meBindEt.getText().toString())){
            if (PhoneUtils.isMobileNO(meBindEt.getText().toString())){
                confirmDialog(meBindEt.getText().toString());
            }else {
                ToastUtils.showToast(mContext,"请输入正确手机号");
            }
        }
    }
}
