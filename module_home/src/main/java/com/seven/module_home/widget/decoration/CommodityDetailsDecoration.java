package com.seven.module_home.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_opensource.application.SSDK;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/5
 */

public class CommodityDetailsDecoration extends RecyclerView.ItemDecoration {

    private int headerCount;

    public CommodityDetailsDecoration(int headerCount) {
        this.headerCount = headerCount;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int totalCount = layoutManager.getItemCount();
        int childPosition = parent.getChildAdapterPosition(view);

        if (childPosition < headerCount) return;

        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {

            outRect.top = ScreenUtils.dip2px(SSDK.getInstance().getContext(), 16);
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = childPosition == totalCount - 1 ?
                    ScreenUtils.dip2px(SSDK.getInstance().getContext(), 60) : 0;

        }
    }
}
