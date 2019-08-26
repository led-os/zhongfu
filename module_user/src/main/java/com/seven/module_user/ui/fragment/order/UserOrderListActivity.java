package com.seven.module_user.ui.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xxxxxxH on 2019/4/9.
 */
@Route(path = RouterPath.ACTIVITY_MINE_ORDER)
public class UserOrderListActivity extends BaseAppCompatActivity {
    @BindView(R2.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R2.id.viewPager)
    ViewPager viewPager;
    @BindView(R2.id.title_tv)
    TextView titleTv;
    @BindView(R2.id.left_btn)
    RelativeLayout backView;
    private final String[] mTitles = {
            "全部", "待付款", "待发货", "待收货","已完成"};
    private List<Fragment> mFragments = new ArrayList<>();
    //  private final String[] mType = {"all", "wait_pay", "wait_send", ""};
    private final int[] mType = {0, 1, 2, 3,4};

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
        statusBar = StatusBar.LIGHT;
        return R.layout.mu_activity_order_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        titleTv.setText("我的订单");
        for (int type : mType) {
            mFragments.add(OrderListFragment.getInstance(type));
        }
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCurrentItem(getIntent().getIntExtra("type", 0));
    }

    @OnClick(R2.id.left_btn)
    @Override
    public void onClick(View view) {
        if (view==backView) {
            finish();
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
