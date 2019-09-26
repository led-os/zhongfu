package com.seven.lib_model;

import android.content.Context;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/8
 */

public class HttpSDK {

    private Context context;

    private static HttpSDK instance = null;

    private HttpConfig config;

    private HttpSDK() {
        config = new HttpConfig();
    }

    public static HttpSDK getInstance() {
        if (instance == null) {
            synchronized (HttpSDK.class) {
                if (instance == null) {
                    instance = new HttpSDK();
                }
            }
        }

        return instance;
    }

    public void initSDK(Context context, HttpConfig config) {
        this.context = context;
        this.config = config;
    }

    public HttpConfig getConfig() {
        return config;
    }

    public void setConfig(HttpConfig config) {
        this.config = config;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
