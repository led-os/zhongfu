package com.seven.lib_common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/25
 */
public class NetWorkUtils {
    public enum NET_TYPE{
        NET_NO , NET_WIFI , NET_CMWAP , NET_CMNET
    }

    public static boolean isNetWork(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                SSDK.getInstance().getContext().getSystemService
                        (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null){
            return true;
        }
        return false;
    }
    public static NET_TYPE isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) SSDK.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return NET_TYPE.NET_NO;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return NET_TYPE.NET_WIFI;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo) || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return NET_TYPE.NET_CMWAP;
                            }
                            return NET_TYPE.NET_CMNET;
                        }
                    }
                }
            }
        }
        return NET_TYPE.NET_NO;
    }
}
