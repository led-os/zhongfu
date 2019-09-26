package com.seven.lib_model.presenter.extension;

import com.google.gson.Gson;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.extension.RewardRuleBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.extension.RewardRuleEntity;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by xxxxxxH on 2019/4/24.
 */
public class ExFragmentPresenter extends BasePresenter<IBaseView,BaseFragment> {
    public ExFragmentPresenter(IBaseView view, BaseFragment activity) {
        super(view, activity);
    }

    public void rewardRule(int code,int role){
        RewardRuleBuilder.Builder builder = new RewardRuleBuilder.Builder();
        RewardRuleBuilder json = builder.role(role).build();
        String jsonstr = new Gson().toJson(json);
        HttpRxObserver  rxObserver = get(getView(),code,RewardRuleEntity.class,null,true);
        if (rxObserver == null)
            return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().rewardrule(jsonstr),getActivity(),FragmentEvent.PAUSE).subscribe(rxObserver);
    }
}
