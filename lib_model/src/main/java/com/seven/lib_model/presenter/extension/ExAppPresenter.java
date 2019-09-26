package com.seven.lib_model.presenter.extension;

import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.common.PageBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.extension.InComeDetailsEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class ExAppPresenter extends BasePresenter<IBaseView,BaseAppCompatActivity> {
    public ExAppPresenter(IBaseView view, BaseAppCompatActivity activity) {
        super(view, activity);
    }


    public void inComeDetails(int code,int page,int page_size){
        PageBuilder.Builder builder = new PageBuilder.Builder();
        PageBuilder json = builder.page(page).page_size(page_size).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = get(getView(),code,InComeDetailsEntity.class,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().inComeDetails(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }


}
