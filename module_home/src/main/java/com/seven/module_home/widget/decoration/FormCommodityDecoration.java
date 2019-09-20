package com.seven.module_home.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_opensource.application.SSDK;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/5
 */

public class FormCommodityDecoration extends RecyclerView.ItemDecoration {

    public FormCommodityDecoration() {
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int totalCount = layoutManager.getItemCount();
        int surplusCount = totalCount % layoutManager.getSpanCount();
        int childPosition = parent.getChildAdapterPosition(view);

        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {

            if (childPosition % layoutManager.getSpanCount() == 0) {
                outRect.left = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 16);
                outRect.top = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 5);
                outRect.right = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 5);
                outRect.bottom = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 5);
            } else if (childPosition % layoutManager.getSpanCount() == 1) {
                outRect.left = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 5);
                outRect.top = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 5);
                outRect.right = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 16);
                outRect.bottom = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 5);
            }

        }
    }
}
