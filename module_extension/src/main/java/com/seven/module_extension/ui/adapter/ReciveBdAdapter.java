package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.extension.GoodsItemEntity;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/20.
 */
public class ReciveBdAdapter extends BaseQuickAdapter<GoodsItemEntity,BaseViewHolder> {
    public ReciveBdAdapter(int layoutResId, @Nullable List<GoodsItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsItemEntity item) {
        ImageView mt_item_buybd_iv = helper.getView(R.id.mt_item_buybd_iv);
        GlideUtils.loadRoundImage(mContext, item.getThumb(), ScreenUtils.dip2px(mContext, 135), ScreenUtils.dip2px(mContext, 135), ScreenUtils.dip2px(mContext, 2), mt_item_buybd_iv, true);
        helper.setText(R.id.me_item_buybd_name, item.getGoods_name())
                .setText(R.id.me_item_buybd_num, "x" + item.getNumber())
        .setGone(R.id.me_item_buybd_ck,false);
    }
}
