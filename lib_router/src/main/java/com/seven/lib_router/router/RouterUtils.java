package com.seven.lib_router.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seven.lib_router.Constants;
import com.seven.lib_router.R;
import com.seven.lib_router.RouterSDK;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/3/7
 */

public class RouterUtils {

    private static RouterUtils instance;

    public static RouterUtils getInstance() {
        if (instance == null) {
            synchronized (RouterSDK.class) {
                if (instance == null)
                    instance = new RouterUtils();
            }
        }
        return instance;
    }

    public void routerNormal(@Nullable String uri) {
        ARouter.getInstance().build(uri)
                .navigation();
    }

    public void routerWithString(@Nullable String uri, @Nullable String key,
                                 @Nullable String value) {
        ARouter.getInstance().build(uri)
                .withString(key, value)
                .navigation();
    }

    public void routerWithString(@Nullable String uri, @Nullable String key, @Nullable String value,
                                 @Nullable Activity activity, @Nullable boolean isFinish) {
        ARouter.getInstance()
                .build(uri)
                .withString(key, value)
                .withTransition(R.anim.fade_in, R.anim.fade_out)
                .navigation(activity);

        if (isFinish)
            activity.onBackPressed();
    }

    public void routerWithSerializable(@Nullable String uri, @Nullable String key,
                                       @Nullable Serializable value) {
        ARouter.getInstance().build(uri)
                .withSerializable(key, value)
                .navigation();
    }

    public void routerWithFade(@Nullable String uri, @Nullable Context context) {
        ARouter.getInstance()
                .build(uri)
                .withTransition(R.anim.fade_in, R.anim.fade_out)
                .navigation(context);
    }

    public void routerWithFade(@Nullable String uri, @Nullable Activity activity, @Nullable boolean isFinish) {
        ARouter.getInstance()
                .build(uri)
                .withTransition(R.anim.fade_in, R.anim.fade_out)
                .navigation(activity);

        if (isFinish)
            activity.onBackPressed();
    }

    public void routerWithFade(@Nullable String uri, @Nullable Activity activity, int startAnim, int endAnim) {
        ARouter.getInstance()
                .build(uri)
                .withTransition(startAnim, endAnim)
                .navigation(activity);
    }
}
