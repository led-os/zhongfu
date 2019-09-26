package com.seven.lib_router;


import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.event.MessageEvent;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/12
 */

public class Variable {

//    public static final String WECHAT_APP_ID = "wx51ffbf68801004c4";
//    public static final String WECHAT_SECRET = "971233b26d0ac6bf52ed81a230131090";
    public static final String WECHAT_APP_ID = "wx0d3b49888cf435d4";//青烟
    public static final String WECHAT_SECRET = "971233b26d0ac6bf52ed81a230131090";

    private static Variable instance;

    public Variable(){
        eventHttpCode=10001;
        initWxAPi();
    }

    public static Variable getInstance() {
        if (instance == null) {
            synchronized (Variable.class) {
                if (instance == null) {
                    instance = new Variable();
                }
            }
        }

        return instance;
    }

    private IWXAPI wxApi;

    private void initWxAPi(){
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        wxApi = WXAPIFactory.createWXAPI(SSDK.getInstance().getContext(), WECHAT_APP_ID, false);
        //将该app注册到微信
        wxApi.registerApp(WECHAT_APP_ID);
    }

    private int eventHttpCode;

    /**
     * 网络请求的拦截器消息
     */
    private void eventHttp(){
        EventBus.getDefault().post(new MessageEvent(eventHttpCode, ""));
    }

    public int getEventHttpCode() {
        return eventHttpCode;
    }

    public void setEventHttpCode(int eventHttpCode) {
        this.eventHttpCode = eventHttpCode;
    }

    private String token;
    private int userId;
    private boolean payPassword;
    private double tokenCount;
    private String aliAccount;
    private String wxAccount;
    private boolean read;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String getWechatAppId() {
        return WECHAT_APP_ID;
    }

    public static String getWechatSecret() {
        return WECHAT_SECRET;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void setInstance(Variable instance) {
        Variable.instance = instance;
    }

    public boolean isPayPassword() {
        return payPassword;
    }

    public void setPayPassword(boolean payPassword) {
        this.payPassword = payPassword;
    }

    public double getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(double tokenCount) {
        this.tokenCount = tokenCount;
    }

    public IWXAPI getWxApi() {
        return wxApi;
    }

    public void setWxApi(IWXAPI wxApi) {
        this.wxApi = wxApi;
    }
}
