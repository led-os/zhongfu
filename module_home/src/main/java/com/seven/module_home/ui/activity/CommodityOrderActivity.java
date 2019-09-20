package com.seven.module_home.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_model.builder.home.OrderAddBuilder;
import com.seven.lib_model.model.home.CartEntity;
import com.seven.lib_model.model.home.ContactDefaultEntity;
import com.seven.lib_model.model.home.OrderEntity;
import com.seven.lib_model.model.home.OrderListEntity;
import com.seven.lib_model.presenter.home.ActHomePresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.MessageEvent;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_home.R;
import com.seven.module_home.R2;
import com.seven.module_home.adapter.OrderAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/19
 */
@Route(path = RouterPath.ACTIVITY_COMMODITY_ORDER)
public class CommodityOrderActivity extends BaseTitleActivity {

    public List<CartEntity> cartList;

    @BindView(R2.id.name_tv)
    public TypeFaceView nameTv;
    @BindView(R2.id.mobile_tv)
    public TypeFaceView mobileTv;
    @BindView(R2.id.address_rl)
    public RelativeLayout defaultRl;
    @BindView(R2.id.address_tv)
    public TypeFaceView addressTv;

    @BindView(R2.id.recycler_view)
    public RecyclerView recyclerView;
    private OrderAdapter adapter;

    @BindView(R2.id.amount_tv)
    public TypeFaceView amountTv;
    private double amount;

    private ActHomePresenter presenter;
    private OrderListEntity listEntity;
    private ContactDefaultEntity contactEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.mh_activity_commodity_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        setTitleText(R.string.label_commodity_order);

    }

    @Override
    protected void initBundleData(Intent intent) {

        if (intent == null)
            intent = getIntent();
        cartList = (List<CartEntity>) intent.getSerializableExtra(Constants.BundleConfig.ENTITY);

        if (cartList == null || cartList.size() == 0) return;

        setRecyclerView();

        presenter = new ActHomePresenter(this, this);
        showLoadingDialog();
        request();
    }

    private void request() {
        presenter.orderPayment(Constants.RequestConfig.ORDER_PAYMENT, cartList.get(0).getFrom(),
                cartList.get(0).getCart_ids(), cartList.get(0).getId(), cartList.get(0).getSku_id(), cartList.get(0).getNumber());
        presenter.contactDefault(Constants.RequestConfig.CONTACT_DEFAULT);
    }

    private void setRecyclerView() {

        adapter = new OrderAdapter(R.layout.mh_item_order, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {
            case Constants.RequestConfig.ORDER_PAYMENT:

                if (object == null) return;

                listEntity = (OrderListEntity) object;

                adapter.setNewData(listEntity.getItems());

                for (OrderListEntity.ItemsBean itemsBean : listEntity.getItems())
                    amount += itemsBean.getPrice();

                amountTv.setText("¥ " + FormatUtils.formatCurrencyD(amount));

                break;

            case Constants.RequestConfig.CONTACT_DEFAULT:

                if (object == null) return;

                contactEntity = (ContactDefaultEntity) object;

                if (!TextUtils.isEmpty(contactEntity.getContact_name()))
                    nameTv.setText(contactEntity.getContact_name());
                if (!TextUtils.isEmpty(contactEntity.getContact_phone()))
                    mobileTv.setText(contactEntity.getContact_phone());
                if (!TextUtils.isEmpty(contactEntity.getAddress()))
                    addressTv.setText(contactEntity.getProvince_name() + contactEntity.getCity_name() +
                            contactEntity.getDistrict_name() + contactEntity.getAddress());
                defaultRl.setVisibility(View.VISIBLE);

                break;

            case Constants.RequestConfig.ORDER_ADD:

                if (object == null) return;

                OrderEntity orderEntity = (OrderEntity) object;

                ARouter.getInstance().build(RouterPath.ACTIVITY_PAY)
                        .withBoolean(Constants.BundleConfig.NORMAL, true)
                        .withSerializable(Constants.BundleConfig.ENTITY, orderEntity)
                        .navigation();

                break;
        }

    }

    public void btClick(View view) {

        if (view.getId() == R.id.address_layout) {

            ARouter.getInstance().build(RouterPath.ACTIVITY_ADDRESS)
                    .withInt(Constants.BundleConfig.EVENT_CODE, Constants.EventConfig.ADDRESS)
                    .navigation();

        }

        if (view.getId() == R.id.payment_rl) {

            List<OrderAddBuilder.GoodsListBean> list = new ArrayList<>();
            OrderAddBuilder.GoodsListBean listBean = null;
            for (OrderListEntity.ItemsBean itemsBean : listEntity.getItems()) {
                listBean = new OrderAddBuilder.GoodsListBean();
                listBean.setGoods_id(itemsBean.getGoods_id());
                listBean.setSku_id(itemsBean.getGoods_sku_id());
                listBean.setNumber(itemsBean.getNumber());

                list.add(listBean);
            }

            showLoadingDialog();
            presenter.orderAdd(Constants.RequestConfig.ORDER_ADD, contactEntity.getId(), listEntity.getFrom(),
                    listEntity.getFrom_ext(), list);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getWhat()) {

            case Constants.EventConfig.ADDRESS:

                ContactDefaultEntity entity = (ContactDefaultEntity) ((ObjectsEvent) event).getObjects()[0];

                nameTv.setText(entity.getContact_name());
                mobileTv.setText(entity.getContact_phone());
                addressTv.setText(entity.getAddress());
                defaultRl.setVisibility(entity.getId() == contactEntity.getId() ?
                        View.VISIBLE : View.GONE);

                contactEntity=entity;

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

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
