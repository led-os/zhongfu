package com.seven.lib_common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import com.seven.lib_common.listener.IKeyBoardVisibleListener;
import com.seven.lib_common.listener.ViewLocationListener;
import com.seven.lib_common.listener.ViewMeasureListener;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/27
 */

public class ScreenUtils {

    private static boolean isVisibleForLast = false;
    private static View decorView;

    /**
     * px-->dip/dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dip/dp-->px
     *
     * @param context
     * @param dipValue dp
     * @return px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px-->sp
     *
     * @param context
     * @param pxValue px
     * @return sp
     */
    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp-->px
     *
     * @param context
     * @param spValue sp
     * @return px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int heightDp = 0;
        int heightPx = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            heightDp = Integer.parseInt(field.get(obj).toString());
            heightPx = context.getResources().getDimensionPixelSize(heightDp);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return heightPx;
    }

    public static void addOnSoftKeyBoardVisibleListener(Activity activity, final IKeyBoardVisibleListener listener) {
        globalLayoutListener = new GlobalLayoutListener(activity, listener);
        decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    private static GlobalLayoutListener globalLayoutListener;

    public static void removeOnSoftKeyBoardVisibleListener() {

        if (decorView == null || globalLayoutListener == null) return;
        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        globalLayoutListener = null;
        decorView = null;
    }

    private static class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        Activity activity;
        IKeyBoardVisibleListener listener;

        public GlobalLayoutListener(Activity activity, IKeyBoardVisibleListener listener) {
            this.activity = activity;
            this.listener = listener;
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //Calculate the height of the visible screen
            int displayHeight = rect.bottom - rect.top;
            //Get the overall screen height
            int height = decorView.getHeight() - getStatusBarHeight(SSDK.getInstance().getContext());
            //Keyboard height
            int keyboardHeight = height - displayHeight;
            int navigationHeight = getNavigationBarHeight(activity);
            if (navigationHeight > 0)
                keyboardHeight -= navigationHeight;
            boolean visible = (double) displayHeight / height < 0.8;
            if (visible != isVisibleForLast) {
                listener.onSoftKeyBoardVisible(visible, keyboardHeight);
            }
            isVisibleForLast = visible;
        }
    }

    public static int getViewLocationH(final View view) {

        int[] location = {0, 0};
        view.getLocationOnScreen(location);
        return location[1] + view.getHeight();
    }

    public static int getViewLocation(final View view) {

        int[] location = {0, 0};
        view.getLocationOnScreen(location);
        return location[1];
    }

    public static void getViewLocationH(final int code, final View view, final ViewLocationListener listener) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                int height = view.getHeight();
                int width = view.getWidth();

                int[] location = {0, 0};
                view.getLocationOnScreen(location);

                listener.location(code, height, width, location);
            }
        });
    }

    public static int getViewHeight(View view) {
        return view.getHeight();
    }

    public static void getViewWidth(final int code, final View view, final ViewMeasureListener listener) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.measure(code, view.getHeight(), view.getWidth());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public static int getScaling16_9(int size) {
        return size / 16 * 9;
    }

    public static String getImageSize(int width, int height) {
//        return "?imageView2/1/w/" + width + "/h/" + height + "/format/WebP";
        return "?imageView2/1/w/" + width + "/h/" + height;
    }

    public static int[] getDynamicSize(int width, int height, int screenWidth) {

        int[] size = new int[2];

        if (width <= height) {
            size[0] = screenWidth;
            size[1] = screenWidth;
            return size;
        }

        size[0] = screenWidth;
        size[1] = (int) ((double) screenWidth / (double) width * (double) height);
        return size;
    }

    public static void fitsSystemWindows(boolean isTranslucentStatus, View view) {
        if (isTranslucentStatus) {
            view.getLayoutParams().height = calcStatusBarHeight(view.getContext());
        }
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return 宽度
     */
    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;

        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return 高度
     */
    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int height = dm.heightPixels;
        return height;
    }

    public static void hideBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            option = option | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
        }
    }

}
