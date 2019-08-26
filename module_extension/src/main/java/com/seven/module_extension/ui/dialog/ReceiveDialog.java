package com.seven.module_extension.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seven.lib_common.base.dialog.BaseDialog;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.module_extension.R;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class ReceiveDialog extends BaseDialog {

    TextView me_not_vip_cancel;
    TextView me_not_vip_ok;

    public ReceiveDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.me_dialog_success;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public void initView() {
        me_not_vip_cancel = getView(me_not_vip_cancel,R.id.me_not_vip_cancel);
        me_not_vip_ok = getView(me_not_vip_ok,R.id.me_not_vip_ok);
        me_not_vip_cancel.setOnClickListener(this);
        me_not_vip_ok.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.me_not_vip_cancel){
            listener.onClick(me_not_vip_cancel,"cancel");
        }
        if (view.getId() == R.id.me_not_vip_ok){
            listener.onClick(me_not_vip_ok,"go");
        }
        dismiss();
    }
}
