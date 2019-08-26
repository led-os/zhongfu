package com.seven.module_home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_common.utils.OutlineUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.home.CommodityEntity;
import com.seven.lib_opensource.application.SSDK;
import com.seven.module_home.R;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/5
 */

public class HomeAdapter extends BaseQuickAdapter<CommodityEntity, BaseViewHolder> {

    private int diameter;

    public HomeAdapter(int layoutResId, @Nullable List<CommodityEntity> data, int screenWidth) {
        super(layoutResId, data);
        mContext = SSDK.getInstance().getContext();
        diameter = (screenWidth - ScreenUtils.dip2px(mContext, 16 * 2 + 10)) / 2;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityEntity item) {

        ImageView imageView = helper.getView(R.id.cover_iv);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.width = diameter;
        params.height = diameter;
        imageView.setLayoutParams(params);

        GlideUtils.loadImage(mContext, item.getThumb(), imageView);
        helper.setText(R.id.title_tv, item.getGoods_name())
                .setText(R.id.price_tv, FormatUtils.formatCurrencyD(item.getPrice()))
                .setText(R.id.token_tv,ResourceUtils.getFormatText(R.string.hint_token, FormatUtils.formatCurrencyD(item.getToken_price())))
                .setText(R.id.buy_count_tv, ResourceUtils.getFormatText(R.string.buy_count, item.getSales()));

    }
}
