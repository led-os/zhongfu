package com.seven.lib_common.base.sheet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.seven.lib_common.R;
import com.seven.lib_common.config.RunTimeConfig;


/**
 * 性别选择器
 *
 * @author seven
 */
public class SexSheet extends IBaseSheet {

    //男 女 取消
    private RelativeLayout mMan;
    private RelativeLayout mWoman;
    private RelativeLayout mCancel;

    public SexSheet(Activity activity, int theme,com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    @Override
    public int getRootLayoutId() {

        isTouch=true;

        return R.layout.lb_sheet_sex;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mMan = getView(mMan, R.id.sex_man_rl);
        mMan.setOnClickListener(this);
        mWoman = getView(mWoman, R.id.sex_woman_rl);
        mWoman.setOnClickListener(this);
        mCancel = getView(mCancel, R.id.sex_cancel_rl);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.sex_man_rl)
            listener.onClick(v, RunTimeConfig.CommonConfig.SEX_MAN);
        else if (id == R.id.sex_woman_rl)
            listener.onClick(v, RunTimeConfig.CommonConfig.SEX_WOMAN);

        dismiss();

    }
}
