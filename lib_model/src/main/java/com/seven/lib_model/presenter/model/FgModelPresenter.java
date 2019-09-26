package com.seven.lib_model.presenter.model;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.common.PageBuilder;
import com.seven.lib_model.builder.model.BusinessListBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.model.BusinessEntity;
import com.seven.lib_model.model.model.BusinessTotalEntity;
import com.seven.lib_model.model.model.LooperMessageEntity;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/5
 */

public class FgModelPresenter extends BasePresenter<IBaseView, BaseFragment> {

    public FgModelPresenter(IBaseView view, BaseFragment activity) {
        super(view, activity);
    }

    public void looperMessage(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, LooperMessageEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().looperMessage(), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessTotal(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, BusinessTotalEntity.class, null, false);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessTotal(), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

    public void businessList(int requestCode, int type, int sort, int page, int pageSize) {

        BusinessListBuilder.Builder builder = new BusinessListBuilder.Builder();
        BusinessListBuilder json = (BusinessListBuilder) builder
                .sort(sort)
                .type(type)
                .page(page)
                .page_size(pageSize)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, BusinessEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().businessList(jsonStr), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

}
