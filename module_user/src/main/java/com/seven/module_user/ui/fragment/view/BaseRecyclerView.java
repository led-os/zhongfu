package com.seven.module_user.ui.fragment.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.seven.module_user.R;
import com.seven.module_user.ui.fragment.utils.ThreadUtil;

import java.util.List;

/**
 * 封装的列表通用类，支持下拉刷新，上拉加载更多的接口
 */
public class BaseRecyclerView<T> extends FrameLayout {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter mQuickAdapter;
    private DividerListItemDecoration dividerListItemDecoration;

    public BaseRecyclerView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_recyclerview, this, true);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setEnabled(true);
        mRecyclerView = findViewById(R.id.rv_list);
    }

    /**
     * 初始化操作,默认支持下拉刷新
     *
     * @param quickAdapter adapter
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView init(BaseQuickAdapter quickAdapter) {
        init(null, quickAdapter);
        return this;
    }

    /**
     * 初始化操作,默认支持下拉刷新
     *
     * @param quickAdapter adapter
     * @param isRefresh    是否支持下拉刷新
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView init(BaseQuickAdapter quickAdapter, boolean isRefresh) {
        init(null, quickAdapter, isRefresh);
        return this;
    }

    /**
     * 初始化操作,默认支持下拉刷新
     *
     * @param layoutManager 什么类型的列表
     * @param quickAdapter  adapter
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter quickAdapter) {
        init(layoutManager, quickAdapter, true);
        return this;
    }

    /**
     * 初始化操作
     *
     * @param layoutManager 什么类型的列表
     * @param quickAdapter  adapter
     * @param isRefresh     是否支持下拉刷新
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter quickAdapter, boolean isRefresh) {
        if (!isRefresh) {
            mSwipeRefreshLayout.setVisibility(GONE);
            mRecyclerView = findViewById(R.id.rv_list1);
            mRecyclerView.setVisibility(VISIBLE);
        }
        mRecyclerView.setLayoutManager(layoutManager != null ? layoutManager : new LinearLayoutManager(getContext()));
        dividerListItemDecoration = new DividerListItemDecoration(getContext(), DividerListItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(dividerListItemDecoration);
        mQuickAdapter = quickAdapter;
        mQuickAdapter.openLoadAnimation();
       // mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());//添加默认的加载更多的view
        mRecyclerView.setAdapter(mQuickAdapter);
        mRecyclerView.setHasFixedSize(true);
        return this;
    }

    /**
     * 添加列表项点击事件
     *
     * @param onItemClickListener 点击监听
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView addOnItemClickListener(OnItemClickListener onItemClickListener) {
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
        return this;
    }

    /**
     * 添加列表项子控件点击事件
     *
     * @param onItemChildClickListener 点击监听
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView addOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mRecyclerView.addOnItemTouchListener(onItemChildClickListener);
        return this;
    }

    /**
     * 添加列表项子控件长按事件
     *
     * @param onItemChildLongClickListener 点击监听
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView addOnItemLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        mRecyclerView.addOnItemTouchListener(onItemChildLongClickListener);
        return this;
    }

    /**
     * 添加头部view
     *
     * @param header 头部view
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView addHeaderView(View header) {
        if (header != null) {
            mQuickAdapter.addHeaderView(header);
        }
        return this;
    }

    /**
     * 添加底部view
     *
     * @param footer 底部view
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView addFooterView(View footer) {
        mQuickAdapter.addFooterView(footer);
        return this;
    }

    /**
     * 设置加载更多的提示view
     *
     * @param view 加载更多的view
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView setLoadMoreView(LoadMoreView view) {
        mQuickAdapter.setLoadMoreView(view);
        return this;
    }

    /**
     * 设置空数据的时候显示什么view
     *
     * @param emptyView 空数据提示
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView setEmptyView(View emptyView) {
        mQuickAdapter.setEmptyView(emptyView);
        mQuickAdapter.setHeaderFooterEmpty(true, true);
        return this;
    }

    /**
     * 设置下拉刷新的事件监听
     *
     * @param listener 第
     */
    public BaseRecyclerView setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setOnRefreshListener(listener);
        return this;
    }

    /**
     * 设置加载更多的数据监听
     *
     * @param listener 监听器
     * @return 返回recyclerView实例
     */
    public BaseRecyclerView setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener listener) {
        mQuickAdapter.setOnLoadMoreListener(listener, mRecyclerView);
        return this;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public RecyclerView getRealRecyclerView() {
        return mRecyclerView;
    }

    public BaseRecyclerView setEnableLoadMore(boolean enable){
        mQuickAdapter.setEnableLoadMore(enable);
        return this;
    }

    /**
     * 获取adapter
     *
     * @return 返回adapter
     */
    public BaseQuickAdapter getAdapter() {
        return mQuickAdapter;
    }

    public void setRefreshing(boolean value) {
        mSwipeRefreshLayout.setRefreshing(value);
    }

    public void setNewData(List list) {
        mQuickAdapter.setNewData(list);


    }

    public void addDataList(List list) {
        mQuickAdapter.addData(list);
        mQuickAdapter.loadMoreComplete();

    }

    public void addDataList(int position, List list) {
        mQuickAdapter.addData(position, list);
        mQuickAdapter.loadMoreComplete();
    }

    public List<T> getData() {
        return mQuickAdapter.getData();
    }

    public void notifyDataSetChanged() {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {mQuickAdapter.notifyDataSetChanged();
            }
        });
    }

    public void notifyItemChanged(int position) {
        mQuickAdapter.notifyItemChanged(position);
    }

    public void notifyItemChanged(final int position, final int count) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mQuickAdapter.notifyItemChanged(position, count);
            }
        });
    }

    public void notifyItemChanged(final int position, final T count) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mQuickAdapter.notifyItemChanged(position, count);
            }
        });
    }

    /**
     * 获取指定位置的数据源
     *
     * @param position 位置下标
     * @return 数据源
     */
    public Object getDataByPosition(int position) {
        if (mQuickAdapter.getItemCount() > 0) {
            return mQuickAdapter.getItem(position);
        } else {
            return null;
        }
    }

    /**
     * 删除每行的分割线
     */
    public BaseRecyclerView removeItemDecoration() {
        mRecyclerView.removeItemDecoration(dividerListItemDecoration);
        return this;
    }

    /**
     * 替换分割线
     */
    public BaseRecyclerView changeItemDecoration(RecyclerView.ItemDecoration decoration) {
        mRecyclerView.removeItemDecoration(dividerListItemDecoration);
        mRecyclerView.addItemDecoration(decoration);
        return this;
    }

    /**
     * 滑动到指定位置
     */
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }
}
