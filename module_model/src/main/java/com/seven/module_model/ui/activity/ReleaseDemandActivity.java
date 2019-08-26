package com.seven.module_model.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.IKeyBoardVisibleListener;
import com.seven.lib_common.stextview.TypeFaceEdit;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.widget.password.GridPasswordView;
import com.seven.lib_model.presenter.model.ActModelPresenter;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_model.R;
import com.seven.module_model.R2;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */
@Route(path = RouterPath.ACTIVITY_RELEASE_DEMAND)
public class ReleaseDemandActivity extends BaseTitleActivity implements IKeyBoardVisibleListener {

    @BindView(R2.id.buy_btn)
    public RelativeLayout buyBtn;
    @BindView(R2.id.buy_iv)
    public ImageView buyIv;
    @BindView(R2.id.sell_btn)
    public RelativeLayout sellBtn;
    @BindView(R2.id.sell_iv)
    public ImageView sellIv;

    @BindView(R2.id.token_et)
    public TypeFaceEdit tokenEt;
    @BindView(R2.id.buy_token_et)
    public TypeFaceEdit buyTokenEt;
    @BindView(R2.id.price_tv)
    public TypeFaceView priceTv;
    @BindView(R2.id.price_et)
    public TypeFaceEdit priceEt;
    @BindView(R2.id.buy_price_et)
    public TypeFaceEdit buyPriceEt;

    @BindView(R2.id.sell_layout)
    public LinearLayout sellLayout;
    @BindView(R2.id.alipay_account_et)
    public TypeFaceEdit alipayEt;
    @BindView(R2.id.wechat_account_et)
    public TypeFaceEdit wechatEt;

    @BindView(R2.id.demand_tv)
    public TypeFaceView demandTv;

    @BindView(R2.id.password_layout)
    public RelativeLayout passwordLayout;
    @BindView(R2.id.password_tv)
    public GridPasswordView passwordView;
    @BindView(R2.id.password_bg)
    public ImageView passwordBg;

    private ActModelPresenter presenter;

    private int type;
    private String token;
    private String price;
    private String ali;
    private String wx;

    private boolean keyBoard;

    @Override
    protected int getLayoutId() {
        return R.layout.mm_activity_release_demand;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText(R.string.label_demand);

    }

    @Override
    protected void initBundleData(Intent intent) {

        selectTab(buyBtn);

        listener();

        presenter = new ActModelPresenter(this, this);

        alipayEt.setText(Variable.getInstance().getAliAccount());
        wechatEt.setText(Variable.getInstance().getWxAccount());

    }

    private void listener() {

        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

                if (psw.length() == 6) {
                    showLoadingDialog();
                    presenter.payPassword(Constants.RequestConfig.PAY_PASSWORD, psw);
                }

            }

            @Override
            public void onInputFinish(String psw) {

            }
        });
    }

    private void selectTab(View view) {

        if (!view.isSelected()) {
            buyBtn.setSelected(buyBtn == view);
            sellBtn.setSelected(sellBtn == view);

            buyIv.setVisibility(buyBtn.isSelected() ? View.VISIBLE : View.GONE);
            sellIv.setVisibility(sellBtn.isSelected() ? View.VISIBLE : View.GONE);

            priceTv.setText(buyBtn.isSelected() ? R.string.label_sale_price : R.string.label_buy_price);
            tokenEt.setVisibility(buyBtn.isSelected() ? View.VISIBLE : View.GONE);
            buyTokenEt.setVisibility(buyBtn.isSelected() ? View.GONE : View.VISIBLE);
            priceEt.setVisibility(buyBtn.isSelected() ? View.VISIBLE : View.GONE);
            buyPriceEt.setVisibility(buyBtn.isSelected() ? View.GONE : View.VISIBLE);

            sellLayout.setVisibility(buyBtn.isSelected() ? View.VISIBLE : View.GONE);
            demandTv.setText(buyBtn.isSelected() ? R.string.button_release_sale : R.string.button_release_buy);

            type = buyBtn.isSelected() ? Constants.InterfaceConfig.BUSINESS_SALE :
                    Constants.InterfaceConfig.BUSINESS_BUY;
        }
    }

    public void btClick(View view) {

        if (view.getId() == R.id.buy_btn)
            selectTab(buyBtn);

        if (view.getId() == R.id.sell_btn)
            selectTab(sellBtn);

        if (view.getId() == R.id.demand_btn) {

            if (buyBtn.isSelected() && TextUtils.isEmpty(tokenEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_token_number_1));
                return;
            }

            if (!buyBtn.isSelected() && TextUtils.isEmpty(buyTokenEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_token_number_2));
                return;
            }

            if (buyBtn.isSelected() && TextUtils.isEmpty(priceEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_sale_price));
                return;
            }

            if (!buyBtn.isSelected() && TextUtils.isEmpty(buyPriceEt.getText().toString())) {
                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_buy_price));
                return;
            }

            if (buyBtn.isSelected()) {

                if (TextUtils.isEmpty(alipayEt.getText().toString()) && TextUtils.isEmpty(wechatEt.getText().toString())) {
                    ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.label_receiving_account) +
                            ResourceUtils.getText(R.string.hint_receiving_account));
                    return;
                }

                token = buyBtn.isSelected() ? tokenEt.getText().toString() :
                        buyTokenEt.getText().toString();

                if(Double.parseDouble(token)==0){
                    ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_token_number_min));
                    return;
                }

//                if (Double.parseDouble(token) > Variable.getInstance().getTokenCount()) {
//                    ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_token_number_insufficient));
//                    return;
//                }
            }

            if(!Variable.getInstance().isPayPassword()){
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_PAY_PASSWORD);
                return;
            }

            keyBoard = true;
            passwordView.clearPassword();
            passwordView.forceInputViewGetFocus();
        }
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {

            case Constants.RequestConfig.PAY_PASSWORD:

                token = buyBtn.isSelected() ? tokenEt.getText().toString() :
                        buyTokenEt.getText().toString();

                price = buyBtn.isSelected() ? priceEt.getText().toString() :
                        buyPriceEt.getText().toString();

                ali = TextUtils.isEmpty(alipayEt.getText().toString()) ? "" :
                        alipayEt.getText().toString();

                wx = TextUtils.isEmpty(wechatEt.getText().toString()) ? "" :
                        wechatEt.getText().toString();

                presenter.business(Constants.RequestConfig.BUSINESS, type,
                        Double.parseDouble(token), Double.parseDouble(price), ali, wx);

                break;

            case Constants.RequestConfig.BUSINESS:

                ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_release_succeed));

                if (!buyBtn.isSelected() && TextUtils.isEmpty(Variable.getInstance().getAliAccount()) &&
                        TextUtils.isEmpty(Variable.getInstance().getWxAccount()))
                    ARouter.getInstance().build(RouterPath.ACTIVITY_ACCOUNT).navigation();

                onBackPressed();
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

        dismissLoadingDialog();

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ScreenUtils.addOnSoftKeyBoardVisibleListener(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScreenUtils.removeOnSoftKeyBoardVisibleListener();
    }

    @Override
    public void onSoftKeyBoardVisible(boolean visible, int windowBottom) {

        if (!keyBoard) return;

        if (!visible)
            keyBoard = false;

        passwordLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        passwordBg.setVisibility(visible ? View.VISIBLE : View.GONE);
        passwordLayout.setPadding(0, 0, 0, visible ? windowBottom : 0);

    }
}
