package com.seven.module_user.ui.fragment.token;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
 * Created by xxxxxxH on 2019/4/10.
 * 令牌明细
 */
@Route(path = RouterPath.ACTIVITY_TOKEN)
public class UserTokenListActivity extends BaseAppCompatActivity {

    @BindView(R2.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R2.id.viewPager)
    ViewPager viewPager;
    @BindView(R2.id.title_tv)
    TextView titleTv;
    @BindView(R2.id.left_btn)
    RelativeLayout backView;
    private final String[] mTitles = {
            "全部", "收入", "支出", "冻结"};
    private List<Fragment> mFragments = new ArrayList<>();
    private final String[] mType = {"", "1", "2", "3"};

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
        return R.layout.mu_activity_token_list_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        titleTv.setText("令牌明细");
        for (String type : mType) {
            mFragments.add(TokenListFragment.getInstance(type));
        }
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R2.id.left_btn)
    public void onClose(View view) {
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
