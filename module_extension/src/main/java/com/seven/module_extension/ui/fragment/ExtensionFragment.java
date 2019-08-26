package com.seven.module_extension.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.ReceiveGoodsEntity;
import com.seven.lib_model.model.extension.RewardItem;
import com.seven.lib_model.model.extension.RewardListEntity;
import com.seven.lib_model.model.extension.RewardRuleEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.presenter.extension.ExFragmentPresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.RewardGetAdapter;
import com.seven.module_extension.ui.adapter.RewardRuleAdapter;
import com.seven.module_extension.ui.dialog.NotVipDialog;
import com.seven.module_extension.ui.dialog.SelectUserTypeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/26
 */

@Route(path = RouterPath.FRAGMENT_EXTENSION)
public class ExtensionFragment extends BaseFragment {
    @BindView(R2.id.me_userlevel)
    ImageView meUserlevel;
    @BindView(R2.id.me_profit_details)
    TypeFaceView meProfitDetails;
    @BindView(R2.id.me_profit_num)
    TypeFaceView meProfitNum;
    @BindView(R2.id.me_title)
    LinearLayout meTitle;
    @BindView(R2.id.me_tab)
    LinearLayout meTab;
    @BindView(R2.id.me_buy_receive)
    TypeFaceView meBuyReceive;
    @BindView(R2.id.me_buy_arrow)
    ImageView meBuyArrow;
    @BindView(R2.id.me_buy_bd)
    RelativeLayout meBuyBd;
    @BindView(R2.id.me_interviewpeo)
    TypeFaceView meInterviewpeo;
    @BindView(R2.id.me_inyerview_arrow)
    ImageView meInyerviewArrow;
    @BindView(R2.id.me_buy_interview)
    RelativeLayout meBuyInterview;
    @BindView(R2.id.me_rv_everyreward)
    RecyclerView meRvEveryreward;
    @BindView(R2.id.me_rv_rewardrules)
    RecyclerView meRvRewardrules;
    @BindView(R2.id.me_content)
    LinearLayout meContent;
    @BindView(R2.id.me_title_right)
    ImageView meTitleRight;
    @BindView(R2.id.me_buy_up_rl)
    RelativeLayout me_buy_up_rl;
    @BindView(R2.id.me_ext_up_rl)
    RelativeLayout me_ext_up_rl;
    @BindView(R2.id.me_rv_slice)
    TextView me_rv_slice;
    @BindView(R2.id.me_reward_tv)
    TypeFaceView me_reward_tv;


    private ExFragmentPresenter presenter;
    private List<RewardRuleEntity> list;
    private SelectUserTypeDialog dialog;
    private int type = 0;
    private RewardRuleAdapter adapter;
    private NotVipDialog notVipDialog;
    private RewardGetAdapter getAdapter;
    UserEntity user;

    @Override
    public int getContentViewId() {

        return R.layout.me_fragment_extension;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new ExFragmentPresenter(this, this);
        UserEntity userEntity = new Gson().fromJson(SharedData.getInstance().getUserInfo(), UserEntity.class);
        getData(0);
        if (userEntity == null) {
            me_reward_tv.setVisibility(View.GONE);
        } else {
            setUserData();
            getRewardList();
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        meProfitDetails.setOnClickListener(this);
        me_buy_up_rl.setOnClickListener(this);
        me_ext_up_rl.setOnClickListener(this);
        me_rv_slice.setOnClickListener(this);
        meBuyBd.setOnClickListener(this);
        meBuyInterview.setOnClickListener(this);
        meTitleRight.setOnClickListener(this);
    }

    private void setUserData() {
        String userInfo = SharedData.getInstance().getUserInfo();
        if (userInfo != null && !userInfo.equals("null")) {
            user = new Gson().fromJson(userInfo, UserEntity.class);
            if (user == null) {
                meUserlevel.setBackgroundResource(R.drawable.me_normaluser);
                meInterviewpeo.setText("已成功邀请" + "0人");
                meProfitNum.setText("0");
                return;
            } else {
                meInterviewpeo.setText("已成功邀请" + user.getInvite_number() + "人");
                meBuyReceive.setText("能领取" + user.getForm_goods_number() + "次令牌奖励");
                meProfitNum.setText(!TextUtils.isEmpty(user.getPromotion_token_number()) ? user.getPromotion_token_number() : "0");
                switch (user.getRole()) {
                    case 0:
                        meUserlevel.setBackgroundResource(R.drawable.me_normaluser);
                        showNotVipDialog();
                        break;
                    case 1:
                        meUserlevel.setBackgroundResource(R.drawable.me_vip);
                        break;
                    case 2:
                        meUserlevel.setBackgroundResource(R.drawable.me_kuangzhu);
                        break;
                    case 3:
                        meUserlevel.setBackgroundResource(R.drawable.me_changzhu);
                        break;
                    case 4:
                        meUserlevel.setBackgroundResource(R.drawable.ctylord);
                        break;
                    default:
                }
            }
        }
    }

    private void getRewardList() {
        ApiManager.rewardList().subscribe(new CommonObserver<BaseResult<RewardListEntity>>() {
            @Override
            public void onNext(BaseResult<RewardListEntity> rewardListEntityBaseResult) {

                if (rewardListEntityBaseResult.getData().getItems().size() != 0) {
                    meRvEveryreward.setVisibility(View.VISIBLE);
                    me_reward_tv.setVisibility(View.VISIBLE);
                    getAdapter = new RewardGetAdapter(R.layout.me_item_reward_get, rewardListEntityBaseResult.getData().getItems());
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    meRvEveryreward.setLayoutManager(manager);
                    meRvEveryreward.setAdapter(getAdapter);
                    getAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            List<RewardItem> list = adapter.getData();
                            int id = list.get(position).getId();
                            if (list.get(position).getReward_type() == 6) {
                                ARouter.getInstance().build(RouterPath.ACTIVITY_BD_LIST)
                                        .withInt("id", id)
                                        .navigation();
                            } else if (list.get(position).getReward_type() == 1 ||
                                    list.get(position).getReward_type() == 2 ||
                                    list.get(position).getReward_type() == 7 ||
                                    list.get(position).getReward_type() == 8) {
                                ARouter.getInstance().build(RouterPath.ACTIVITY_REWARD_LIST)
                                        .withInt("id", id)
                                        .navigation();
                            } else if (list.get(position).getReward_type() == 4 ||
                                    list.get(position).getReward_type() == 5) {
                                ARouter.getInstance().build(RouterPath.ACTIVITY_QUOTA)
                                        .withInt("id", id)
                                        .withInt("type", list.get(position).getReward_type())
                                        .navigation();
                            }
                        }
                    });
                } else {
                    meRvEveryreward.setVisibility(View.GONE);
                    me_reward_tv.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showNotVipDialog() {
        if (notVipDialog == null) {
            notVipDialog = new NotVipDialog(getActivity(), R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                }

                @Override
                public void onClick(View v, Object... objects) {
                    if (objects[0] != null) {
                        RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_BUY_BD);
                    }
                }

                @Override
                public void dismiss() {

                }
            });
            if (!notVipDialog.isShowing())
                notVipDialog.show();
        }
    }

    private void getData(int role) {
        presenter.rewardRule(1, role);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) return;
            list = new ArrayList<>();
            list = (List<RewardRuleEntity>) object;
        }
        setRv(list);
    }

    private void setRv(List<RewardRuleEntity> data) {
        adapter = new RewardRuleAdapter(R.layout.me_item_newrewardrole, data);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        meRvRewardrules.setLayoutManager(manager);
        meRvRewardrules.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.me_profit_details) {
            if (user == null)
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
            else
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_TOKEN);
        } else if (v.getId() == R.id.me_buy_up_rl) {
            if (user != null && user.getRole() == 4) {
                ToastUtils.showToast(getActivity(), "你已经是最高等级");
            } else {
                if (user == null)
                    RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
                else
                    RouterUtils.getInstance().routerWithString(RouterPath.ACTIVITY_BUY_ROLE, "level", user.getRole() + "");
            }
        } else if (v.getId() == R.id.me_rv_slice) {
            showDialog();
        } else if (v.getId() == R.id.me_buy_bd) {
            if (user == null)
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
            else
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_BUY_BD);
        } else if (v.getId() == R.id.me_buy_interview) {
            if (user == null)
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
            else
                RouterUtils.getInstance().routerWithString(RouterPath.ACTIVITY_MY_INTERVIEW,"id",String.valueOf(user.getId()));

        } else if (v.getId() == R.id.me_ext_up_rl) {
            if (user == null)
                RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
            else
                RouterUtils.getInstance().routerWithString(RouterPath.ACTIVITY_MY_INTERVIEW,"id",String.valueOf(user.getId()));
        } else if (v.getId() == R.id.me_title_right) {
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LEVEL);
        }

    }

    private void showDialog() {
        dialog = new SelectUserTypeDialog(getActivity(), R.style.Dialog, new OnClickListener() {
            @Override
            public void onCancel(View v, Object... objects) {

            }

            @Override
            public void onClick(View v, Object... objects) {
                String userType = (String) objects[0];
                me_rv_slice.setText("筛选：" + userType);
                if (userType.equals("普通用户")) {
                    type = 0;
                } else if (userType.equals("VIP")) {
                    type = 1;
                } else if (userType.equals("矿主")) {
                    type = 2;
                } else if (userType.equals("场主")) {
                    type = 3;
                } else if (userType.equals("城主")) {
                    type = 4;
                }
                getData(type);
            }

            @Override
            public void dismiss() {

            }
        });
        if (!dialog.isShowing())
            dialog.showDialog(0, -(ScreenUtils.getScreenHeight(getActivity())));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getWhat()) {
            case Constants.EventConfig.USER_DATA_CHANGE:
                getData(1);
                getRewardList();
                setUserData();
                break;
            case Constants.EventConfig.LOGOUT:
                SharedData.getInstance().setUserInfo("");
                SharedData.getInstance().setToken("");
                Variable.getInstance().setUserId(0);
                Variable.getInstance().setTokenCount(0);
                Variable.getInstance().setToken("");
                setUserData();
                break;
            case Constants.EventConfig.LOGIN:
            case Constants.EventConfig.REGISTER:
                getUserInfo();
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getUserInfo() {
        // presenter.getUserInfo(1);
        ApiManager.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserEntity> userEntityBaseResult) {
                        Gson gson = new Gson();
                        String userString = gson.toJson(userEntityBaseResult.getData());
                        if (userString != null && !userString.equals("null")) {
                            SharedData.getInstance().setUserInfo(userString);
                            Variable.getInstance().setUserId(userEntityBaseResult.getData().getId());
                            Variable.getInstance().setTokenCount(TextUtils.isEmpty(userEntityBaseResult.getData().getToken_number_total()) ? 0 : Double.parseDouble(userEntityBaseResult.getData().getToken_number_total()));
                            EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.USER_DATA_CHANGE, "change"));
                        } else {
                            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("xxxxxxH", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
