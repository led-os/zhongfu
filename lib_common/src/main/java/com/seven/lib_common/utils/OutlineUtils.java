package com.seven.lib_common.utils;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.seven.lib_opensource.application.SSDK;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/12
 */

public class OutlineUtils {

    private static volatile OutlineUtils outlineUtils;

    public OutlineUtils() {

    }

    public static OutlineUtils getInstance() {

        if (outlineUtils == null) {

            synchronized (OutlineUtils.class) {
                outlineUtils = new OutlineUtils();
            }

        }

        return outlineUtils;
    }

    public void outlineView(View view, int radius) {

        final int newRadius = radius == 0 ? ScreenUtils.dip2px(SSDK.getInstance().getContext(), 4) : radius;

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), newRadius);
            }
        };
        view.setOutlineProvider(viewOutlineProvider);
        view.setClipToOutline(true);
    }

}
