package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.extension.ItemsBean;
import com.seven.lib_model.model.extension.MyInterViewEntity;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/3.
 */
public class MyInviteAdapter extends BaseQuickAdapter<ItemsBean, BaseViewHolder> {
    public MyInviteAdapter(int layoutResId, @Nullable List<ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemsBean item) {
        GlideUtils.loadCircleImage(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.me_item_myinterview_iv));
        helper.setText(R.id.me_item_interview_name, item.getUsername());
        ImageView sex = helper.getView(R.id.me_item_interview_sex);
        if (item.getSex().equals("male")) {
            sex.setBackgroundResource(R.drawable.me_male);
        } else {
            sex.setBackgroundResource(R.drawable.me_famale);
        }
        ImageView level = helper.getView(R.id.me_item_interview_level);
        switch (item.getRole()) {
            case 0:
                level.setBackground(mContext.getResources().getDrawable(R.drawable.me_normaluser));
                break;
            case 1:
                level.setBackground(mContext.getResources().getDrawable(R.drawable.me_vip));
                break;
            case 2:
                level.setBackground(mContext.getResources().getDrawable(R.drawable.me_kuangzhu));
                break;
            case 3:
                level.setBackground(mContext.getResources().getDrawable(R.drawable.me_changzhu));
                break;
            case 4:
                level.setBackground(mContext.getResources().getDrawable(R.drawable.ctylord));
                break;
            default:
        }
    }
}
