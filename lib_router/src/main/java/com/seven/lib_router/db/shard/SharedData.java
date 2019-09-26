package com.seven.lib_router.db.shard;

import android.content.Context;
import android.content.SharedPreferences;

import com.seven.lib_router.Constants;
import com.seven.lib_router.RouterSDK;


/**
 * @author seven
 */
public class SharedData {

    private SharedPreferences mSharedPreferences;

    private SharedPreferences.Editor mEditor;

    public static volatile SharedData sharedData;


    private SharedData() {

        mSharedPreferences = RouterSDK.getInstance().getContext().getSharedPreferences(
                Constants.SharedConfig.SHARED_NAME, Context.MODE_PRIVATE);

        mEditor = mSharedPreferences.edit();
    }

    public static SharedData getInstance() {

        if (sharedData == null) {

            synchronized (SharedData.class) {
                sharedData = new SharedData();
            }
        }
        return sharedData;
    }

    /**
     * 是否第一次登录
     *
     * @return
     */
    public boolean getIsFirst() {
        return mSharedPreferences.getBoolean(Constants.SharedConfig.FIRST, true);
    }

    public void setIsFirst(boolean isFirst) {
        mEditor.putBoolean(Constants.SharedConfig.FIRST, isFirst).commit();
    }

    public String getToken() {
        return mSharedPreferences.getString(Constants.SharedConfig.TOKEN, "");
    }

    public void setToken(String token) {
        mEditor.putString(Constants.SharedConfig.TOKEN, token).commit();
    }

    public void setUserInfo(String json) {
        mEditor.putString(Constants.SharedConfig.USER_INFO, json).apply();
    }

    public String getUserInfo() {
        return mSharedPreferences.getString(Constants.SharedConfig.USER_INFO, "");
    }
}
