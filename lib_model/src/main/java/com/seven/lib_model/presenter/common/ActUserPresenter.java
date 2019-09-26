package com.seven.lib_model.presenter.common;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.common.SmsBuilder;
import com.seven.lib_model.builder.user.RegisterBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.user.RegisterEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/20
 */

public class ActUserPresenter extends BasePresenter<IBaseView, BaseActivity> {
    public ActUserPresenter(IBaseView view, BaseActivity activity) {
        super(view, activity);
    }

    public void sms(int requestCode, String phone, String scene) {

        SmsBuilder.Builder builder = new SmsBuilder.Builder();
        SmsBuilder json = builder
                .phone(phone)
                .scene(scene)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().sms(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void register(int requestCode, String phone, String code, String password, String inviteCode) {

        RegisterBuilder.Builder builder = new RegisterBuilder.Builder();
        RegisterBuilder json = builder
                .phone(phone)
                .code(code)
                .password(password)
                .password_confirmation(password)
                .invite_code(inviteCode)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, RegisterEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().register(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void login(int requestCode, String phone, String password) {

        RegisterBuilder.Builder builder = new RegisterBuilder.Builder();
        RegisterBuilder json = builder
                .phone(phone)
                .password(password)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, RegisterEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().login(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void password(int requestCode, String phone, String code, String password) {

        RegisterBuilder.Builder builder = new RegisterBuilder.Builder();
        RegisterBuilder json = builder
                .phone(phone)
                .code(code)
                .password(password)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

//        HttpRxObserver rxObserver = get(getView(), requestCode, RegisterEntity.class, null, false);
        HttpRxObserver rxObserver = get(getView(), requestCode, null, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().password(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }
}
