package com.seven.module_home.ui.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seven.greendao.gen.SearchHistoryDao;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.listener.IKeyBoardVisibleListener;
import com.seven.lib_common.stextview.TypeFaceEdit;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.AnimationUtils;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.widget.flowlayout.FlowLayout;
import com.seven.lib_common.widget.flowlayout.TagAdapter;
import com.seven.lib_common.widget.flowlayout.TagFlowLayout;
import com.seven.lib_model.model.home.CommodityEntity;
import com.seven.lib_model.presenter.home.ActHomePresenter;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_router.Constants;
import com.seven.lib_router.RouterSDK;
import com.seven.lib_router.db.dao.SearchHistory;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_home.R;
import com.seven.module_home.R2;
import com.seven.module_home.adapter.HomeAdapter;
import com.seven.module_home.widget.decoration.CommodityDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/14
 */

@Route(path = RouterPath.ACTIVITY_COMMODITY)
public class CommodityListActivity extends BaseActivity implements IKeyBoardVisibleListener,
        BaseQuickAdapter.OnItemClickListener {

    @Autowired(name = Constants.BundleConfig.FLOW)
    public int flow;
    @Autowired(name = Constants.BundleConfig.ID)
    public int id;
    private boolean pauseAnim;
    private boolean isBack;
    private boolean isSearch;

    @BindView(R2.id.left_btn)
    public RelativeLayout leftBtn;
    @BindView(R2.id.right_text_btn)
    public RelativeLayout rightBtn;

    @BindView(R2.id.search_layout)
    public LinearLayout searchLayout;
    @BindView(R2.id.search_et)
    public TypeFaceEdit searchEt;

    private RelativeLayout.LayoutParams layoutParams;
    private InputMethodManager imm;
    private Handler handler;
    private AnimRunnable thread;

    @BindView(R2.id.global_btn)
    public LinearLayout globalBtn;
    @BindView(R2.id.sales_volume_btn)
    public LinearLayout salesVolumeBtn;
    @BindView(R2.id.price_btn)
    public LinearLayout priceBtn;
    @BindView(R2.id.sales_volume_iv)
    public ImageView salesVolumeIv;
    @BindView(R2.id.price_iv)
    public ImageView priceIv;
    private boolean isDown;

    private int page = 1;
    private int pageSize = 10;
    public boolean isRefresh;
    private boolean isMoreEnd;

    @BindView(R2.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.recycler_view)
    public RecyclerView recyclerView;
    public HomeAdapter adapter;
    private List<CommodityEntity> commodityList;

    @BindView(R2.id.history_layout)
    public RelativeLayout historyLayout;
    @BindView(R2.id.tag_flow)
    public TagFlowLayout flowLayout;
    private TagAdapter tagAdapter;

    private ActHomePresenter presenter;

    private int sort;
    private String keyword;

    private SearchHistoryDao historyDao;
    private List<SearchHistory> allList;
    private List<SearchHistory> historyList;

    @Override
    protected int getContentViewId() {
        return R.layout.mh_activity_commodity;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initBundleData(Intent intent) {

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        handler = new Handler();
        thread = new AnimRunnable();

        historyDao = RouterSDK.getInstance().getDaoSession().getSearchHistoryDao();
        allList = historyDao.loadAll();
        historyList = new ArrayList<>();

        setRecyclerView();

        resetHistory();
        setFlowLayout();

        if (flow == Constants.BundleConfig.FLOW_SEARCH) {
            leftBtn.setVisibility(View.GONE);
            rightBtn.setVisibility(View.VISIBLE);
            layoutParams = (RelativeLayout.LayoutParams) searchLayout.getLayoutParams();
            layoutParams.leftMargin = ScreenUtils.dip2px(mContext, 16);
            layoutParams.rightMargin = ScreenUtils.dip2px(mContext, 56);
            searchLayout.setLayoutParams(layoutParams);
            historyLayout.setVisibility(View.VISIBLE);
            pauseAnim = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(searchEt, InputMethodManager.SHOW_FORCED);
                }
            }, 100);
        }

        presenter = new ActHomePresenter(this, this);
        sort = Constants.InterfaceConfig.SORT_COMPREHENSIVE;
        setScreenSelected(globalBtn);

        setTextWatcher();
    }

    private void resetHistory() {
        historyList.clear();
        SearchHistory searchHistory;
        for (int i = allList.size() - 1; i >= 0; i--) {
            searchHistory = allList.get(i);
            boolean isAdd = true;
            if (historyList.size() < 10) {
                for (SearchHistory searchHistoryX : historyList) {
                    if (searchHistory.getRecord().equals(searchHistoryX.getRecord())) {
                        isAdd = false;
                        break;
                    }
                }
                if (isAdd)
                    historyList.add(searchHistory);
            }
        }
    }

    private void setTextWatcher() {

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search() {

        if (searchEt.getText().toString().trim().length() == 0) {
            ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_not_search));
            return;
        }

        SearchHistory history = new SearchHistory(System.currentTimeMillis(), searchEt.getText().toString());
        if (historyList.size() == 0) {
            historyList.add(history);
        } else {

            boolean isExist = false;
            for (SearchHistory searchHistory : historyList) {
                if (searchHistory.getRecord().equals(searchEt.getText().toString())) {
                    historyList.remove(searchHistory);
                    isExist = true;
                    break;
                }
            }
            historyList.add(0, history);
            if (!isExist && historyList.size() > 10)
                historyList.remove(historyList.size() - 1);
        }
        tagAdapter.notifyDataChanged();

        historyDao.insert(history);

        if (imm != null && searchEt != null)
            imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
        isRefresh = true;
        isSearch = true;
        page = 1;
        sort = Constants.InterfaceConfig.SORT_COMPREHENSIVE;
        showLoadingDialog();
        request(page, sort, searchEt.getText().toString());

    }

    private void request(int page, int sort, String keyword) {
        presenter.commodityList(Constants.RequestConfig.COMMODITY_LIST, page, sort, keyword, id);
    }

    private void setScreenSelected(View view) {

        if (view.isSelected()) {
            if (view == globalBtn) return;
            if (isDown) {

                if (view == salesVolumeBtn) {
                    salesVolumeIv.setImageResource(R.drawable.label_screen_3);
                    sort = Constants.InterfaceConfig.SORT_SALES_RISE;
                } else {
                    priceIv.setImageResource(R.drawable.label_screen_3);
                    sort = Constants.InterfaceConfig.SORT_PRICE_RISE;
                }
                isDown = false;
            } else {
                if (view == salesVolumeBtn) {
                    salesVolumeIv.setImageResource(R.drawable.label_screen_2);
                    sort = Constants.InterfaceConfig.SORT_SALES_DROP;
                } else {
                    priceIv.setImageResource(R.drawable.label_screen_2);
                    sort = Constants.InterfaceConfig.SORT_PRICE_DROP;
                }
                isDown = true;
            }
        } else {
            globalBtn.setSelected(globalBtn == view);
            salesVolumeBtn.setSelected(salesVolumeBtn == view);
            priceBtn.setSelected(priceBtn == view);
            isDown = true;

            if (globalBtn.isSelected()) {
                salesVolumeIv.setImageResource(R.drawable.label_screen_1);
                priceIv.setImageResource(R.drawable.label_screen_1);
                sort = Constants.InterfaceConfig.SORT_COMPREHENSIVE;
            }
            if (salesVolumeBtn.isSelected()) {
                salesVolumeIv.setImageResource(R.drawable.label_screen_2);
                priceIv.setImageResource(R.drawable.label_screen_1);
                sort = Constants.InterfaceConfig.SORT_SALES_DROP;
            }
            if (priceBtn.isSelected()) {
                salesVolumeIv.setImageResource(R.drawable.label_screen_1);
                priceIv.setImageResource(R.drawable.label_screen_2);
                sort = Constants.InterfaceConfig.SORT_PRICE_DROP;
            }
        }

        if (pauseAnim) return;
        isRefresh = true;
        page = 1;
        showLoadingDialog();
        request(page, sort, searchEt.getText().toString());
    }

    private void setRecyclerView() {

        adapter = new HomeAdapter(R.layout.mh_item_home, commodityList, screenWidth);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(SSDK.getInstance().getContext(), 2));
        recyclerView.addItemDecoration(new CommodityDecoration(adapter.getHeaderLayoutCount()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_1,
                R.color.refresh_2,
                R.color.refresh_3,
                R.color.refresh_4);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkUtils.isNetWork()) {
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                isRefresh = true;
                page = 1;
                request(page, sort, searchEt.getText().toString());
            }
        });

    }

    private void loadMore() {

        if (isMoreEnd) {
            ToastUtils.showToast(mContext, ResourceUtils.getText(R.string.hint_more_not));
            return;
        }
        page++;
        request(page, sort, searchEt.getText().toString());
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {

            case Constants.RequestConfig.COMMODITY_LIST:

                if (object == null || ((List<CommodityEntity>) object).size() == 0) {
                    adapter.loadMoreEnd();
                    isMoreEnd = true;
                } else {
                    commodityList = (List<CommodityEntity>) object;

                    if (isRefresh) {
                        adapter.setNewData(commodityList);

                        isRefresh = false;
                        isMoreEnd = false;
                    } else {
                        adapter.addData(commodityList);
                    }
                    adapter.loadMoreComplete();

                    if (commodityList.size() < pageSize) {
                        adapter.loadMoreEnd();
                        isMoreEnd = true;
                    }
                }

                break;

        }
    }

    public void btClick(View view) {

        if (view.getId() == R.id.left_btn)
            onBackPressed();

        if (view.getId() == R.id.right_text_btn)
            if (flow == Constants.BundleConfig.FLOW_SEARCH) {
                if (imm != null && searchEt != null)
                    imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
                isBack = true;
            } else {
                if (imm != null && searchEt != null)
                    imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
            }

        if (view.getId() == R.id.global_btn)
            setScreenSelected(globalBtn);

        if (view.getId() == R.id.sales_volume_btn)
            setScreenSelected(salesVolumeBtn);

        if (view.getId() == R.id.price_btn)
            setScreenSelected(priceBtn);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


        ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY_DETAILS)
                .withInt(Constants.BundleConfig.ID, this.adapter.getItem(position).getId())
                .navigation();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

        dismissLoadingDialog();

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ScreenUtils.addOnSoftKeyBoardVisibleListener(CommodityListActivity.this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScreenUtils.removeOnSoftKeyBoardVisibleListener();
        if (imm != null && searchEt != null)
            imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        if (handler != null && thread != null) {
            handler.removeCallbacks(thread);
            handler = null;
            thread = null;
        }
        super.onDestroy();
    }

    @Override
    public void onSoftKeyBoardVisible(boolean visible, int windowBottom) {

        if (isBack && !isSearch)
            onBackPressed();

        searchEt.setCursorVisible(visible);

        if (pauseAnim) {
            pauseAnim = false;
            return;
        }
        resetSearchLayout(visible);
        setHistoryLayout(visible);

    }

    private void resetSearchLayout(boolean visible) {

        if (!visible)
            leftBtn.setVisibility(View.VISIBLE);

        if (visible)
            rightBtn.setVisibility(View.VISIBLE);


        thread.setVisible(visible);
        handler.postDelayed(thread, 10);
    }

    private void setHistoryLayout(final boolean visible) {

        if (visible) historyLayout.setVisibility(View.VISIBLE);

        int start = visible ? screenHeight : 0;
        int end = visible ? 0 : screenHeight;

        AnimationUtils.translation(historyLayout, Constants.AnimName.TRANS_Y,
                start, end, 150, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!visible)
                            historyLayout.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void setFlowLayout() {

        tagAdapter = new TagAdapter<SearchHistory>(historyList) {
            @Override
            public View getView(FlowLayout parent, final int position, final SearchHistory item) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.mh_history_tv, flowLayout, false);
                TypeFaceView tv = view.findViewById(R.id.flow_tv);
                tv.setText(item.getRecord());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        searchEt.setText(item.getRecord());
                        search();

                    }
                });
                return view;
            }
        };
        flowLayout.setAdapter(tagAdapter);
    }

    private class AnimRunnable implements Runnable {

        private boolean visible;
        private int leftStart;
        private int leftEnd;
        private int rightStart;
        private int rightEnd;

        private int leftMargin;
        private int rightMargin;

        public void setVisible(boolean visible) {
            this.visible = visible;
            leftStart = visible ? 48 : 16;
            leftEnd = visible ? 16 : 48;
            rightStart = visible ? 16 : 56;
            rightEnd = visible ? 56 : 16;

            leftMargin = leftStart;
            rightMargin = rightStart;
        }

        @Override
        public void run() {


            if (visible && leftMargin > leftEnd) {
                leftMargin -= 2;
                if (leftMargin == leftEnd)
                    leftBtn.setVisibility(View.GONE);
            }

            if (!visible && leftMargin < leftEnd)
                leftMargin += 2;

            if (visible && rightMargin < rightEnd)
                rightMargin += 2;

            if (!visible && rightMargin > rightEnd) {
                rightMargin -= 2;
                if (rightMargin == rightEnd)
                    rightBtn.setVisibility(View.GONE);
            }

            layoutParams = (RelativeLayout.LayoutParams) searchLayout.getLayoutParams();
            layoutParams.leftMargin = ScreenUtils.dip2px(mContext, leftMargin);
            layoutParams.rightMargin = ScreenUtils.dip2px(mContext, rightMargin);
            searchLayout.setLayoutParams(layoutParams);

            if (rightMargin != rightEnd)
                handler.postDelayed(this, 10);
        }
    }
}
