package com.seven.lib_common.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.seven.lib_common.R;
import com.seven.lib_common.base.dialog.BaseDialog;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ResourceUtils;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */

public class MallDialog extends BaseDialog {

    public static final int PAY_SUCCEED = 1;
    public static final int PAY_FAILURE = 2;
    public static final int TRANSACTION = 3;

    private int type;
    private String[] strings;

    private ImageView logoIv;
    private TypeFaceView titleTv;
    private TypeFaceView contentTv;
    private ImageView lineIv;
    private LinearLayout buttonLayout;

    private RelativeLayout cancelBtn;
    private RelativeLayout sureBtn;

    public MallDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    public MallDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener,
                      int type, String... strings) {
        this(activity, theme, listener);
        this.type = type;
        this.strings = strings;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.lb_dialog_mall;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        logoIv = getView(logoIv, R.id.logo_iv);
        titleTv = getView(titleTv, R.id.title_tv);
        contentTv = getView(contentTv, R.id.content_tv);
        lineIv = getView(lineIv, R.id.line_iv);
        buttonLayout = getView(buttonLayout, R.id.button_layout);

        cancelBtn = getView(cancelBtn, R.id.cancel_btn);
        sureBtn = getView(sureBtn, R.id.sure_btn);
    }

    @Override
    public void initData() {

        cancelBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);

        function(type);
    }

    private void function(int type) {

        switch (type) {

            case PAY_SUCCEED:
                contentTv.setVisibility(View.GONE);

                logoIv.setImageResource(R.drawable.dialog_succeed);
                titleTv.setText(ResourceUtils.getText(R.string.pay_succeed));
                break;

            case PAY_FAILURE:

                contentTv.setVisibility(View.GONE);

                logoIv.setImageResource(R.drawable.dialog_failure);
                titleTv.setText(ResourceUtils.getText(R.string.pay_failure));

                break;

            case TRANSACTION:

                setCanceledOnTouchOutside(true);

                titleTv.setVisibility(View.GONE);
                lineIv.setVisibility(View.GONE);
                buttonLayout.setVisibility(View.GONE);

                logoIv.setImageResource(R.drawable.dialog_succeed);
                contentTv.setText(strings[0]);
                break;

        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cancel_btn)
            listener.onCancel(v);
        if (v.getId() == R.id.sure_btn){
            listener.onClick(v);
        }

        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        listener.dismiss();
    }
}
