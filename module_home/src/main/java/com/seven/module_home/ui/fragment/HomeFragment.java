package com.seven.module_home.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.OutlineUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.loader.GlideImageLoader;
import com.seven.lib_model.model.home.BannerEntity;
import com.seven.lib_model.model.home.BannerEntranceEntity;
import com.seven.lib_model.model.home.CommodityEntity;
import com.seven.lib_model.presenter.home.FgHomePresenter;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_home.R;
import com.seven.module_home.R2;
import com.seven.module_home.adapter.BannerEntranceAdapter;
import com.seven.module_home.adapter.BannerEntranceListAdapter;
import com.seven.module_home.adapter.HomeAdapter;
import com.seven.module_home.widget.decoration.HomeDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/26
 */
@Route(path = RouterPath.FRAGMENT_HOME)
public class HomeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    private int page = 1;
    private int pageSize = 10;
    public boolean isRefresh;
    private boolean isMoreEnd;

    @BindView(R2.id.title_rl)
    public RelativeLayout titleRl;

    @BindView(R2.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.recycler_view)
    public RecyclerView recyclerView;
    public HomeAdapter adapter;
    private List<CommodityEntity> commodityList;

    //banner
    private LinearLayout searchLayout;

    private RelativeLayout bannerLayout;
    private Banner banner;
    private List<BannerEntity> bannerList;
    private List<String> banners;

    private RecyclerView bannerRecycler;
    private BannerEntranceListAdapter entranceAdapter;
    private List<List<BannerEntranceEntity>> entranceList;

    private FgHomePresenter presenter;

    @Override
    public int getContentViewId() {
        return R.layout.mh_fragment_home;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleRl.getLayoutParams();
        params.topMargin = notificationHeight;
        titleRl.setLayoutParams(params);

        titleRl.setOnClickListener(this);

        setRecyclerView();

        presenter = new FgHomePresenter(this, this);
        showLoadingDialog();
        request(page);
    }

    private void request(int page) {
        if (page == 1) {
            presenter.banner(Constants.RequestConfig.BANNER);
            presenter.entrance(Constants.RequestConfig.ENTRANCE);
        }
        presenter.commodityRecommendList(Constants.RequestConfig.COMMODITY_RECOMMEND_LIST, page, pageSize);
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
        adapter.addHeaderView(getHeaderView());
        recyclerView.setLayoutManager(new GridLayoutManager(SSDK.getInstance().getContext(), 2));
        recyclerView.addItemDecoration(new HomeDecoration(adapter.getHeaderLayoutCount()));
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
                request(page);
            }
        });

    }

    private void loadMore() {

        if (isMoreEnd) {
            ToastUtils.showToast(getActivity(), ResourceUtils.getText(R.string.hint_more_not));
            return;
        }
        page++;
        request(page);
    }

    private View getHeaderView() {
        View bannerView = LayoutInflater.from(SSDK.getInstance().getContext()).inflate(
                R.layout.mh_header_banner, (ViewGroup) recyclerView.getParent(), false);

        searchLayout = getView(bannerView, searchLayout, R.id.search_layout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCommodity(Constants.BundleConfig.FLOW_SEARCH, 0);
            }
        });

        bannerLayout = getView(bannerView, bannerLayout, R.id.banner_layout);
        banner = getView(bannerView, banner, R.id.banner_view);

        OutlineUtils.getInstance().outlineView(bannerLayout, 0);

        bannerRecycler = getView(bannerView, bannerRecycler, R.id.recycler_view);
        entranceAdapter = new BannerEntranceListAdapter(R.layout.mh_item_banner_entrance_list, entranceList, screenWidth, this);
        entranceAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SSDK.getInstance().getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bannerRecycler.setLayoutManager(layoutManager);
        bannerRecycler.setAdapter(entranceAdapter);

        return bannerView;
    }

    private void setBannerView() {

        banners = new ArrayList<>();
        for (BannerEntity bannerEntity : bannerList)
            banners.add(bannerEntity.getShow_picture());

        banner.setImages(banners)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int i) {

                        String link = bannerList.get(i).getLink();
                        if (TextUtils.isEmpty(link)) return;

                        if (link.startsWith("category:")) {

                            String[] array = link.split(":");
                            if (array.length == 2)
                                intentCommodity(Constants.BundleConfig.FLOW_ENTRANCE, Integer.parseInt(array[1]));

                        } else
                            ARouter.getInstance().build(RouterPath.ACTIVITY_WEB)
                                    .withString(Constants.BundleConfig.URL, link)
                                    .navigation();

                    }
                })
                .start();

    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {
            case Constants.RequestConfig.BANNER:

                if (object == null) return;

                bannerList = (List<BannerEntity>) object;

                if (bannerList.size() == 0) return;
                setBannerView();

                break;

            case Constants.RequestConfig.ENTRANCE:

                if (object == null) return;

                List<BannerEntranceEntity> list = (List<BannerEntranceEntity>) object;
                List<BannerEntranceEntity> newList = new ArrayList<>();

                entranceList = new ArrayList<>();
                for (BannerEntranceEntity entity : list) {
                    if (newList.size() < 2)
                        newList.add(entity);
                    else {
                        entranceList.add(newList);
                        newList = new ArrayList<>();
                        newList.add(entity);
                    }
                }
                entranceList.add(newList);

                entranceAdapter.setNewData(entranceList);

                break;

            case Constants.RequestConfig.COMMODITY_RECOMMEND_LIST:

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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.title_rl)
            ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY)
                    .withInt(Constants.BundleConfig.FLOW, Constants.BundleConfig.FLOW_SEARCH)
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter instanceof BannerEntranceAdapter)
            intentCommodity(Constants.BundleConfig.FLOW_ENTRANCE, ((BannerEntranceAdapter) adapter).getItem(position).getId());
        else
            ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY_DETAILS)
                    .withInt(Constants.BundleConfig.ID, this.adapter.getItem(position).getId())
                    .navigation();
    }

    private void intentCommodity(int flow, int id) {

        if (id > 0)
            ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY)
                    .withInt(Constants.BundleConfig.FLOW, flow)
                    .withInt(Constants.BundleConfig.ID, id)
                    .navigation();
        else
            ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY)
                    .withInt(Constants.BundleConfig.FLOW, flow)
                    .navigation();
    }
}
