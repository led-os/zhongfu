package com.seven.lib_opensource.application;

import android.content.Context;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/8
 */

public class SSDK {

    private Context context;

    private static SSDK instance = null;

    private SConfig sConfig;
    private boolean isLoggerDebug=true;

    private SSDK() {
        sConfig = new SConfig();
    }

    public static SSDK getInstance() {
        if (instance == null) {
            synchronized (SSDK.class) {
                if (instance == null) {
                    instance = new SSDK();
                }
            }
        }

        return instance;
    }

    public void initSDK(Context context, SConfig config) {
        this.context = context;
        this.sConfig = config;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SConfig getsConfig() {
        return sConfig;
    }

    public void setsConfig(SConfig sConfig) {
        this.sConfig = sConfig;
    }

    public boolean isLoggerDebug() {
        return isLoggerDebug;
    }

    public void setLoggerDebug(boolean loggerDebug) {
        isLoggerDebug = loggerDebug;
    }
}
