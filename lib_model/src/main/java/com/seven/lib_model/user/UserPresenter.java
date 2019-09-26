package com.seven.lib_model.user;

import com.google.gson.Gson;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.user.ConfirmOrderBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.user.UserEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by ouyang on 2019/3/31.
 */

public class UserPresenter extends BasePresenter<IBaseView,BaseFragment>{
    public UserPresenter(IBaseView view, BaseFragment activity) {
        super(view, activity);
    }

    public void getUserInfo(int code){
        HttpRxObserver rxObserver = get(getView(),code, UserEntity.class,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getUserInfo(),getActivity(), FragmentEvent.PAUSE).subscribe(rxObserver);
    }

    public void confirmOrder(int code,int order_id){
        HttpRxObserver rxObserver = get(getView(),code,null,null,false);
        ConfirmOrderBuilder.Builder builder = new ConfirmOrderBuilder.Builder();
        ConfirmOrderBuilder json = builder.order_id(order_id).build();
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().confirmOrder(new Gson().toJson(json)),getActivity(),FragmentEvent.PAUSE).subscribe(rxObserver);
    }

}
