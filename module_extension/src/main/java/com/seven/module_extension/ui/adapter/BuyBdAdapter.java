package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.extension.BdGoodsEntity;
import com.seven.lib_model.model.extension.GoodsItemEntity;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/25.
 */
public class BuyBdAdapter extends BaseQuickAdapter<BdGoodsEntity, BaseViewHolder> {
    public BuyBdAdapter(int layoutResId, @Nullable List<BdGoodsEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BdGoodsEntity item) {
        ImageView mt_item_buybd_iv = helper.getView(R.id.mt_item_buybd_iv);
        GlideUtils.loadRoundImage(mContext, item.getGoods_list().get(0).getThumb(), ScreenUtils.dip2px(mContext, 135), ScreenUtils.dip2px(mContext, 135), ScreenUtils.dip2px(mContext, 2), mt_item_buybd_iv, true);
        helper.setText(R.id.me_item_buybd_name, item.getGoods_list().get(0).getGoods_name())
                .setText(R.id.me_item_buybd_num, "x" + item.getGoods_list().get(0).getNumber());
        try {
            helper.setText(R.id.me_item_buybd_price, !TextUtils.isEmpty(String.valueOf(item.getPrice())) ? "￥"+String.valueOf((int)item.getPrice()) : "");
            if (item.isSelected()) {
                helper.setBackgroundRes(R.id.me_item_buybd_ck, R.drawable.me_select);
            } else {
                helper.setBackgroundRes(R.id.me_item_buybd_ck, R.drawable.me_unselect);
            }
        }catch (Exception e){
            ToastUtils.showToast(mContext,"a");
        }

    }
}
