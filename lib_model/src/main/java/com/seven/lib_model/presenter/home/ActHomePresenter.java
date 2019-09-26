package com.seven.lib_model.presenter.home;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.home.CommodityListBuilder;
import com.seven.lib_model.builder.home.CartAddBuilder;
import com.seven.lib_model.builder.home.CommodityDetailsBuilder;
import com.seven.lib_model.builder.home.OrderAddBuilder;
import com.seven.lib_model.builder.home.OrderPayBuilder;
import com.seven.lib_model.builder.home.OrderPaymentBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.home.AliPayEntity;
import com.seven.lib_model.model.home.CartTotalEntity;
import com.seven.lib_model.model.home.CommodityDetailsEntity;
import com.seven.lib_model.model.home.CommodityEntity;
import com.seven.lib_model.model.home.ContactDefaultEntity;
import com.seven.lib_model.model.home.OrderEntity;
import com.seven.lib_model.model.home.OrderListEntity;
import com.seven.lib_model.model.home.WxPayEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/23
 */

public class ActHomePresenter extends BasePresenter<IBaseView, BaseActivity> {
    public ActHomePresenter(IBaseView view, BaseActivity activity) {
        super(view, activity);
    }

    public void commodityDetails(int requestCode, int goods_id) {

        CommodityDetailsBuilder.Builder builder = new CommodityDetailsBuilder.Builder();
        CommodityDetailsBuilder json = builder
                .goods_id(goods_id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, CommodityDetailsEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().commodityDetails(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void cartTotal(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, CartTotalEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().cartTotal(), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void cartAdd(int requestCode, int goods_id, int sku_id, int number) {

        CartAddBuilder.Builder builder = new CartAddBuilder.Builder();
        CartAddBuilder json = builder
                .goods_id(goods_id)
                .goods_sku_id(sku_id)
                .number(number)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().cartAdd(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void orderPayment(int requestCode, String from, String cart_ids, int goods_id, int sku_id, int number) {

        OrderPaymentBuilder.Builder builder = new OrderPaymentBuilder.Builder();
        OrderPaymentBuilder json = builder
                .from(from)
                .cart_ids(cart_ids)
                .goods_id(goods_id)
                .sku_id(sku_id)
                .number(number)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, OrderListEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().orderPayment(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void orderAdd(int requestCode, int contact_id, String from, String from_ext, List<OrderAddBuilder.GoodsListBean> list) {

        OrderAddBuilder.Builder builder = new OrderAddBuilder.Builder();
        OrderAddBuilder json = builder
                .contact_id(contact_id)
                .from(from)
                .from_ext(from_ext)
                .addList(list)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, OrderEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().orderAdd(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void contactDefault(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, ContactDefaultEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().contactDefault(), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void orderPay(int requestCode, String type, String order_sn, String subject) {

        OrderPayBuilder.Builder builder = new OrderPayBuilder.Builder();
        OrderPayBuilder json = builder
                .type(type)
                .order_sn(order_sn)
                .subject(subject)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = null;

        if (type.equals("wxpay"))
            rxObserver = get(getView(), requestCode, WxPayEntity.class, "pay_info", false);

        if (type.equals("alipay"))
            rxObserver = get(getView(), requestCode, AliPayEntity.class, null, false);

        if (type.equals("tokenPay"))
            rxObserver = get(getView(), requestCode);

        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().orderPay(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void collect(int requestCode, int goods_id) {

        CommodityDetailsBuilder.Builder builder = new CommodityDetailsBuilder.Builder();
        CommodityDetailsBuilder json = builder
                .goods_id(goods_id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().collect(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void commodityList(int requestCode,int page,int sort,String keyword,int category) {

        CommodityListBuilder.Builder builder = new CommodityListBuilder.Builder();
        CommodityListBuilder json = (CommodityListBuilder) builder
                .sort(sort)
                .keyword(keyword)
                .category(category)
                .page(page)
                .page_size(pageSize)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, CommodityEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().commodityList(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

}
