package com.seven.lib_model.user;

import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.user.LoginEntity;
import com.seven.lib_model.model.user.TokenEntity;
import com.seven.lib_model.model.user.UserInfoDetailEntify;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Created by ouyang on 2019/3/31.
 */

public class UserActivityPresenter extends BasePresenter<IBaseView, BaseAppCompatActivity> {

    public UserActivityPresenter(IBaseView view, BaseAppCompatActivity activity) {
        super(view, activity);
    }

    public void getUserInfoDetail(int code, int id) {
        HttpRxObserver rxObserver = get(getView(), code, UserInfoDetailEntify.class, "data", false);
        if (rxObserver == null) return;
        // HttpRxObservable.getObservable(RequestHelper.getInstance().getUserInfo(""), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

//    public void login(int code,String url, LoginEntity json) {
//        HttpRxObserver rxObserver = get(getView(), code, TokenEntity.class, "data", false);
//        if (rxObserver == null) return;
//        HttpRxObservable.getObservable(RequestHelper.getInstance().login(url,json), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
//    }

}
