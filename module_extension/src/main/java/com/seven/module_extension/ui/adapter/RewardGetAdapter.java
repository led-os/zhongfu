package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_model.model.extension.RewardItem;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class RewardGetAdapter extends BaseQuickAdapter<RewardItem,BaseViewHolder> {
    public RewardGetAdapter(int layoutResId, @Nullable List<RewardItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RewardItem item) {
        helper.setText(R.id.me_item_get_title,item.getReward_name());
    }
}
