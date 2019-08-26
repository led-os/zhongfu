package com.seven.module_home.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.glide.loader.GlideImageLoader;
import com.seven.lib_model.model.home.CartEntity;
import com.seven.lib_model.model.home.CartTotalEntity;
import com.seven.lib_model.model.home.CommodityDetailsEntity;
import com.seven.lib_model.presenter.home.ActHomePresenter;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_home.R;
import com.seven.module_home.R2;
import com.seven.module_home.adapter.CommodityDetailsAdapter;
import com.seven.module_home.ui.sheet.ShareSheet;
import com.seven.module_home.ui.sheet.SpecificationSheet;
import com.seven.module_home.widget.decoration.CommodityDetailsDecoration;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/18
 */
@Route(path = RouterPath.ACTIVITY_COMMODITY_DETAILS)
public class CommodityDetailsActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemClickListener {

    @Autowired(name = Constants.BundleConfig.ID)
    public int id;

    public boolean isRefresh;

    @BindView(R2.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.recycler_view)
    public RecyclerView recyclerView;
    private CommodityDetailsAdapter adapter;
    private List<String> detailsPictures;

    @BindView(R2.id.count_tv)
    public TypeFaceView countTv;
    @BindView(R2.id.collection_rl)
    public RelativeLayout collectionRl;

    private Banner banner;
    private TypeFaceView tokenTv;
    private TypeFaceView oldPriceTv;
    private TypeFaceView titleTv;
    private TypeFaceView priceTv;
    private TypeFaceView buyCountTv;
    private RelativeLayout specificationsRl;

    private SpecificationSheet specificationSheet;

    private ActHomePresenter presenter;
    private CommodityDetailsEntity detailsEntity;

    private ShareSheet shareSheet;

    @Override
    protected int getLayoutId() {
        return R.layout.mh_activity_commodity_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);

        setTitleText(R.string.label_commodity_details);

    }

    @Override
    protected void initBundleData(Intent intent) {

        setRecyclerView();

        presenter = new ActHomePresenter(this, this);
        showLoadingDialog();
        request();
    }

    private void request() {

        presenter.commodityDetails(Constants.RequestConfig.COMMODITY_DETAILS, id);
        presenter.cartTotal(Constants.RequestConfig.CART_TOTAL);
    }

    private void setRecyclerView() {

        adapter = new CommodityDetailsAdapter(R.layout.mh_item_commodity_details, detailsPictures, screenWidth);
        adapter.setOnItemClickListener(this);
        adapter.addHeaderView(getHeaderView());
        recyclerView.setLayoutManager(new LinearLayoutManager(SSDK.getInstance().getContext()));
        recyclerView.addItemDecoration(new CommodityDetailsDecoration(adapter.getHeaderLayoutCount()));
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
                request();
            }
        });

    }

    private View getHeaderView() {
        View view = LayoutInflater.from(SSDK.getInstance().getContext()).inflate(
                R.layout.mh_header_commodity_details, (ViewGroup) recyclerView.getParent(), false);

        banner = getView(view, banner, R.id.banner_view);
        titleTv = getView(view, titleTv, R.id.title_tv);
        priceTv = getView(view, priceTv, R.id.price_tv);
        buyCountTv = getView(view, buyCountTv, R.id.buy_count_tv);
        tokenTv = getView(view, tokenTv, R.id.token_tv);
        oldPriceTv = getView(view, oldPriceTv, R.id.old_price_tv);
        oldPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        specificationsRl = getView(view, specificationsRl, R.id.specifications_rl);
        specificationsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpecifications(detailsEntity);
            }
        });

        return view;
    }

    private void setHeaderView(CommodityDetailsEntity entity) {

        banner.setImages(entity.getPictures())
                .setImageLoader(new GlideImageLoader())
                .start();

        tokenTv.setText(ResourceUtils.getFormatText(R.string.hint_token, FormatUtils.formatCurrencyD(entity.getToken_price())));
        oldPriceTv.setText(ResourceUtils.getText(R.string.rmb) + FormatUtils.formatCurrencyD(entity.getPrice()));
        titleTv.setText(entity.getGoods_name());
        priceTv.setText(FormatUtils.formatCurrencyD(entity.getPrice()));
        buyCountTv.setText(ResourceUtils.getFormatText(R.string.hint_sales_count, entity.getSales()));
    }

    public void btClick(View view) {

        if (view.getId() == R.id.share_rl)
            shareSheet();

        if (view.getId() == R.id.shopping_rl) {

            if (!isLogin()) return;

            ARouter.getInstance().build(RouterPath.ACTIVITY_SHOPPING_CART)
                    .withInt(Constants.BundleConfig.EVENT_CODE, Constants.EventConfig.SHOPPING_CART)
                    .navigation();

        }

        if (view.getId() == R.id.collection_rl) {

            if (!isLogin()) return;

            showLoadingDialog();
            presenter.collect(Constants.RequestConfig.COLLECT, id);
        }

        if (view.getId() == R.id.buy_rl || view.getId() == R.id.shopping_add_rl)
            showSpecifications(detailsEntity);

    }

    private void shareSheet() {
        if (shareSheet == null)
            shareSheet = new ShareSheet(this, R.style.Dialog, null, detailsEntity);

        if (!shareSheet.isShowing())
            shareSheet.showDialog(0, -screenHeight);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);

        switch (code) {

            case Constants.RequestConfig.COMMODITY_DETAILS:

                if (object == null) return;

                detailsEntity = (CommodityDetailsEntity) object;

                setHeaderView(detailsEntity);

                detailsPictures = detailsEntity.getDetail_pictures();

                adapter.setNewData(detailsPictures);

                collectionRl.setSelected(detailsEntity.getIs_collect() == 1);

                break;

            case Constants.RequestConfig.CART_TOTAL:

                if (object == null) return;

                CartTotalEntity totalEntity = (CartTotalEntity) object;

                countTv.setText(String.valueOf(totalEntity.getTotal()));

                break;

            case Constants.RequestConfig.CART_ADD:

                countTv.setText(String.valueOf(Integer.parseInt(countTv.getText().toString()) + 1));
                presenter.cartTotal(Constants.RequestConfig.CART_TOTAL);
                if (specificationSheet.isShowing())
                    specificationSheet.dismiss();

                break;

            case Constants.RequestConfig.COLLECT:

                detailsEntity.setIs_collect(detailsEntity.getIs_collect() == 1 ? 0 : 1);
                collectionRl.setSelected(detailsEntity.getIs_collect() == 1);

                break;

        }

    }

    private void showSpecifications(CommodityDetailsEntity entity) {

        if (specificationSheet == null) {
            specificationSheet = new SpecificationSheet(this, R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                    if (!isLogin()) return;

                    int skuId = (int) objects[0];
                    int number = (int) objects[1];

                    showLoadingDialog();
                    presenter.cartAdd(Constants.RequestConfig.CART_ADD, id, skuId, number);

                }

                @Override
                public void onClick(View v, Object... objects) {

                    if (!isLogin()) return;

                    CommodityDetailsEntity.SkuListBean skuBean = (CommodityDetailsEntity.SkuListBean) objects[0];
                    int number = (int) objects[1];

                    List<CartEntity> cartList = new ArrayList<>();
                    CartEntity cartEntity = new CartEntity();
                    cartEntity.setFrom(Constants.InterfaceConfig.GOODS_DETAILS);
                    cartEntity.setId(detailsEntity.getId());
                    cartEntity.setGoods_name(detailsEntity.getGoods_name());
                    cartEntity.setSku_id(skuBean.getId());
                    cartEntity.setNumber(number);
                    cartEntity.setPrice(skuBean.getPrice());
                    cartEntity.setSales(skuBean.getSales());
                    cartEntity.setStock(skuBean.getStock());
                    cartEntity.setThumb(skuBean.getThumb());
                    cartList.add(cartEntity);

                    ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY_ORDER)
                            .withSerializable(Constants.BundleConfig.ENTITY, (Serializable) cartList)
                            .navigation();
                }

                @Override
                public void dismiss() {

                }
            }, entity);
        }

        if (!specificationSheet.isShowing())
            specificationSheet.showDialog(0, -screenHeight);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getWhat()) {

            case Constants.EventConfig.SHOPPING_CART:

                String ids = (String) ((ObjectsEvent) event).getObjects()[0];

                List<CartEntity> cartList = new ArrayList<>();
                CartEntity cartEntity = new CartEntity();
                cartEntity.setFrom(Constants.InterfaceConfig.CART);
                cartEntity.setCart_ids(ids);
                cartList.add(cartEntity);

                ARouter.getInstance().build(RouterPath.ACTIVITY_COMMODITY_ORDER)
                        .withSerializable(Constants.BundleConfig.ENTITY, (Serializable) cartList)
                        .navigation();

                break;
        }
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

        dismissLoadingDialog();

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
