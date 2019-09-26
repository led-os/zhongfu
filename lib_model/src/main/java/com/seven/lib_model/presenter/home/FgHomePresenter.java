package com.seven.lib_model.presenter.home;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.common.PageBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.home.BannerEntity;
import com.seven.lib_model.model.home.BannerEntranceEntity;
import com.seven.lib_model.model.home.CommodityEntity;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/20
 */

public class FgHomePresenter extends BasePresenter<IBaseView,BaseFragment> {
    public FgHomePresenter(IBaseView view, BaseFragment activity) {
        super(view, activity);
    }

    public void banner(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, BannerEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().banner(), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

    public void entrance(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, BannerEntranceEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().entrance(), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

    public void commodityRecommendList(int requestCode,int page,int pageSize) {

        PageBuilder.Builder builder = new PageBuilder.Builder();
        PageBuilder json = builder
                .page(page)
                .page_size(pageSize)
                .build();
        String jsonStr = new Gson().toJson(json);
        Logger.i(jsonStr);

        HttpRxObserver rxObserver = get(getView(), requestCode, CommodityEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().commodityRecommendList(jsonStr), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

    public void incomeCommodityList(int requestCode) {

        HttpRxObserver rxObserver = get(getView(), requestCode, CommodityEntity.class, "items", true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().incomeCommodityList(), null, FragmentEvent.PAUSE).subscribe(rxObserver);
    }

}
