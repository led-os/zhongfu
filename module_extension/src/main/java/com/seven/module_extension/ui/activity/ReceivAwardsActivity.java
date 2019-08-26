package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.model.extension.RewardInfoLlistEntity;
import com.seven.lib_model.model.extension.RewardListEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_router.Variable;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.RewardInfoAdapter;
import com.seven.module_extension.ui.dialog.ReceiveDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterPath.ACTIVITY_REWARD_LIST)
public class ReceivAwardsActivity extends BaseTitleActivity {
    @Autowired(name = "id")
    int id = 0;
    @BindView(R2.id.me_lingqu_rv)
    RecyclerView meLingquRv;
    @BindView(R2.id.me_lingqu_refresh)
    SwipeRefreshLayout meLingquRefresh;

    private ExActivityPresenter presenter;

    private List<RewardInfoLlistEntity> rewardList;

    private RewardInfoAdapter adapter;
    private UserEntity userEntity;
    private ReceiveDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.me_acitvity_lingqu_lingpai;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.me_receivelingpai_title);
        presenter = new ExActivityPresenter(this, this);
        String userInfo = SharedData.getInstance().getUserInfo();
        userEntity = new Gson().fromJson(userInfo, UserEntity.class);
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        if (intent == null) intent = getIntent();
        id = intent.getIntExtra("id", -1);
        meLingquRefresh.setColorSchemeResources(
                R.color.primary,
                R.color.primary,
                R.color.primary,
                R.color.primary);
        meLingquRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request(id);
            }
        });
        setRv();
        request(id);
    }

    private void setRv() {
        adapter = new RewardInfoAdapter(R.layout.me_item_lingqu, rewardList);
        meLingquRv.setLayoutManager(new LinearLayoutManager(mContext));
        meLingquRv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<RewardInfoLlistEntity> list = adapter.getData();
                int id = (int) list.get(position).getId();
                if (!isLogin())return;
                presenter.getReward(2, String.valueOf(id));
            }
        });
    }

    private void request(int id) {
        presenter.rewardInfo(1, id);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) return;
            rewardList = new ArrayList<>();
            rewardList = (List<RewardInfoLlistEntity>) object;
            if (rewardList.size() > 0) {
                adapter.setNewData(rewardList);
            }
        }
        if (code == 2){
            ToastUtils.showToast(mContext,"领取成功");
            request(id);
        }
    }
    private void showDialog(){
        if (dialog == null){
            dialog = new ReceiveDialog(ReceivAwardsActivity.this, R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                }

                @Override
                public void onClick(View v, Object... objects) {
                    RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MINE_ORDER);
                }

                @Override
                public void dismiss() {

                }
            });
        }
        if (!dialog.isShowing())
            dialog.show();
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {
        if (meLingquRefresh.isRefreshing())
            meLingquRefresh.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {

    }

}
