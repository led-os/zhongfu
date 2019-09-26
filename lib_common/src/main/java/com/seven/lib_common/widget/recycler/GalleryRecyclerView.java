package com.seven.lib_common.widget.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/12/2
 */

public class GalleryRecyclerView extends RecyclerView {

    private int listSize;

    public GalleryRecyclerView(Context context) {
        super(context);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
//        if (layoutManager instanceof LinearLayoutManager) {
//            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//            int lastItemPosition = linearManager.findLastVisibleItemPosition();
//            if (lastItemPosition == listSize - 1)
//                getParent().requestDisallowInterceptTouchEvent(true);
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
