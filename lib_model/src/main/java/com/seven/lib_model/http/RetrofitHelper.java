package com.seven.lib_model.http;


import android.content.Context;

import com.seven.lib_http.retrofit.RetrofitApi;
import com.seven.lib_model.HttpSDK;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class RetrofitHelper extends RetrofitApi<RetrofitService> {

    private static RetrofitHelper retrofitHelper;

    private RetrofitHelper() {
        super();
    }

    public static RetrofitHelper getInstance() {

        if (retrofitHelper == null)
            retrofitHelper = new RetrofitHelper();

        return retrofitHelper;
    }

    @Override
    protected Context getContext() {
        return null;
    }

    @Override
    protected String getBaseUrl() {
        return HttpSDK.getInstance().getConfig().getAppUrl();
    }

    @Override
    protected String getStoreUrl() {
        return HttpSDK.getInstance().getConfig().getStoreUrl();
    }

    @Override
    protected String getDefaultKey() {
        return HttpSDK.getInstance().getConfig().getAppUrl();
    }
}
