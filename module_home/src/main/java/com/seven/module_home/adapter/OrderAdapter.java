package com.seven.module_home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.home.CartEntity;
import com.seven.lib_model.model.home.OrderListEntity;
import com.seven.lib_opensource.application.SSDK;
import com.seven.module_home.R;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class OrderAdapter extends BaseQuickAdapter<OrderListEntity.ItemsBean,BaseViewHolder> {

    public OrderAdapter(int layoutResId, @Nullable List<OrderListEntity.ItemsBean> data) {
        super(layoutResId, data);
        mContext= SSDK.getInstance().getContext();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListEntity.ItemsBean item) {

        GlideUtils.loadImage(mContext, item.getGoods_thumb(), (ImageView) helper.getView(R.id.cover_iv));

        helper.setText(R.id.commodity_tv,item.getGoods_name())
                .setText(R.id.specification_tv,item.getGoods_sku_name())
                .setText(R.id.price_tv, FormatUtils.formatCurrencyD(item.getPrice()))
                .setText(R.id.number_tv,String.valueOf(item.getNumber()))
                .setText(R.id.commodity_price_tv,FormatUtils.formatCurrencyD(item.getPrice()));

    }
}
