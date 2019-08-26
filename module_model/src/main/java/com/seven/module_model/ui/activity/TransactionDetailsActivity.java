package com.seven.module_model.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.ui.dialog.MallDialog;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.UserTypeUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.model.BusinessInfoEntity;
import com.seven.lib_model.presenter.model.ActModelPresenter;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_model.R;
import com.seven.module_model.R2;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */
@Route(path = RouterPath.ACTIVITY_TRANSACTION_DETAILS)
public class TransactionDetailsActivity extends BaseTitleActivity implements View.OnLongClickListener{

    @Autowired(name = Constants.BundleConfig.TYPE)
    public int type;
    @Autowired(name = Constants.BundleConfig.ID)
    public int id;
    @Autowired(name = Constants.BundleConfig.DETAILS)
    public boolean details;
    @Autowired(name = Constants.BundleConfig.STATUS)
    public int status;

    @BindView(R2.id.scrollView)
    public NestedScrollView scrollView;
    @BindView(R2.id.status_business_tv)
    public TypeFaceView statusBusinessTv;

    @BindView(R2.id.info_hint)
    public TypeFaceView infoHint;
    @BindView(R2.id.name_tv)
    public TypeFaceView nameTv;
    @BindView(R2.id.avatar_iv)
    public ImageView avatarIv;
    @BindView(R2.id.radio_tv)
    public TypeFaceView radioTv;
    @BindView(R2.id.volume_tv)
    public TypeFaceView volumeTv;
    @BindView(R2.id.role_iv)
    public ImageView roleIv;

    @BindView(R2.id.alipay_account_tv)
    public TypeFaceView alipayAccountTv;
    @BindView(R2.id.wechat_account_tv)
    public TypeFaceView wechatAccountTv;
    @BindView(R2.id.status_tv)
    public TypeFaceView statusTv;
    @BindView(R2.id.token_tv)
    public TypeFaceView tokenTv;
    @BindView(R2.id.price_tv)
    public TypeFaceView priceTv;

    @BindView(R2.id.voucher_layout)
    public LinearLayout voucherLayout;
    @BindView(R2.id.voucher_iv)
    public ImageView voucherIv;

    @BindView(R2.id.transaction_btn)
    public RelativeLayout transactionBtn;
    @BindView(R2.id.transaction_tv)
    public TypeFaceView transactionTv;

    private MallDialog transactionDialog;

    private ActModelPresenter presenter;
    private BusinessInfoEntity infoEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.mm_acitivity_transaction_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);

        setTitleText(R.string.title_transaction_details);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) voucherIv.getLayoutParams();
        params.width = (screenWidth - ScreenUtils.dip2px(mContext, 16 * 2)) / 3;
        params.height = (screenWidth - ScreenUtils.dip2px(mContext, 16 * 2)) / 3;
        voucherIv.setLayoutParams(params);

        alipayAccountTv.setOnLongClickListener(this);
        wechatAccountTv.setOnLongClickListener(this);
    }

    @Override
    protected void initBundleData(Intent intent) {

        presenter = new ActModelPresenter(this, this);

        if (status == Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS) {
            scrollView.setVisibility(View.GONE);
            statusBusinessTv.setVisibility(View.VISIBLE);
            transactionBtn.setVisibility(View.VISIBLE);
            transactionTv.setText(R.string.button_cancel_business);
            return;
        }

        request();
    }

    private void request() {
        showLoadingDialog();
        if (details)
            presenter.businessOrderInfo(Constants.RequestConfig.BUSINESS_ORDER_INFO, id);
        else
            presenter.businessInfo(Constants.RequestConfig.BUSINESS_INFO, id);
    }

    private void showInfo(BusinessInfoEntity entity) {

        status = entity.getStatus();

        infoHint.setText(type == Constants.BundleConfig.TYPE_SELL ?
                R.string.label_buy_info : R.string.label_sell_info);
        transactionTv.setText(type == Constants.BundleConfig.TYPE_SELL ?
                R.string.button_launch_sell : R.string.button_launch_buy);

        GlideUtils.loadCircleImage(mContext, entity.getAvatar(), avatarIv);
        roleIv.setImageResource(UserTypeUtils.userTypeImage(entity.getRole()));

        if (entity.getStatus() >= Constants.InterfaceConfig.BUSINESS_STATUS_SURE) {
            voucherLayout.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(mContext, entity.getProof_picture(), voucherIv);
        }

        nameTv.setText(entity.getUsername());
        radioTv.setText(ResourceUtils.getFormatText(R.string.radio, entity.getRatio() + "%"));
        volumeTv.setText(ResourceUtils.getFormatText(R.string.volume, entity.getBusiness_success()));

        if (!details && type == Constants.BundleConfig.TYPE_SELL) {
            alipayAccountTv.setText("：" + (Variable.getInstance().getAliAccount() == null ? "" : Variable.getInstance().getAliAccount()));
            wechatAccountTv.setText("：" + (Variable.getInstance().getWxAccount() == null ? "" : Variable.getInstance().getWxAccount()));
        } else {
            alipayAccountTv.setText("：" + entity.getAli_account());
            wechatAccountTv.setText("：" + entity.getWx_account());
        }
        statusTv.setText("(交易状态)");
        tokenTv.setText(FormatUtils.formatCurrencyD(entity.getToken_number()));
        priceTv.setText(FormatUtils.formatCurrencyD(entity.getPrice()));

        if (entity.getStatus() == Constants.InterfaceConfig.BUSINESS_STATUS_ALL)
            transactionBtn.setVisibility(View.VISIBLE);

        if (entity.getStatus() == Constants.InterfaceConfig.BUSINESS_STATUS_UPLOAD &&
                type == Constants.BundleConfig.TYPE_BUY) {
            transactionBtn.setVisibility(View.VISIBLE);
            transactionTv.setText(R.string.button_voucher);
        }

        if (entity.getStatus() == Constants.InterfaceConfig.BUSINESS_STATUS_SURE &&
                type == Constants.BundleConfig.TYPE_SELL) {
            transactionBtn.setVisibility(View.VISIBLE);
            transactionTv.setText(R.string.label_commodity_order);
        }

    }

    public void btClick(View view) {

        if (view.getId() == R.id.transaction_btn) {

            if (!isLogin()) return;

            if (status == Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS) {
                showLoadingDialog();
                presenter.businessCancel(Constants.RequestConfig.BUSINESS_CANCEL, id);
                return;
            }

            //待上传状态 买方
            if (infoEntity.getStatus() == Constants.InterfaceConfig.BUSINESS_STATUS_UPLOAD &&
                    type == Constants.BundleConfig.TYPE_BUY) {
                ARouter.getInstance().build(RouterPath.ACTIVITY_UPLOAD_VOUCHER).
                        withInt(Constants.BundleConfig.ID, id)
                        .navigation();
                return;
            }

            //待确认状态 卖方
            if (infoEntity.getStatus() == Constants.InterfaceConfig.BUSINESS_STATUS_SURE &&
                    type == Constants.BundleConfig.TYPE_SELL) {

                showLoadingDialog();
                presenter.businessConfirm(Constants.RequestConfig.BUSINESS_CONFIRM, id);
                return;
            }

            //售卖 无收款账号
            if (type == Constants.BundleConfig.TYPE_SELL &&
                    TextUtils.isEmpty(Variable.getInstance().getAliAccount()) &&
                    TextUtils.isEmpty(Variable.getInstance().getWxAccount())) {
                ARouter.getInstance().build(RouterPath.ACTIVITY_ACCOUNT).navigation();
                return;
            }

            //发起售卖、购买
            showLoadingDialog();
            presenter.businessAccept(Constants.RequestConfig.BUSINESS_ACCEPT, id);

        }

    }

    private void showBuyDialog() {

        if (transactionDialog == null) {

            String content = "";

            if (status == Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS)
                content = ResourceUtils.getText(R.string.hint_cancel_succeed);
            else
                content = ResourceUtils.getText(type == Constants.BundleConfig.TYPE_SELL ?
                        R.string.hint_sell_info : R.string.hint_buy_info);

            transactionDialog = new MallDialog(this, R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                }

                @Override
                public void onClick(View v, Object... objects) {

                }

                @Override
                public void dismiss() {
                    onBackPressed();
                }
            }, MallDialog.TRANSACTION, content);
        }

        if (!transactionDialog.isShowing())
            transactionDialog.show();
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {

            case Constants.RequestConfig.BUSINESS_INFO:
            case Constants.RequestConfig.BUSINESS_ORDER_INFO:

                if (object == null) return;

                infoEntity = (BusinessInfoEntity) object;

                showInfo(infoEntity);

                break;

            case Constants.RequestConfig.BUSINESS_CANCEL:
            case Constants.RequestConfig.BUSINESS_ACCEPT:

                showBuyDialog();

                break;

            case Constants.RequestConfig.BUSINESS_CONFIRM:

                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.BUSINESS_PROOF, id));

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
        if (!details && type == Constants.BundleConfig.TYPE_SELL) {
            alipayAccountTv.setText("：" + (Variable.getInstance().getAliAccount() == null ? "" : Variable.getInstance().getAliAccount()));
            wechatAccountTv.setText("：" + (Variable.getInstance().getWxAccount() == null ? "" : Variable.getInstance().getWxAccount()));
        }
    }

    @Override
    public boolean onLongClick(View view) {

        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clipData=null;
        if(view.getId()==R.id.alipay_account_tv){
            clipData = ClipData.newPlainText(null, alipayAccountTv.getText().toString());
        }

        if(view.getId()==R.id.wechat_account_tv){
            clipData = ClipData.newPlainText(null, wechatAccountTv.getText().toString());
        }

        clipboard.setPrimaryClip(clipData);

        ToastUtils.showToast(mContext,ResourceUtils.getText(R.string.hint_copy));

        return false;
    }
}
