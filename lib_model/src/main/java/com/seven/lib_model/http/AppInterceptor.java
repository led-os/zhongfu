package com.seven.lib_model.http;

import android.text.TextUtils;

import com.seven.lib_common.utils.AppUtils;
import com.seven.lib_model.HttpSDK;
import com.seven.lib_opensource.application.SevenApplication;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/3
 */

public class AppInterceptor implements Interceptor {

    private String ts;

    public AppInterceptor() {
        this.ts = String.valueOf(System.currentTimeMillis() / 1000);
    }

//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        Request request = chain.request()
//                .newBuilder()
//                .addHeader("Content-Type", "application/json;charset=UTF-8")
//                .addHeader("X-UUID", TextUtils.isEmpty(HttpSDK.getInstance().getConfig().getUuid()) ? "" : HttpSDK.getInstance().getConfig().getUuid())
//                .addHeader("X-TS", ts)
//                .addHeader("X-VERSION", AppUtils.getVersionName(HttpSDK.getInstance().getContext()))
//                .addHeader("X-ACCESS-TOKEN", TextUtils.isEmpty(HttpSDK.getInstance().getConfig().getToken()) ? "" : HttpSDK.getInstance().getConfig().getToken())
//                .addHeader("X-LANGUAGE", TextUtils.isEmpty(HttpSDK.getInstance().getConfig().getLanguage()) ? "zh-cn" : HttpSDK.getInstance().getConfig().getLanguage())
//                .addHeader("X-SIGN", getSign(chain.request()))
//                .addHeader("X-PLATFORM", "Android")
//                .addHeader("User-Agent", AppUtils.getSystemVersion() + "," + AppUtils.getDeviceBrand() + "," + AppUtils.getSystemModel())
//                .build();
//        return chain.proceed(request);
//    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", SevenApplication.getInstance().getToken() == null ? "" : SevenApplication.getInstance().getToken())
                .build();

        return chain.proceed(request);
    }

    private String getSign(Request request) {

        /**
         * http://api.dev.ikolo.me/app_home/videos?pageIndex=1&pageSize=10
         */

        String requestUrl = request.url().toString();
        String appHost = RetrofitHelper.getInstance().getBaseUrl();
        String storeHost = RetrofitHelper.getInstance().getStoreUrl();

        String basePath = request.url().toString().replace(requestUrl.startsWith(appHost) ?
                appHost : storeHost, "/");

        String key = requestUrl.startsWith(appHost) ?
                HttpSDK.getInstance().getConfig().getAppKey() : HttpSDK.getInstance().getConfig().getStoreKey();

        int index = basePath.indexOf("?");

        String path = index == -1 ? basePath : basePath.substring(0, basePath.indexOf("?"));
        String query = index == -1 ? "" : basePath.substring(basePath.indexOf("?") + 1, basePath.length());

        return md5(key + path + query + ts);
    }

    public String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
