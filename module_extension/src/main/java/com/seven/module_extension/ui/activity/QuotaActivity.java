package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.QuotaEntity;
import com.seven.lib_model.model.extension.QuotaItem;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.QuotaAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxxxxxH on 2019/5/14.
 */
@Route(path = RouterPath.ACTIVITY_QUOTA)
public class QuotaActivity extends BaseTitleActivity {
    @Autowired(name = "id")
    int id = 0;
    @Autowired(name = "type")
    int type = 0;
    @BindView(R2.id.me_quota_rv)
    RecyclerView meQuotaRv;
    @BindView(R2.id.me_quota_refresh)
    SwipeRefreshLayout meQuotaRefresh;
    List<QuotaItem> itemList;
    ExActivityPresenter presenter;
    QuotaAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_quota;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
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
        type = intent.getIntExtra("type", -1);
        if (type == 4) {
            setTitleText("矿主名额");
        } else {
            setTitleText("场主名额");
        }
        meQuotaRefresh.setColorSchemeResources(
                R.color.primary,
                R.color.primary,
                R.color.primary,
                R.color.primary);
        meQuotaRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request(String.valueOf(id));
            }
        });
        presenter = new ExActivityPresenter(this, this);
        setRv();
        request(String.valueOf(id));
    }

    private void request(String id) {
        presenter.getQuota(1, id);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) return;
            itemList = new ArrayList<>();
            itemList = (List<QuotaItem>) object;
            adapter.setNewData(itemList);
        }
    }

    private void setRv() {
        adapter = new QuotaAdapter(R.layout.me_item_master, itemList);
        meQuotaRv.setLayoutManager(new LinearLayoutManager(this));
        meQuotaRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<QuotaItem> list = adapter.getData();
                String id = String.valueOf(list.get(position).getId());
                if (list.get(position).getPhone() != null && TextUtils.isEmpty(list.get(position).getPhone())) {
                    RouterUtils.getInstance().routerWithString(RouterPath.ACTIVITY_BIND, "id", id);
                }
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {
        if (meQuotaRefresh.isRefreshing())
            meQuotaRefresh.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
