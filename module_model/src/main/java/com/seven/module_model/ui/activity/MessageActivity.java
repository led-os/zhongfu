package com.seven.module_model.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.model.model.MessageEntity;
import com.seven.lib_model.presenter.model.ActModelPresenter;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_model.R;
import com.seven.module_model.R2;
import com.seven.module_model.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */
@Route(path = RouterPath.ACTIVITY_MESSAGE)
public class MessageActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemClickListener {

    private int page = 1;
    private int pageSize = 10;
    public boolean isRefresh;
    private boolean isMoreEnd;

    @BindView(R2.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.recycler_view)
    public RecyclerView recyclerView;
    public MessageAdapter adapter;
    private List<MessageEntity> messageList;

    private ActModelPresenter presenter;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.mm_activity_message;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText(R.string.label_message);

    }

    @Override
    protected void initBundleData(Intent intent) {

        setRecyclerView();

        presenter = new ActModelPresenter(this, this);
        showLoadingDialog();
        request(page);
    }

    private void request(int page) {

        presenter.messageList(Constants.RequestConfig.MESSAGE_LIST);

    }

    private void setRecyclerView() {

        adapter = new MessageAdapter(R.layout.mh_item_message, messageList);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SSDK.getInstance().getContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_1,
                R.color.refresh_2,
                R.color.refresh_3,
                R.color.refresh_4);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkUtils.isNetWork()) {
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                isRefresh = true;
                page = 1;
                request(page);
            }
        });

    }

    private void loadMore() {

        if (isMoreEnd) {
            ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_more_not));
            return;
        }
        page++;
        request(page);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {

            case Constants.RequestConfig.MESSAGE_LIST:

                if (object == null || ((List<MessageEntity>) object).size() == 0) return;

                messageList = (List<MessageEntity>) object;

                if (isRefresh) {
                    adapter.setNewData(messageList);

                    isRefresh = false;
                    isMoreEnd = false;
                } else {
                    adapter.addData(messageList);
                }
                adapter.loadMoreComplete();

                if (messageList.size() < pageSize) {
                    adapter.loadMoreEnd();
                    isMoreEnd = true;
                }

                break;

            case Constants.RequestConfig.MESSAGE_READ:

                messageList.get(position).setIs_read(1);
                this.adapter.notifyItemChanged(position);

                break;

        }

    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

        dismissLoadingDialog();

        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (this.adapter.getItem(position).getIs_read() == 0) {
            this.position = position;
            presenter.messageRead(Constants.RequestConfig.MESSAGE_READ, this.adapter.getItem(position).getId());
        }

        if (this.adapter.getItem(position).getType() == 1) {
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MINE_ORDER);
        } else {
            ARouter.getInstance().build(RouterPath.ACTIVITY_TRANSACTION_DETAILS)
                    .withInt(Constants.BundleConfig.TYPE, this.adapter.getItem(position).getType())
                    .withInt(Constants.BundleConfig.ID, this.adapter.getItem(position).getTrigger_id())
                    .withBoolean(Constants.BundleConfig.DETAILS, true)
                    .withInt(Constants.BundleConfig.STATUS, 0)
                    .navigation();
        }

    }
}
