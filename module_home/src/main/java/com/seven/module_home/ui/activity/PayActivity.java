package com.seven.module_home.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.task.ActivityStack;
import com.seven.lib_common.ui.dialog.MallDialog;
import com.seven.lib_common.utils.CheckSystemUtils;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.pay.PayUtils;
import com.seven.lib_model.model.home.AliPayEntity;
import com.seven.lib_model.model.home.OrderEntity;
import com.seven.lib_model.model.home.WxPayEntity;
import com.seven.lib_model.presenter.home.ActHomePresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_home.R;
import com.seven.module_home.R2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */
@Route(path = RouterPath.ACTIVITY_PAY)
public class PayActivity extends BaseTitleActivity {

    @Autowired(name = Constants.BundleConfig.NORMAL)
    public boolean normal;

    public OrderEntity orderEntity;

    @BindView(R2.id.alipay_rl)
    public RelativeLayout alipayRl;
    @BindView(R2.id.wechat_pay_rl)
    public RelativeLayout wxPayRl;
    @BindView(R2.id.app_pay_rl)
    public RelativeLayout appPayRl;

    @BindView(R2.id.app_pay_count_tv)
    public TypeFaceView payCountTv;
    @BindView(R2.id.pay_tv)
    public TypeFaceView payTv;

    private MallDialog resultDialog;

    private ActHomePresenter presenter;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.mh_activity_pay;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);

        EventBus.getDefault().register(this);

        setTitleText(R.string.label_pay);

    }

    @Override
    protected void initBundleData(Intent intent) {

        if (intent == null)
            intent = getIntent();

        orderEntity = (OrderEntity) intent.getSerializableExtra(Constants.BundleConfig.ENTITY);

        appPayRl.setVisibility(normal ? View.VISIBLE : View.GONE);

        presenter = new ActHomePresenter(this, this);

        selectTab(alipayRl);

        type = Constants.InterfaceConfig.PAY_ALI;
        payCountTv.setText(ResourceUtils.getFormatText(R.string.label_app_pay_count, Variable.getInstance().getTokenCount()));
        payTv.setText(ResourceUtils.getFormatText(R.string.button_sdk_pay, FormatUtils.formatCurrencyD(orderEntity.getTotal())));
    }

    private void selectTab(View view) {

        if (!view.isSelected()) {
            alipayRl.setSelected(alipayRl == view);
            wxPayRl.setSelected(wxPayRl == view);
            appPayRl.setSelected(appPayRl == view);
        }
    }

    public void btClick(View view) {

        if (view.getId() == R.id.alipay_rl) {
            selectTab(alipayRl);
            type = Constants.InterfaceConfig.PAY_ALI;
            payTv.setText(ResourceUtils.getFormatText(R.string.button_sdk_pay, FormatUtils.formatCurrencyD(orderEntity.getTotal())));
        }

        if (view.getId() == R.id.wechat_pay_rl) {

            if (!CheckSystemUtils.isAvilible(mContext, "com.tencent.mm")) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_wx));
                return;
            }

            selectTab(wxPayRl);
            type = Constants.InterfaceConfig.PAY_WX;
            payTv.setText(ResourceUtils.getFormatText(R.string.button_sdk_pay, FormatUtils.formatCurrencyD(orderEntity.getTotal())));
        }

        if (view.getId() == R.id.app_pay_rl) {
            selectTab(appPayRl);
            type = Constants.InterfaceConfig.PAY_APP;
            payTv.setText(ResourceUtils.getFormatText(R.string.button_app_pay, FormatUtils.formatCurrencyD(orderEntity.getToken_price())));
        }

        if (view.getId() == R.id.pay_btn) {

            if (type == Constants.InterfaceConfig.PAY_APP && !Variable.getInstance().isPayPassword()) {
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_PAY_PASSWORD);
                return;
            }

            showLoadingDialog();
            presenter.orderPay(Constants.RequestConfig.ORDER_PAY, type, orderEntity.getOrder_sn(), orderEntity.getSubject());
        }

    }

    private void resultDialog(int type) {

        if (resultDialog == null) {
            resultDialog = new MallDialog(this, R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                    ActivityStack.getInstance().finishActivity(CommodityListActivity.class);
                    ActivityStack.getInstance().finishActivity(CommodityDetailsActivity.class);
                    ActivityStack.getInstance().finishActivity(CommodityOrderActivity.class);
                    onBackPressed();

                }

                @Override
                public void onClick(View v, Object... objects) {

                    RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MINE_ORDER);

                    ActivityStack.getInstance().finishActivity(CommodityListActivity.class);
                    ActivityStack.getInstance().finishActivity(CommodityDetailsActivity.class);
                    ActivityStack.getInstance().finishActivity(CommodityOrderActivity.class);
                    onBackPressed();

                }

                @Override
                public void dismiss() {

                }
            }, type);
            resultDialog.setCancelable(false);
        }

        if (!resultDialog.isShowing())
            resultDialog.show();
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        switch (code) {

            case Constants.RequestConfig.ORDER_PAY:

                if (type.equals(Constants.InterfaceConfig.PAY_ALI)) {
                    AliPayEntity entity = (AliPayEntity) object;
                    PayUtils.getInstance().aliPay(entity.getPay_info());
                }

                if (type.equals(Constants.InterfaceConfig.PAY_WX)) {

                    WxPayEntity entity = (WxPayEntity) object;

                    PayUtils.getInstance().wxPay(entity.getAppid(), entity.getPartnerid(), entity.getPackageX(),
                            entity.getNoncestr(), entity.getTimestamp(), entity.getPrepayid(), entity.getSign());

                }

                if (type == Constants.InterfaceConfig.PAY_APP) {
                    dismissLoadingDialog();
                    ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_pay_success));
                    resultDialog(MallDialog.PAY_SUCCEED);
                }
                break;

        }
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
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
