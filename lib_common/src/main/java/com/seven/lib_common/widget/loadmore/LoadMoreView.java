package com.seven.lib_common.widget.loadmore;


import com.seven.lib_common.R;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/25
 */

public class LoadMoreView extends com.chad.library.adapter.base.loadmore.LoadMoreView {

    @Override
    public int getLayoutId() {

        return R.layout.lb_load_more_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

}
