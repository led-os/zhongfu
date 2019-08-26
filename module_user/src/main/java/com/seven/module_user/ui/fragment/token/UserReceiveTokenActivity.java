package com.seven.module_user.ui.fragment.token;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.RewardItem;
import com.seven.lib_model.model.extension.RewardListEntity;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.view.BaseRecyclerView;

import butterknife.BindView;

/**
 * Created by xxxxxxH on 2019/4/10.
 * 领取令牌
 */

public class UserReceiveTokenActivity extends BaseTitleActivity {
    @BindView(R2.id.list_view)
    BaseRecyclerView recyclerView;

    private String[] zz = {"直推令牌奖励", "间推令牌奖励", "利润分成", "矿主名额", "场主名额", "报单产品", "购买产品令牌奖励", "新进会员令牌奖励"};

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_receive_token_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_receive_token);
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {

    }

    private void getData() {
        ApiManager.rewardList()
                .subscribe(new CommonObserver<BaseResult<RewardListEntity>>() {
                    @Override
                    public void onNext(BaseResult<RewardListEntity> rewardListEntityBaseResult) {
                        recyclerView.init(new LinearLayoutManager(mContext), new BaseQuickAdapter<RewardItem, BaseViewHolder>(R.layout.item_reward_layout, rewardListEntityBaseResult.getData().getItems()) {
                            @Override
                            protected void convert(BaseViewHolder helper, RewardItem item) {
                                helper.setText(R.id.text1,item.getReward_name())
                                        .setText(R.id.text2,zz[item.getReward_type()])
                                        .setText(R.id.number,"+"+item.getReward_number());
                            }
                        });
                    }
                });
    }
}
