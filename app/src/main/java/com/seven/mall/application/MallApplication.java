package com.seven.mall.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.seven.lib_common.stextview.STextViewSDK;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.HttpConfig;
import com.seven.lib_model.HttpSDK;
import com.seven.lib_opensource.application.SConfig;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;
import com.seven.lib_router.Constants;
import com.seven.lib_router.RouterSDK;

import cn.jpush.android.api.JPushInterface;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/26
 */

public class MallApplication extends SevenApplication {

    public static final int MIN_CODE = 0;
    public static final int MAX_CODE = 0;
    public static final int EVENT_CODE = 10001;

//    public static final String BASE_URL = "http://zhongfu.lerqin.com/";
//    public static final String STORE_URL = "http://zhongfu.lerqin.com/";
    public static final String BASE_URL = "http://api.zf.fqwlkj.com.cn/";
    public static final String STORE_URL = "http://api.zf.fqwlkj.com.cn/";
    public static final String APP_KEY = "";
    public static final String STORE_KEY = "";

    private boolean release = true;

    public static MallApplication getInstance() {
        return (MallApplication) application;
    }

    @Override
    public void initApp() {
        super.initApp();

        SConfig config = new SConfig();
        config.setEventCode(EVENT_CODE);
        config.setMinCode(MIN_CODE);
        config.setMaxCode(MAX_CODE);
        SSDK.getInstance().initSDK(getInstance(), config);
        SSDK.getInstance().setLoggerDebug(!release);
        STextViewSDK.getInstance().initSDK(getInstance());

        RouterSDK.getInstance().initSDK(getInstance());
        ApiManager.init(this);
        changeHttpConfig();

        isLoggerDebug = !release;

        if (release) {

        } else {

        }

        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

    }

    private static HttpConfig initHttpConfig() {

        HttpConfig.Builder builder = new HttpConfig.Builder();
        HttpConfig config = builder
                .appUrl(BASE_URL)
                .storeUrl(STORE_URL)
                .appKey(APP_KEY)
                .storeKey(STORE_KEY)
                .build();

        return config;
    }

    public static void changeHttpConfig() {
        HttpSDK.getInstance().setConfig(initHttpConfig());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setAlias(String userId) {
        JPushInterface.setAlias(this, Constants.JpushConfig.ALIAS_CODE, userId);
    }

    public void clearAlias() {
        JPushInterface.clearAllNotifications(this);
        JPushInterface.deleteAlias(this, Constants.JpushConfig.ALIAS_CODE);
    }
}
