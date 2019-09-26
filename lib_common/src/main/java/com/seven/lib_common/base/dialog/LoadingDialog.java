package com.seven.lib_common.base.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.seven.lib_common.R;
import com.seven.lib_opensource.application.SSDK;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/12/12
 */

public class LoadingDialog extends BaseDialog {

    private ImageView loadingView;
    private Animation operatingAnim;
    private AccelerateInterpolator interpolator;

    public LoadingDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.lb_dialog_loading;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();
        initData();

    }

    @Override
    public void initView() {

//        loadingView = getView(loadingView, R.id.loading_view);

    }

    @Override
    public void show() {
        super.show();
//        start();
    }

    @Override
    public void cancel() {
        super.cancel();
//        clearAnim();
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        clearAnim();
    }

    public void start() {
        if (operatingAnim == null) {
            interpolator = new AccelerateInterpolator(1.2f);
            operatingAnim = AnimationUtils.loadAnimation(SSDK.getInstance().getContext(), R.anim.loading_anim);
            operatingAnim.setInterpolator(interpolator);
            loadingView.startAnimation(operatingAnim);
        }
//        AnimationUtils.rotation(loadingView, Constants.AnimName.ROTATION,
//                0, -1080f, 2000, new AccelerateInterpolator(1.5f), null);

    }

    public void clearAnim() {
        if (operatingAnim == null)
            return;
        operatingAnim.reset();
        operatingAnim = null;
        loadingView.clearAnimation();
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {

    }

}
