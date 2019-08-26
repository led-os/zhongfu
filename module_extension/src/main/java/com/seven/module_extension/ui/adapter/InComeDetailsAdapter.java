package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_model.model.extension.InComeItem;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class InComeDetailsAdapter extends BaseQuickAdapter<InComeItem,BaseViewHolder> {
    public InComeDetailsAdapter(int layoutResId, @Nullable List<InComeItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InComeItem item) {
        helper.setText(R.id.me_item_from,"奖励来源" + "\n" + item.getComment())
                .setText(R.id.me_item_content,item.getNumber() + "\n" + item.getCreated_at());
    }
}
