package com.seven.module_home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_model.model.home.BannerEntranceEntity;
import com.seven.lib_opensource.application.SSDK;
import com.seven.module_home.R;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/14
 */

public class BannerEntranceListAdapter extends BaseQuickAdapter<List<BannerEntranceEntity>, BaseViewHolder> {

    private int radius;

    private RecyclerView recyclerView;
    private BannerEntranceAdapter adapter;
    private OnItemClickListener listener;

    public BannerEntranceListAdapter(int layoutResId, @Nullable List<List<BannerEntranceEntity>> data, int screenWith,
                                     OnItemClickListener listener) {
        super(layoutResId, data);
        mContext = SSDK.getInstance().getContext();
        this.listener = listener;
        radius = (screenWith - ScreenUtils.dip2px(mContext, 4 * 2)) / 5;
    }

    @Override
    protected void convert(BaseViewHolder helper, List<BannerEntranceEntity> item) {

        recyclerView = helper.getView(R.id.recycler_view);
        adapter = new BannerEntranceAdapter(R.layout.mh_item_banner_entrance, item, radius);
        adapter.setOnItemClickListener(listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
