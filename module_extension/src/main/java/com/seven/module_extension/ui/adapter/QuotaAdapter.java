package com.seven.module_extension.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_model.model.extension.QuotaEntity;
import com.seven.lib_model.model.extension.QuotaItem;
import com.seven.module_extension.R;

import java.util.List;

/**
 * Created by xxxxxxH on 2019/5/14.
 */
public class QuotaAdapter extends BaseQuickAdapter<QuotaItem, BaseViewHolder> {
    public QuotaAdapter(int layoutResId, @Nullable List<QuotaItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuotaItem item) {
        helper.setText(R.id.me_item_master_index, helper.getPosition() + 1 + "")
                .setText(R.id.me_item_master_name, item.getPhone() != null && !TextUtils.isEmpty(item.getPhone()) ? "账户：" + item.getPhone() : "账户：暂未设置，点击设置");
    }
}
