package com.seven.lib_model.presenter.model;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.common.PageBuilder;
import com.seven.lib_model.builder.common.PayPasswordBuilder;
import com.seven.lib_model.builder.model.BusinessBuilder;
import com.seven.lib_model.builder.model.BusinessDetailsBuilder;
import com.seven.lib_model.builder.model.BusinessOrderListBuilder;
import com.seven.lib_model.builder.model.BusinessProofBuilder;
import com.seven.lib_model.builder.model.MessageBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.model.BusinessEntity;
import com.seven.lib_model.model.model.BusinessInfoEntity;
import com.seven.lib_model.model.model.MessageEntity;
import com.seven.lib_model.model.model.UploadEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class ActModelPresenter extends BasePresenter<IBaseView, BaseActivity> {
    public ActModelPresenter(IBaseView view, BaseActivity activity) {
        super(view, activity);
    }

    public void payPassword(int requestCode, String password) {

        PayPasswordBuilder.Builder builder = new PayPasswordBuilder.Builder();
        PayPasswordBuilder json = builder
                .pay_password(password)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().payPassword(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void business(int requestCode, int type, double token, double price, String ali, String wx) {

        BusinessBuilder.Builder builder = new BusinessBuilder.Builder();
        BusinessBuilder json = builder
                .type(type)
                .token_number(token)
                .price(price)
                .ali_account(ali)
                .wx_account(wx)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().business(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessOrderList(int requestCode, int type, int status, int page, int pageSize) {

        BusinessOrderListBuilder.Builder builder = new BusinessOrderListBuilder.Builder();
        BusinessOrderListBuilder json = (BusinessOrderListBuilder) builder
                .type(type)
                .status(status)
                .page(page)
                .page_size(pageSize)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, BusinessEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessOrderList(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessInfo(int requestCode, int id) {

        BusinessDetailsBuilder.Builder builder = new BusinessDetailsBuilder.Builder();
        BusinessDetailsBuilder json = builder
                .business_id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, BusinessInfoEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessInfo(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessCancel(int requestCode, int id) {

        BusinessDetailsBuilder.Builder builder = new BusinessDetailsBuilder.Builder();
        BusinessDetailsBuilder json = builder
                .business_id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessCancel(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessOrderInfo(int requestCode, int id) {

        BusinessDetailsBuilder.Builder builder = new BusinessDetailsBuilder.Builder();
        BusinessDetailsBuilder json = builder
                .business_id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, BusinessInfoEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessOrderInfo(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessAccept(int requestCode, int id) {

        BusinessDetailsBuilder.Builder builder = new BusinessDetailsBuilder.Builder();
        BusinessDetailsBuilder json = builder
                .business_id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessAccept(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessConfirm(int requestCode, int id) {

        BusinessDetailsBuilder.Builder builder = new BusinessDetailsBuilder.Builder();
        BusinessDetailsBuilder json = builder
                .business_id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessConfirm(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessProof(int requestCode, int id,String url) {

        BusinessProofBuilder.Builder builder = new BusinessProofBuilder.Builder();
        BusinessProofBuilder json = builder
                .business_id(id)
                .proof_picture(url)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessProof(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void messageRead(int requestCode, int id) {

        MessageBuilder.Builder builder = new MessageBuilder.Builder();
        MessageBuilder json = builder
                .id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().messageRead(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void upload(int requestCode, String path, String scene) {

        HttpRxObserver rxObserver = get(getView(), requestCode, UploadEntity.class,null,false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().upload(path, scene), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void messageList(int requestCode,int page) {

        PageBuilder.Builder builder = new PageBuilder.Builder();
        PageBuilder json = builder
                .page(page)
                .page_size(pageSize)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, MessageEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().messageList(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

}
