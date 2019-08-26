package com.seven.module_model.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
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

public class VoucherDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int totalCount = layoutManager.getItemCount();
        int childPosition = parent.getChildAdapterPosition(view);

        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {

            outRect.top = childPosition == 0 ? ScreenUtils.dip2px(SSDK.getInstance().getContext(),
                    10) : 0;
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = ScreenUtils.dip2px(SSDK.getInstance().getContext(),
                    childPosition == totalCount - 1 ? 20 : 10);

        }
    }
}
