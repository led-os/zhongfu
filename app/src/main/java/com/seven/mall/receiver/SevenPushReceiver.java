package com.seven.mall.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.seven.lib_opensource.event.MessageEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/12/26
 */

public class SevenPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Logger.d("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        EventBus.getDefault().post(new MessageEvent(Constants.EventConfig.MESSAGE_READ,""));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Logger.d("[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Logger.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d("[MyReceiver] 用户点击打开了通知");
            Logger.i(intent.getStringExtra(JPushInterface.EXTRA_EXTRA));

            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MESSAGE);

//            String extra = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);
//            String uri = "";
//
//            try {
//                JSONObject jsonObject = new JSONObject(extra);
//                uri = jsonObject.getString("link");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            if (uri.startsWith(KRouterUtils.ROUTE_VIDEO)) {
//
//                KRouterUtils.routerVideo(uri);
//
//            } else if (uri.startsWith(KRouterUtils.ROUTE_CHANNEL) || uri.startsWith(KRouterUtils.ROUTE_ARTIST)) {
//
//                KRouterUtils.routerArtist(uri);
//
//            } else if (uri.startsWith(KRouterUtils.ROUTE_TOPIC)) {
//
//                KRouterUtils.routerArtist(uri);
//
//            } else if (uri.startsWith(KRouterUtils.ROUTE_USER)) {
//
//                KRouterUtils.routerArtist(uri);
//
//            }else if(uri.startsWith(KRouterUtils.ROUTE_MATCH)){
//
//                KRouterUtils.routerMatch(uri);
//
//            } else if (uri.startsWith(KRouterUtils.ROUTE_WEB)) {
//
//                KRouterUtils.routerWeb(uri);
//
//            } else if (uri.startsWith(KRouterUtils.ROUTE_MESSAGE) || uri.startsWith(KRouterUtils.ROUTE_FEED)) {
//
//                KRouterUtils.routerNormal(uri);
//
//            }

//          //打开自定义的Activity
//          Intent i = new Intent(context, TestActivity.class);
//          i.putExtras(bundle);
//          //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//          context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Logger.d("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Logger.w("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Logger.d("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        return "";
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
    }
}
