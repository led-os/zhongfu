package com.seven.module_user.ui.fragment.token;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.InComeDetailsEntity;
import com.seven.lib_model.model.extension.InComeItem;
import com.seven.lib_model.model.user.TokenPageEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.view.BaseRecyclerView;
import com.seven.module_user.ui.fragment.view.DividerSpaceItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xxxxxxH on 2019/4/10.
 * 我的令牌
 */

public class UserTokenActivity extends BaseAppCompatActivity {
    @BindView(R2.id.list_view)
    BaseRecyclerView recyclerView;
    @BindView(R2.id.title)
    TextView textView;
    @BindView(R2.id.token_number)
    TextView tokenNumber;
    @BindView(R2.id.token_total)
    TextView tokenTotal;
    @BindView(R2.id.freeze_token_number)
    TextView freezeTokenNumber;

    private int page = 1;

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
    protected int getContentViewId() {
        return R.layout.mu_activity_token;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        textView.setText("我的令牌");
    }

    @Override
    protected void initBundleData(Intent intent) {
        UserEntity userEntity = new Gson().fromJson(SharedData.getInstance().getUserInfo(), UserEntity.class);
        tokenNumber.setText(String.valueOf(userEntity.getToken_number()));
        freezeTokenNumber.setText(String.valueOf(userEntity.getFreeze_token_number()));
        tokenTotal.setText(String.valueOf(userEntity.getToken_number_total()));
        getData();

    }

    @OnClick(R2.id.all)
    void checkAll() {
      startActivity(new Intent(mContext, UserTokenListActivity.class));
       // RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_IN_COME);
    }

    @OnClick(R2.id.receive_token_btn)
    void receiveToken() {
        startActivity(new Intent(mContext, UserReceiveTokenActivity.class));
    }

    @OnClick(R2.id.token_desc_btn)
    void tokenDesc() {
        startActivity(new Intent(this, TokenDescActivity.class));
    }

    private View getEmptyView() {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("暂无记录");
        return textView;
    }

    @OnClick({R2.id.imgBack})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            finish();
        }
    }

    private void getData() {
        TokenPageEntity entity = new TokenPageEntity();
        entity.setStatus("");
        entity.setPage_size(200);
        entity.setPage(1);
        ApiManager.inComeDetails(entity)
                .subscribe(new CommonObserver<BaseResult<InComeDetailsEntity>>() {
                    @Override
                    public void onNext(BaseResult<InComeDetailsEntity> inComeDetailsEntityBaseResult) {
                        setData(inComeDetailsEntityBaseResult.getData());
                    }
                });
    }

    private void setData(InComeDetailsEntity data) {
        if (recyclerView.getAdapter() == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.init(layoutManager, new BaseQuickAdapter<InComeItem, BaseViewHolder>(R.layout.item_token_list_layout, null) {
                @Override
                protected void convert(BaseViewHolder helper, InComeItem item) {
                    helper.setText(R.id.name, item.getTitle())
                            .setText(R.id.comment, item.getComment())
                            .setText(R.id.number, item.getNumber())
                            .setText(R.id.time, item.getCreated_at());
                }
            }, true).changeItemDecoration(new DividerSpaceItemDecoration(6))
                    .setEmptyView(getEmptyView())
                    .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            page = 1;
                            getData();
                        }
                    });
        }
        recyclerView.setRefreshing(false);
        if (data.getPagination().getPage() == 1) {
            recyclerView.setNewData(data.getItems());
        } else {
            recyclerView.addDataList(data.getItems());
        }
        if (data.getPagination().getTotal_page() == 0) {
            recyclerView.setEnableLoadMore(false);
        }
    }

}
