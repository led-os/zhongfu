package com.seven.lib_common.base.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.seven.lib_common.R;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.AnimationUtils;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/27
 */

public abstract class BaseHeaderActivity extends BaseActivity {

    private View leftBtn;
    private ImageView leftIv;
    private View rightBtn;
    private ImageView rightIv;
    private View rightTvBtn;
    private TypeFaceView rightTv;

    private TypeFaceView title;

    public boolean isLeftBtn;
    public boolean isRightBtn;
    public boolean isRightTvBtn;
    public boolean isTitleVisible;

    @Override
    protected int getContentViewId() {
        return getContentLayoutId();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initHeader();
        initViews(savedInstanceState);

        leftBtn.setVisibility(isLeftBtn ? View.VISIBLE : View.GONE);
        rightBtn.setVisibility(isRightBtn ? View.VISIBLE : View.GONE);
        rightTvBtn.setVisibility(isRightTvBtn ? View.VISIBLE : View.GONE);
        title.setVisibility(isTitleVisible ? View.VISIBLE : View.GONE);
    }

    private void initHeader() {

        try {

            leftBtn = getView(leftBtn, R.id.pre_v_left);
            leftBtn.setOnClickListener(this);
            leftIv = getView(leftIv, R.id.pre_v_left_iv);

            rightBtn = getView(rightBtn, R.id.pre_v_right);
            rightBtn.setOnClickListener(this);
            rightIv = getView(rightIv, R.id.pre_v_right_iv);

            rightTvBtn = getView(rightTvBtn, R.id.pre_tv_right);
            rightTvBtn.setOnClickListener(this);
            rightTv = getView(rightTv, R.id.pre_tv_right_tv);

            title = getView(title, R.id.pre_tv_title);
        } catch (NullPointerException e) {
            Logger.w("The base class initializes the space null pointer");
        }

    }

    private void setVisible(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
//Resource IDs cannot be used in a switchs statement in Android library modules more
//        switchs (v.getId()){
//            case R.id.pre_tv_right:
//                break;
//        }

        if (v.getId() == R.id.pre_v_left)
            onBackPressed();
        else if (v.getId() == R.id.pre_v_right)
            rightBtnOnClick(v);
        else if (v.getId() == R.id.pre_tv_right)
            rightTvOnClick(v);
    }

    public abstract int getContentLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void rightTvOnClick(View v);

    protected abstract void rightBtnOnClick(View v);

    protected abstract String getTitleText();

    protected void setLeftImg(int imgId) {
        if (leftIv != null)
            leftIv.setImageResource(imgId);
    }

    protected void setRightImg(int imgId) {
        if (rightIv != null)
            rightIv.setImageResource(imgId);
    }

    protected void setRightTv(String text) {
        if (rightTv != null)
            rightTv.setText(text);
    }

    protected void setRightTvColor(int color) {
        if (rightTv != null)
            rightTv.setTextColor(color);
    }

    protected void setRightTvBtnVisible(int visible) {
        if (rightTvBtn != null)
            rightTvBtn.setVisibility(visible);
    }

    protected TypeFaceView getTitleView() {
        return title;
    }

    protected void setTitleText(String title) {
        if (this.title != null)
            this.title.setText(title);
    }

    protected void setTitleVisible(int visible) {
        if (this.title != null)
            this.title.setVisibility(visible);
    }

    protected void alphaTitle(int position) {

        if (position == 0 && getTitleView().getVisibility() == View.VISIBLE) {
            AnimationUtils.alpha(getTitleView(), "alpha", 1f, 0f, 750, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setTitleVisible(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else if (position == 1 && getTitleView().getVisibility() == View.GONE) {
            setTitleVisible(View.VISIBLE);
            AnimationUtils.alpha(getTitleView(), "alpha", 0f, 1f, 750, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

}
