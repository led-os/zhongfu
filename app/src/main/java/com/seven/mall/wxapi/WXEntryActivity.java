package com.seven.mall.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.dialog.LoadingDialog;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.mall.R;
import com.seven.mall.application.MallApplication;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private LoadingDialog loadingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtils.hideBar(this);

        Variable.getInstance().getWxApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Variable.getInstance().getWxApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Logger.i(resp.errStr);
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType())
                    Logger.i("Share Failure");
                else {
//                    failure();
                    Logger.i("Login Failure");
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
//                        String code = ((SendAuth.Resp) resp).code;
//                        Logger.i("code = " + code);
//                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
//                        showLoadingDialog();
//
//                        if (((SendAuth.Resp) resp).state.equals(ThirdPartyUtils.WX_LOGIN))
//                            request(KoloApplication.WECHAT_APP_ID, KoloApplication.WECHAT_SECRET, code, "authorization_code");//authorization_code要求传入的固定值
//                        else {
//                            ThirdPartyUtils.getInstance().wxGrantSucceed(((SendAuth.Resp) resp).state, code);
//                            finish();
//                        }
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        Logger.i("Share Succeed");
                        finish();
                        break;
                }
                break;
            default:
                Logger.i("Share default");
                break;
        }

        finish();
    }

    private void showLoadingDialog() {
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            loadingDialog = new LoadingDialog(this, R.style.Dialog, null);
            loadingDialog.show();
        }
    }
}