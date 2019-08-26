package com.seven.module_home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_opensource.application.SSDK;
import com.seven.module_home.R;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/18
 */

public class CommodityDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int width;
    private int height;

    public CommodityDetailsAdapter(int layoutResId, @Nullable List<String> data, int screenWidth) {
        super(layoutResId, data);
        mContext = SSDK.getInstance().getContext();
        width = screenWidth - ScreenUtils.dip2px(mContext, 16 * 2);
        height = ScreenUtils.getScaling16_9(width);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        LinearLayout layout = helper.getView(R.id.item_layout);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.height = height;
        layout.setLayoutParams(params);

        GlideUtils.loadImage(mContext, item, (ImageView) helper.getView(R.id.cover_iv));
    }
}
