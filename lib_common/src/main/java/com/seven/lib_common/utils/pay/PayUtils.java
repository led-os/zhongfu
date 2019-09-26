package com.seven.lib_common.utils.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.R;
import com.seven.lib_common.task.ActivityStack;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/12/19
 */

public class PayUtils {

    private static final String TAG = " PayUtils---> ";

    private static final int SDK_PAY_FLAG = 1;

    private static PayUtils payUtils;

    public PayUtils() {
    }

    public static PayUtils getInstance() {

        synchronized (PayUtils.class) {

            if (payUtils == null)
                payUtils = new PayUtils();
        }
        return payUtils;
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.PAY_RESULT, 1));
                        ToastUtils.showToast(SSDK.getInstance().getContext(), ResourceUtils.getText(R.string.hint_pay_success));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.PAY_RESULT, 0));
                        ToastUtils.showToast(SSDK.getInstance().getContext(), payResult.getMemo());
                    }
                    break;
                }
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
//                    } else {
//                        // 其他状态值则为授权失败
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
//                    }
//                    break;
//                }
                default:
                    break;
            }
        }

        ;
    };

    public void aliPay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                Activity activity = ActivityStack.getInstance().peek();
                if (activity == null) return;

                PayTask payTask = new PayTask(activity);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Logger.i(TAG + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void wxPay(@NonNull String appid, @NonNull String partnerid,
                      @NonNull  String packageX, @NonNull String noncestr,
                      @NonNull int timestamp, @NonNull String prepayid,@NonNull String sign) {


        PayReq req = new PayReq();
//                req.appId = KoloApplication.WECHAT_APP_ID;  // 测试用appId
        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.nonceStr = noncestr;
        req.timeStamp = String.valueOf(timestamp);
        req.packageValue = packageX;
        req.sign = sign;
        req.extData = "app data"; // optional
        Logger.i(TAG + "正常调起支付");
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        Variable.getInstance().getWxApi().sendReq(req);
    }
}
