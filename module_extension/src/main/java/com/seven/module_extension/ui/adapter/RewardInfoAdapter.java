package com.seven.module_extension.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_model.model.extension.RewardInfoLlistEntity;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
public class RewardInfoAdapter extends BaseQuickAdapter<RewardInfoLlistEntity,BaseViewHolder> {
    public RewardInfoAdapter(int layoutResId, @Nullable List<RewardInfoLlistEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RewardInfoLlistEntity item) {
        TextView me_item_btn = helper.getView(R.id.me_item_btn);
        helper.setText(R.id.me_item_lingqu_type,item.getReward_name())
                .setText(R.id.me_lingqu_num,item.getReward_number()+"")
                .setText(R.id.me_item_lingqushijian,item.getDatetime())
                .setText(R.id.me_item_fafangshijian,item.getReward_desc());
        switch (item.getCan_processed()){
            case 1:
                me_item_btn.setText("已领取");
                me_item_btn.setTextColor(Color.parseColor("#abaeb3"));
                me_item_btn.setBackgroundResource(R.drawable.me_grey_btn);
                break;
            case 2:
                me_item_btn.setText("立即领取");
                me_item_btn.setTextColor(mContext.getResources().getColor(R.color.primary));
                me_item_btn.setBackgroundResource(R.drawable.me_get_btn_bg);
                helper.addOnClickListener(R.id.me_item_btn);
                break;
            case 3:
                me_item_btn.setText("时间未到");
                me_item_btn.setTextColor(Color.parseColor("#abaeb3"));
                me_item_btn.setBackgroundResource(R.drawable.me_grey_btn);
                break;
            default:
        }
    }
}
