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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.InComeDetailsEntity;
import com.seven.lib_model.model.extension.InComeItem;
import com.seven.lib_model.model.user.OrderEntity;
import com.seven.lib_model.model.user.TokenPageEntity;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.order.OrderListFragment;
import com.seven.module_user.ui.fragment.order.UserOrderDetailActivity;
import com.seven.module_user.ui.fragment.view.BaseRecyclerView;
import com.seven.module_user.ui.fragment.view.DividerSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xxxxxxH on 2019/4/10.
 */

public class TokenListFragment extends BaseFragment {

    @BindView(R2.id.list_view)
    BaseRecyclerView recyclerView;

    private String currentListType;
    private int page;

    public static TokenListFragment getInstance(String type) {
        TokenListFragment fragment = new TokenListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
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


    @Override
    public void onClick(View view) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.mu_common_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        currentListType = getArguments().getString("type");
       getData();
    }

    private void getData() {
        TokenPageEntity entity = new TokenPageEntity();
        entity.setPage(page);
        entity.setPage_size(20);
        entity.setStatus(currentListType);
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
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.init(layoutManager, new BaseQuickAdapter<InComeItem, BaseViewHolder>(R.layout.item_token_list_layout, null) {
                @Override
                protected void convert(BaseViewHolder helper, InComeItem item) {
                    helper.setText(R.id.name,item.getTitle())
                            .setText(R.id.comment,item.getComment())
                            .setText(R.id.number,item.getNumber())
                            .setText(R.id.time,item.getCreated_at());
                }
            }, true).changeItemDecoration(new DividerSpaceItemDecoration(6))
                    .setEmptyView(getEmptyView())
                    .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            page = 1;
                            getData();
                        }
                    })
                    .setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page++;
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
        //todo 你妈教你total的值是这么返回的吗？
        //todo 。。
    }

    private View getEmptyView() {
        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("暂无记录");
        return textView;
    }
}
