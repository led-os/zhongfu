package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_model.model.extension.RewardRuleEntity;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/24.
 */
public class RewardRuleAdapter extends BaseQuickAdapter<RewardRuleEntity,BaseViewHolder> {
    public RewardRuleAdapter(int layoutResId, @Nullable List<RewardRuleEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RewardRuleEntity item) {
            helper.setText(R.id.me_reward_tv1,item.getName())
                    .setText(R.id.me_reward_tv1_1,item.getValue())
                    .setText(R.id.me_reward_tv1_2,item.getUnit());
    }
}
