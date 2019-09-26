package com.seven.lib_model.user;

import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.user.EditAddressBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.user.UserEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Created by xxxxxxH on 2019/5/19.
 */
public class UserActivityPresenterNew extends BasePresenter<IBaseView,BaseActivity> {
    public UserActivityPresenterNew(IBaseView view, BaseActivity activity) {
        super(view, activity);
    }

    public void editAddress(int code,int contact_id,String province_id,String city_id,String district_id,String address,String is_default,String contact_name,String contact_phone){
        EditAddressBuilder.Builder builder = new EditAddressBuilder.Builder();
        EditAddressBuilder json = builder
                .contact_id(contact_id)
                .province_id(province_id)
                .city_id(city_id)
                .district_id(district_id)
                .address(address)
                .is_default(is_default)
                .contact_name(contact_name)
                .contact_phone(contact_phone)
                .build();
        HttpRxObserver rxObserver = get(getView(),code,null,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().editAddress(new Gson().toJson(json)),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }


}
