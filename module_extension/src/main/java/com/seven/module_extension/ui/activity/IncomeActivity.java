package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.InComeDetailsEntity;
import com.seven.lib_model.model.extension.InComeItem;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_model.presenter.extension.ExAppPresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.MessageEvent;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.PagerAdapter;
import com.seven.module_extension.ui.fragment.InComeAllFragment;
import com.seven.module_extension.ui.fragment.InComeFrozenFragment;
import com.seven.module_extension.ui.fragment.InComeInFragment;
import com.seven.module_extension.ui.fragment.InComeOutFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xxxxxxH on 2019/4/20.
 */
@Route(path = RouterPath.ACTIVITY_IN_COME)
public class IncomeActivity extends BaseAppCompatActivity {
    @BindView(R2.id.me_tab)
    SlidingTabLayout meTab;
    @BindView(R2.id.me_viewpager)
    ViewPager meViewpager;
    @BindView(R2.id.me_income_back)
    ImageView me_income_back;
    @BindView(R2.id.me_refresh)
    SwipeRefreshLayout me_refresh;

    private InComeAllFragment allFragment;
    private InComeInFragment inFragment;
    private InComeOutFragment outFragment;
    private InComeFrozenFragment frozenFragment;
    private List<Fragment> fragmentList;
    private PagerAdapter pagerAdapter;

    private InComeDetailsEntity inComeList;
    private List<InComeItem> allList;
    private List<InComeItem> inList;
    private List<InComeItem> outList;
    private List<InComeItem> frozenList;
    private ExAppPresenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.me_activity_income;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        statusBar = StatusBar.LIGHT;
        me_income_back.setOnClickListener(this);
        fragmentList= new ArrayList<>();
        allFragment = new InComeAllFragment();
        inFragment = new InComeInFragment();
        outFragment = new InComeOutFragment();
        frozenFragment = new InComeFrozenFragment();
        fragmentList.add(allFragment);
        fragmentList.add(inFragment);
        fragmentList.add(outFragment);
        fragmentList.add(frozenFragment);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList);
        meViewpager.setAdapter(pagerAdapter);
        meViewpager.setOffscreenPageLimit(4);
        meTab.setViewPager(meViewpager,new String[]{"全部","收入","支出","冻结"});
        meTab.setCurrentTab(0);
        presenter = new ExAppPresenter(this,this);

        me_refresh.setColorSchemeResources(
                R.color.primary,
                R.color.primary,
                R.color.primary,
                R.color.primary);
        me_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
    }

    public void request(){
        presenter.inComeDetails(1,1,20);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1){
            if (object == null)return;
            inComeList = (InComeDetailsEntity) object;
            allList= new ArrayList<>();
            inList = new ArrayList<>();
            outList = new ArrayList<>();
            allList.addAll(inComeList.getItems());
            for (InComeItem entity:allList){
                if (entity.getNumber().contains("+")){
                    inList.add(entity);
                } else if (entity.getNumber().contains("-")){
                    outList.add(entity);
                }
            }
            allFragment.setRv(allList);
            inFragment.setRv(inList);
            outFragment.setRv(outList);
        }
    }

    @Override
    protected void initBundleData(Intent intent) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {
        if (me_refresh.isRefreshing())
            me_refresh.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.me_income_back){
            finish();
        }
    }

    public View setEmptyView(){
        View view = LayoutInflater.from(IncomeActivity.this).inflate(R.layout.me_emptyview,null);
        return view;
    }

}
