package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.ui.dialog.MallDialog;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.pay.PayUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.model.extension.BuyRoleEntity;
import com.seven.lib_model.model.home.AliPayEntity;
import com.seven.lib_model.model.home.OrderEntity;
import com.seven.lib_model.model.home.WxPayEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterPath.ACTIVITY_BUY_ROLE)
public class BuyRoleActivity extends BaseTitleActivity {

    @Autowired(name = "level")
    String level = "";
    @BindView(R2.id.me_cb_kz)
    CheckBox meCbKz;
    @BindView(R2.id.me_cb_cz)
    CheckBox meCbCz;
    @BindView(R2.id.me_cb_chengz)
    CheckBox meCbChengz;
    @BindView(R2.id.me_cb_apliy)
    CheckBox meCbApliy;
    @BindView(R2.id.me_cb_wechat)
    CheckBox meCbWechat;
    @BindView(R2.id.me_buy_btn)
    Button meBuyBtn;
    @BindView(R2.id.me_kz_price)
    TextView meKzPrice;
    @BindView(R2.id.me_cz_price)
    TextView meCzPrice;
    @BindView(R2.id.me_chengz_price)
    TextView meChengzPrice;

    private String role = "";
    private String pay = "";
    ExActivityPresenter presenter;
    UserEntity entity;
    OrderEntity orderEntity;
    private MallDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_bugrole;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        statusBar = StatusBar.LIGHT;
        setTitleText(R.string.me_buyrole_title);
        EventBus.getDefault().register(this);
        presenter = new ExActivityPresenter(this, this);
        String userInfo = SharedData.getInstance().getUserInfo();
        entity = new Gson().fromJson(userInfo, UserEntity.class);
        ApiManager.getRolePrice()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResult<BuyRoleEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<BuyRoleEntity> buyRoleEntityBaseResult) {
                        meKzPrice.setText("￥" + buyRoleEntityBaseResult.getData().getRole_2());
                        meCzPrice.setText("￥" + buyRoleEntityBaseResult.getData().getRole_3());
                        meChengzPrice.setText("￥" + buyRoleEntityBaseResult.getData().getRole_4());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        if (intent == null) intent = getIntent();
        level = intent.getStringExtra("level");
        switch (level) {
            case "2":
                meCbKz.setEnabled(false);
                break;
            case "3":
                meCbKz.setEnabled(false);
                meCbCz.setEnabled(false);
                break;
            case "4":
                break;
            default:
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(String msg) {

    }


    @OnClick({R2.id.me_cb_kz, R2.id.me_cb_cz, R2.id.me_cb_chengz, R2.id.me_cb_apliy, R2.id.me_cb_wechat, R2.id.me_buy_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.me_cb_kz) {
            meCbKz.setChecked(true);
            meCbCz.setChecked(false);
            meCbChengz.setChecked(false);
            role = "2";
        } else if (i == R.id.me_cb_cz) {
            meCbKz.setChecked(false);
            meCbCz.setChecked(true);
            meCbChengz.setChecked(false);
            role = "3";
        } else if (i == R.id.me_cb_chengz) {
            meCbKz.setChecked(false);
            meCbCz.setChecked(false);
            meCbChengz.setChecked(true);
            role = "4";
        } else if (i == R.id.me_cb_apliy) {
            meCbApliy.setChecked(true);
            meCbWechat.setChecked(false);
            pay = "0";
        } else if (i == R.id.me_cb_wechat) {
            meCbApliy.setChecked(false);
            meCbWechat.setChecked(true);
            pay = "1";
        } else if (i == R.id.me_buy_btn) {
//            if (new Gson().fromJson(SharedData.getInstance().getUserInfo(), UserEntity.class).getRole() == 0) {
//                ToastUtils.showToast(mContext, "需要购买报单成为vip才能购买");
//                return;
//            }
            if (role.isEmpty()) {
                ToastUtils.showToast(mContext, "请选择购买角色");
                return;
            }
            if (pay.isEmpty()) {
                ToastUtils.showToast(mContext, "请选择支付方式");
                return;
            }
            if (!isLogin()) return;
            showLoadingDialog();
            presenter.buyRole(1, Integer.parseInt(role), pay == "0" ? "alipay" : "wxpay");
        }
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            dismissLoadingDialog();
            if (pay.equals("0")) {
                AliPayEntity aliPayEntity = (AliPayEntity) object;
                PayUtils.getInstance().aliPay(aliPayEntity.getPay_info());
            }
            if (pay.equals("1")) {
                WxPayEntity wxPayEntity = (WxPayEntity) object;
                PayUtils.getInstance().wxPay(wxPayEntity.getAppid(), wxPayEntity.getPartnerid(), wxPayEntity.getPackageX(),
                        wxPayEntity.getNoncestr(), wxPayEntity.getTimestamp(), wxPayEntity.getPrepayid(), wxPayEntity.getSign());
            }
        }
    }

    private void resultDialog(int type){
        if (dialog == null){
            dialog = new MallDialog(this, R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                }

                @Override
                public void onClick(View v, Object... objects) {
                    RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MINE_ORDER);
                }

                @Override
                public void dismiss() {

                }
            },type);
            dialog.setCancelable(false);
        }

        if (!dialog.isShowing())
            dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getWhat()) {
            case Constants.EventConfig.PAY_RESULT:

                dismissLoadingDialog();

                int status = (int) ((ObjectsEvent) event).getObjects()[0];

                resultDialog(status == 1 ? MallDialog.PAY_SUCCEED : MallDialog.PAY_FAILURE);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
