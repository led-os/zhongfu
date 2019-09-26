package com.seven.lib_common.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.R;
import com.seven.lib_common.entity.SheetCommonEntity;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/29
 */

public class SheetCommonAdapter extends BaseQuickAdapter<SheetCommonEntity, BaseViewHolder> {

    public SheetCommonAdapter(@LayoutRes int layoutResId, @Nullable List<SheetCommonEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetCommonEntity item) {

        helper.setGone(R.id.common_line, item.isShowLine())
                .setText(R.id.common_text_tv, item.getText())
                .setTextColor(R.id.common_text_tv, item.getTextColor());

    }
}
