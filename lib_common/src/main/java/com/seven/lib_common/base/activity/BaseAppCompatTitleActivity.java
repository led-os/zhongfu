package com.seven.lib_common.base.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.seven.lib_common.R;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_opensource.application.SSDK;

import butterknife.ButterKnife;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/27
 */

public abstract class BaseAppCompatTitleActivity extends BaseAppCompatActivity {

    private RelativeLayout rootLayout;
    private RelativeLayout titleLayout;
    private TypeFaceView titleTv;
    private RelativeLayout leftBtn;
    private ImageView leftIv;
    private RelativeLayout rightBtn;
    private ImageView rightIv;
    private RelativeLayout rightTextBtn;
    private TypeFaceView rightTextTv;

    protected boolean isRightImageBtn;
    protected boolean isRightTextBtn;

    @Override
    protected int getContentViewId() {
        knife = true;
        return R.layout.lb_base_title;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        init();

        View contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = (int) SSDK.getInstance().getContext().getResources().getDimension(R.dimen.toolbar);

        rootLayout = getView(rootLayout, R.id.root_layout);
        rootLayout.addView(contentView, params);

        initData();

        unBinder = ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    private void init() {
        rootLayout = getView(rootLayout, R.id.root_layout);
        titleLayout = getView(titleLayout, R.id.title_layout);
        titleTv = getView(titleTv, R.id.title_tv);
        leftBtn = getView(leftBtn, R.id.left_btn);
        leftIv = getView(leftIv, R.id.left_iv);
        rightBtn = getView(rightBtn, R.id.right_image_btn);
        rightIv = getView(rightIv, R.id.right_image_iv);
        rightTextBtn = getView(rightTextBtn, R.id.right_text_btn);
        rightTextTv = getView(rightTextTv, R.id.right_text_tv);

        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        rightTextBtn.setOnClickListener(this);
    }

    private void initData() {
        setVisible(rightBtn, isRightImageBtn);
        setVisible(rightTextBtn, isRightTextBtn);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.left_btn)
            onBackPressed();
        else if (view.getId() == R.id.right_image_btn)
            rightBtnClick(view);
        else if (view.getId() == R.id.right_text_btn)
            rightTextBtnClick(view);
    }

    private void setVisible(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void rightTextBtnClick(View v);

    protected abstract void rightBtnClick(View v);

    protected void setTitleBg(int id) {
        titleLayout.setBackgroundResource(id);
    }

    protected void setTitleBgColor(int id){
        titleLayout.setBackgroundColor(ContextCompat.getColor(SSDK.getInstance().getContext(), id));
    }

    protected void setTitleText(int id) {
        titleTv.setText(getResources().getString(id));
    }

    protected void setLeftImg(int id) {
        leftIv.setImageResource(id);
    }

    protected void setRightImg(int id) {
        rightIv.setImageResource(id);
    }

    protected void setRightTextBg(int id) {
        rightTextBtn.setBackgroundResource(id);
    }

    protected void setRightTextTv(int id) {
        rightTextTv.setText(getResources().getString(id));
    }

    protected void setRightTextColor(int id) {
        rightTextTv.setTextColor(ContextCompat.getColor(SSDK.getInstance().getContext(), id));
    }
}
