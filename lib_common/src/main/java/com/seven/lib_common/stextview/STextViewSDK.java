package com.seven.lib_common.stextview;

import android.content.Context;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/6
 */

public class STextViewSDK {

    public Context context;

    private static STextViewSDK instance = null;

    private STextViewSDK() {
    }

    public static STextViewSDK getInstance() {
        if (instance == null) {
            synchronized (STextViewSDK.class) {
                if (instance == null) {
                    instance = new STextViewSDK();
                }
            }
        }

        return instance;
    }

    public void initSDK(Context context) {
        this.context = context;
    }
}
