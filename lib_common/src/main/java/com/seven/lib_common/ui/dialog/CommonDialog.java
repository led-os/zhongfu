package com.seven.lib_common.ui.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.seven.lib_common.R;
import com.seven.lib_common.base.dialog.BaseDialog;
import com.seven.lib_common.stextview.TypeFaceView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/12/12
 */

public class CommonDialog extends BaseDialog {

    private TypeFaceView hintTv;
    private TypeFaceView contentTv;

    private RelativeLayout sureRl;
    private RelativeLayout cancelRl;
    private String[] strings;

    public CommonDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    public CommonDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener, String... strings) {
        this(activity, theme, listener);
        this.strings = strings;
    }

    @Override
    public int getRootLayoutId() {
        isTouch = true;
        return R.layout.lb_dialog_common;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();
        initData();

    }

    @Override
    public void initView() {

        hintTv = getView(hintTv, R.id.hint_tv);
        contentTv = getView(contentTv, R.id.content_tv);
        sureRl = getView(sureRl, R.id.sure_rl);
        cancelRl = getView(cancelRl, R.id.cancel_rl);
        sureRl.setOnClickListener(this);
        cancelRl.setOnClickListener(this);
    }

    @Override
    public void initData() {

        if (strings.length > 0)
            contentTv.setText(strings[0]);

        if (strings.length > 1)
            hintTv.setText(strings[1]);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sure_rl)
            listener.onClick(v);

        dismiss();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void show() {

        super.show();
    }

}
