package com.seven.lib_model.presenter.app;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.app.PushBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.app.MessageReadEntity;
import com.seven.lib_model.model.app.UpdateEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/20
 */

public class ActAppPresenter extends BasePresenter<IBaseView, BaseAppCompatActivity> {
    public ActAppPresenter(IBaseView view, BaseAppCompatActivity activity) {
        super(view, activity);
    }

    public void pushId(int requestCode, int id) {

        PushBuilder.Builder builder = new PushBuilder.Builder();
        PushBuilder json = builder
                .registration_id(id)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().pushId(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void deletePushId(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().deletePushId(), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void messageNotRead(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, MessageReadEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().messageNotRead(), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void update(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, UpdateEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().update(), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void getUserInfo(int requestCode){
        HttpRxObserver rxObserver = get(getView(),requestCode, UserEntity.class,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getUserInfo(),getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }
}
