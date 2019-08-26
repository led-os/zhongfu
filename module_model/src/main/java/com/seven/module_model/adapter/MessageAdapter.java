package com.seven.module_model.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.TimeUtils;
import com.seven.lib_model.model.model.MessageEntity;
import com.seven.module_model.R;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */

public class MessageAdapter extends BaseQuickAdapter<MessageEntity, BaseViewHolder> {
    public MessageAdapter(int layoutResId, @Nullable List<MessageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageEntity item) {

        helper.setText(R.id.content_tv, item.getContent())
                .setText(R.id.time_tv, TimeUtils.millisecondToFull(System.currentTimeMillis()))
                .setGone(R.id.read_iv, item.getIs_read() == 0);

    }
}
