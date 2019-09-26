package com.seven.lib_model.presenter.extension;

import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.mvp.presenter.BasePresenter;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_http.observer.HttpRxObservable;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_model.builder.extension.BindingBuilder;
import com.seven.lib_model.builder.extension.BuyBuilder;
import com.seven.lib_model.builder.extension.BuyRoleBuilder;
import com.seven.lib_model.builder.extension.InviteBuilder;
import com.seven.lib_model.builder.extension.LevelBuilder;
import com.seven.lib_model.builder.extension.ReceiveBuilder;
import com.seven.lib_model.builder.extension.RewardBilder;
import com.seven.lib_model.builder.extension.RewardListBuilder;
import com.seven.lib_model.http.RequestHelper;
import com.seven.lib_model.model.extension.BdGoodsEntity;
import com.seven.lib_model.model.extension.BindItemEntity;
import com.seven.lib_model.model.extension.GoodsItemEntity;
import com.seven.lib_model.model.extension.LevelEntity;
import com.seven.lib_model.model.extension.MyInterViewEntity;
import com.seven.lib_model.model.extension.QuotaItem;
import com.seven.lib_model.model.extension.RewardInfoLlistEntity;
import com.seven.lib_model.model.home.AliPayEntity;
import com.seven.lib_model.model.home.WxPayEntity;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Created by xxxxxxH on 2019/5/3.
 */
public class ExActivityPresenter extends BasePresenter<IBaseView, BaseActivity> {
    public ExActivityPresenter(IBaseView view, BaseActivity activity) {
        super(view, activity);
    }

    public void invite(int code, int user_id, int page, int page_size) {
        InviteBuilder.Builder builder = new InviteBuilder.Builder();
        InviteBuilder json = builder
                .user_id(user_id)
                .page(page)
                .page_size(page_size)
                .build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = get(getView(), code, MyInterViewEntity.class, null, false);
        if (rxObserver == null) return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().inviteList(jsonStr), getActivity(), ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    public void level(int code,int id){
        LevelBuilder.Builder builder = new LevelBuilder.Builder();
        LevelBuilder json = builder.id(id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = get(getView(),code,LevelEntity.class,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().level(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //绑定名额
    public void  bind(int code,String phone,String id){
        BindingBuilder.Builder builder = new BindingBuilder.Builder();
        BindingBuilder json = builder.phone(phone).id(id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = get(getView(),code,null,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().binding(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }
    //获取绑定列表
    public  void getBindList(int code,String id){
        BindingBuilder.Builder builder = new BindingBuilder.Builder();
        BindingBuilder json = builder.id(id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = getList(getView(),code,BindItemEntity.class,"items",true);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getBindList(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //领取报单奖励
    public void getReceive(int code,String ids,String contact_id){
        ReceiveBuilder.Builder builder = new ReceiveBuilder.Builder();
        ReceiveBuilder json = builder.ids(ids).contact_id(contact_id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = get(getView(),code,null,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getReceive(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //报表奖励列表
    public void rewardList(int code,String id){
        BindingBuilder.Builder builder = new BindingBuilder.Builder();
        BindingBuilder json = builder.id(id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = getList(getView(),code,BdGoodsEntity.class,"items",true);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().rewardLsit(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //获取令牌详情
    public void rewardInfo(int code,int id){
        RewardListBuilder.Builder builder = new RewardListBuilder.Builder();
        RewardListBuilder json = builder.reward_id(id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = getList(getView(),code,RewardInfoLlistEntity.class,null,true);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().rewardInfo(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //领取奖励
    public void getReward(int code,String id){
        RewardBilder.Builder builder = new RewardBilder.Builder();
        RewardBilder json = builder.reward_info_id(id).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = get(getView(),code,null,null,false);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getReward(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //获取名额
    public void  getQuota(int code, String id){
        LevelBuilder.Builder builder = new LevelBuilder.Builder();
        LevelBuilder json = builder.id(Integer.parseInt(id)).build();
        String jsonStr = new Gson().toJson(json);
        HttpRxObserver rxObserver = getList(getView(),code,QuotaItem.class,"items",true);
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getQuota(jsonStr),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);

    }

    //生成报单订单
    public void getOrder(int code,int contact_id,int type){
        BuyBuilder.Builder builder = new BuyBuilder.Builder();
        BuyBuilder json = builder.contact_id(contact_id).type(type).build();
        HttpRxObserver rxObserver = get(getView(),code, com.seven.lib_model.model.home.OrderEntity.class,null,false);
        if (rxObserver ==null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().getOrder(new Gson().toJson(json)),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }

    //生成角色订单
    public void buyRole(int code,int role,String type){
        BuyRoleBuilder.Builder builder = new BuyRoleBuilder.Builder();
        BuyRoleBuilder json = builder.role(role).type(type).build();
        HttpRxObserver rxObserver = null;
        if (type.equals("alipay")){
            rxObserver = get(getView(),code, AliPayEntity.class,null,false);
        }else {
            rxObserver =  get(getView(),code, WxPayEntity.class,"pay_info",false);
        }
        if (rxObserver == null)return;
        HttpRxObservable.getObservable(RequestHelper.getInstance().buyRole(new Gson().toJson(json)),getActivity(),ActivityEvent.PAUSE).subscribe(rxObserver);
    }
}
