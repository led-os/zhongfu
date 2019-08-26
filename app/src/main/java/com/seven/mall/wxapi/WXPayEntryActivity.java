package com.seven.mall.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
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
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

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

        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_OK:
                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.PAY_RESULT, 1));
                ToastUtils.showToast(MallApplication.getInstance(), ResourceUtils.getText(R.string.hint_pay_success));
                break;

            case BaseResp.ErrCode.ERR_COMM:
                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.PAY_RESULT, 0));
                ToastUtils.showToast(MallApplication.getInstance(), ResourceUtils.getText(R.string.hint_pay_failure));
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.PAY_RESULT, 0));
                ToastUtils.showToast(MallApplication.getInstance(), ResourceUtils.getText(R.string.hint_pay_cancel));
                break;
            default:
                Logger.i("Pay default");
                break;
        }

        finish();
    }
}