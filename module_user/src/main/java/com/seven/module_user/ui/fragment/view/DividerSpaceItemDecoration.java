package com.seven.module_user.ui.fragment.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class DividerSpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public DividerSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildLayoutPosition(view) != 0)
            outRect.top = space;
    }
}