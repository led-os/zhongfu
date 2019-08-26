package com.seven.module_model.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.model.model.BusinessEntity;
import com.seven.lib_model.presenter.model.ActModelPresenter;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_model.R;
import com.seven.module_model.R2;
import com.seven.module_model.adapter.OrderAdapter;
import com.seven.module_model.widget.decoration.VoucherDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */
@Route(path = RouterPath.ACTIVITY_VOUCHER)
public class VoucherActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.sell_btn)
    public RelativeLayout sellBtn;
    @BindView(R2.id.sell_iv)
    public ImageView sellIv;
    @BindView(R2.id.buy_btn)
    public RelativeLayout buyBtn;
    @BindView(R2.id.buy_iv)
    public ImageView buyIv;

    @BindView(R2.id.all_btn)
    public RelativeLayout allBtn;
    @BindView(R2.id.all_iv)
    public ImageView allIv;
    @BindView(R2.id.business_btn)
    public RelativeLayout businessBtn;
    @BindView(R2.id.business_iv)
    public ImageView businessIv;
    @BindView(R2.id.upload_btn)
    public RelativeLayout uploadBtn;
    @BindView(R2.id.upload_iv)
    public ImageView uploadIv;
    @BindView(R2.id.sure_btn)
    public RelativeLayout sureBtn;
    @BindView(R2.id.sure_iv)
    public ImageView sureIv;
    @BindView(R2.id.end_btn)
    public RelativeLayout endBtn;
    @BindView(R2.id.end_iv)
    public ImageView endIv;

    private int page = 1;
    private int pageSize = 10;
    public boolean isRefresh;
    private boolean isMoreEnd;
    @BindView(R2.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.recycler_view)
    public RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<BusinessEntity> orderList;

    private ActModelPresenter presenter;

    private int type;
    private int status;

    @Override
    protected int getLayoutId() {
        return R.layout.mm_activity_voucher;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        setTitleText(R.string.label_voucher);

    }

    @Override
    protected void initBundleData(Intent intent) {

        selectTab(sellBtn);
        selectTabSecond(allBtn);

        setRecyclerView();

        presenter = new ActModelPresenter(this, this);
        type = Constants.InterfaceConfig.BUSINESS_TYPE_BUY;
        status = Constants.InterfaceConfig.BUSINESS_STATUS_ALL;
        showLoadingDialog();
        request(page, type, status);
    }

    private void request(int page, int type, int status) {
        presenter.businessOrderList(Constants.RequestConfig.BUSINESS_ORDER_LIST, type, status, page, pageSize);
    }

    private void selectTab(View view) {

        if (!view.isSelected()) {
            buyBtn.setSelected(buyBtn == view);
            sellBtn.setSelected(sellBtn == view);

            buyIv.setVisibility(buyBtn.isSelected() ? View.VISIBLE : View.GONE);
            sellIv.setVisibility(sellBtn.isSelected() ? View.VISIBLE : View.GONE);

            if (presenter == null) return;
            type = buyBtn.isSelected() ? Constants.InterfaceConfig.BUSINESS_TYPE_SALE :
                    Constants.InterfaceConfig.BUSINESS_TYPE_BUY;
            isRefresh = true;
            page = 1;
            showLoadingDialog();
            request(page, type, status);
        }
    }

    private void selectTabSecond(View view) {

        if (!view.isSelected()) {
            allBtn.setSelected(allBtn == view);
            businessBtn.setSelected(businessBtn == view);
            uploadBtn.setSelected(uploadBtn == view);
            sureBtn.setSelected(sureBtn == view);
            endBtn.setSelected(endBtn == view);

            allIv.setVisibility(allBtn.isSelected() ? View.VISIBLE : View.GONE);
            businessIv.setVisibility(businessBtn.isSelected() ? View.VISIBLE : View.GONE);
            uploadIv.setVisibility(uploadBtn.isSelected() ? View.VISIBLE : View.GONE);
            sureIv.setVisibility(sureBtn.isSelected() ? View.VISIBLE : View.GONE);
            endIv.setVisibility(endBtn.isSelected() ? View.VISIBLE : View.GONE);

            if (presenter == null) return;

            if (allBtn.isSelected())
                status = Constants.InterfaceConfig.BUSINESS_STATUS_ALL;

            if (businessBtn.isSelected())
                status = Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS;

            if (uploadBtn.isSelected())
                status = Constants.InterfaceConfig.BUSINESS_STATUS_UPLOAD;

            if (sureBtn.isSelected())
                status = Constants.InterfaceConfig.BUSINESS_STATUS_SURE;

            if (endBtn.isSelected())
                status = Constants.InterfaceConfig.BUSINESS_STATUS_END;

            isRefresh = true;
            page = 1;
            showLoadingDialog();
            request(page, type, status);
        }
    }

    private void setRecyclerView() {

        adapter = new OrderAdapter(R.layout.mm_item_order, orderList);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SSDK.getInstance().getContext()));
        recyclerView.addItemDecoration(new VoucherDecoration());
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
                request(page, type, status);
            }
        });
    }

    private void loadMore() {

        if (isMoreEnd) {
            ToastUtils.showToast(SSDK.getInstance().getContext(), ResourceUtils.getText(R.string.hint_more_not));
            return;
        }
        page++;
        request(page, type, status);
    }

    public void btClick(View view) {

        if (view.getId() == R.id.sell_btn)
            selectTab(sellBtn);

        if (view.getId() == R.id.buy_btn)
            selectTab(buyBtn);

        if (view.getId() == R.id.all_btn)
            selectTabSecond(allBtn);

        if (view.getId() == R.id.business_btn)
            selectTabSecond(businessBtn);

        if (view.getId() == R.id.upload_btn)
            selectTabSecond(uploadBtn);

        if (view.getId() == R.id.sure_btn)
            selectTabSecond(sureBtn);

        if (view.getId() == R.id.end_btn)
            selectTabSecond(endBtn);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {
            case Constants.RequestConfig.BUSINESS_ORDER_LIST:

                if (object == null || ((List<BusinessEntity>) object).size() == 0) {
                    adapter.loadMoreEnd();
                    isMoreEnd = true;

                    if (page == 1)
                        adapter.setNewData(null);

                } else {
                    orderList = (List<BusinessEntity>) object;

                    if (isRefresh) {
                        adapter.setNewData(orderList);

                        isRefresh = false;
                        isMoreEnd = false;
                    } else {
                        adapter.addData(orderList);
                    }
                    adapter.loadMoreComplete();

                    if (orderList.size() < pageSize) {
                        adapter.loadMoreEnd();
                        isMoreEnd = true;
                    }
                }

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
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        BusinessEntity entity = this.adapter.getItem(position);

//        if (entity.getStatus() == Constants.InterfaceConfig.BUSINESS_STATUS_BUSINESS) {
//            ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_wait_business));
//        } else {

        ARouter.getInstance().build(RouterPath.ACTIVITY_TRANSACTION_DETAILS)
                .withInt(Constants.BundleConfig.TYPE, buyBtn.isSelected() ?
                        Constants.BundleConfig.TYPE_BUY : Constants.BundleConfig.TYPE_SELL)
                .withInt(Constants.BundleConfig.ID, entity.getId())
                .withBoolean(Constants.BundleConfig.DETAILS, true)
                .withInt(Constants.BundleConfig.STATUS, entity.getStatus())
                .navigation();

//        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getWhat()) {

            case Constants.EventConfig.BUSINESS_PROOF:

                int id = (int) ((ObjectsEvent) event).getObjects()[0];

                for (int i = 0; i < adapter.getData().size(); i++)
                    if (adapter.getItem(i).getId() == id)
                        adapter.remove(i);

                adapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
