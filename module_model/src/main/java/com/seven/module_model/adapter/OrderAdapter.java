package com.seven.module_model.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.UserTypeUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.model.BusinessEntity;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_router.Constants;
import com.seven.module_model.R;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */

public class OrderAdapter extends BaseQuickAdapter<BusinessEntity, BaseViewHolder> {

    public OrderAdapter(int layoutResId, @Nullable List<BusinessEntity> data) {
        super(layoutResId, data);
        mContext = SSDK.getInstance().getContext();
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessEntity item) {

        GlideUtils.loadCircleImage(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.avatar_iv));

        helper.setText(R.id.name_tv, item.getUsername())
                .setText(R.id.radio_tv, ResourceUtils.getFormatText(R.string.radio, item.getRatio() + "%"))
                .setText(R.id.volume_tv, ResourceUtils.getFormatText(R.string.volume, item.getBusiness_success()))
                .setText(R.id.token_tv, String.valueOf(item.getToken_number()))
                .setText(R.id.price_tv, String.valueOf(item.getPrice()))
                .setText(R.id.status_tv, getStatus(item.getStatus()))
                .setImageResource(R.id.role_iv, UserTypeUtils.userTypeImage(item.getRole()))
                .setGone(R.id.status_rl, item.getStatus() != 0)
                .setGone(R.id.wechat_pay_iv, !TextUtils.isEmpty(item.getWx_account()))
                .setGone(R.id.alipay_iv, !TextUtils.isEmpty(item.getAli_account()))
                .setGone(R.id.business_layout, (!TextUtils.isEmpty(item.getWx_account()) ||
                        !TextUtils.isEmpty(item.getAli_account())) &&
                        item.getStatus() != Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS);
    }

    private String getStatus(int status) {

        switch (status) {

            case Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS:
                return ResourceUtils.getText(R.string.status_wait_business);

            case Constants.InterfaceConfig.BUSINESS_STATUS_UPLOAD:
                return ResourceUtils.getText(R.string.status_wait_upload);

            case Constants.InterfaceConfig.BUSINESS_STATUS_SURE:
                return ResourceUtils.getText(R.string.status_wait_sure);

            case Constants.InterfaceConfig.BUSINESS_STATUS_END:
                return ResourceUtils.getText(R.string.status_end);

            default:
                return "" + status;
        }

    }

}
