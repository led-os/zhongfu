package com.seven.module_user.ui.fragment.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.user.CancelOrderEntity;
import com.seven.lib_model.model.user.ConfirmOrderEntity;
import com.seven.lib_model.model.user.OrderEntity;
import com.seven.lib_model.model.user.OrderListRequestEntity;
import com.seven.lib_model.model.user.mine.CommonListPageEntity;
import com.seven.lib_model.model.user.mine.GoodsListBean;
import com.seven.lib_model.user.UserPresenter;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.view.BaseRecyclerView;
import com.seven.module_user.ui.fragment.view.DividerSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xxxxxxH on 2019/4/9.
 */

public class OrderListFragment extends BaseFragment {

    @BindView(R2.id.list_view)
    BaseRecyclerView recyclerView;

    private int currentListType;
    private int currentPage = 1;

    private UserPresenter presenter;

    public static OrderListFragment getInstance(int type) {
        OrderListFragment fragment = new OrderListFragment();
        fragment.currentListType = type;
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
    public int getContentViewId() {
        return R.layout.mu_common_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        //getData();
        presenter = new UserPresenter(this, this);
    }

    @Override
    public void onClick(View view) {

    }

    private void cancelOrder(int id, String comment) {
        CancelOrderEntity entity = new CancelOrderEntity();
        entity.setComment(comment);
        entity.setOrder_id(id);
        ApiManager.cancelOrder(entity)
                .subscribe(new CommonObserver<BaseResult>() {
                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getCode() == 0) {
                            ToastUtils.showToast(getActivity(), baseResult.getMessage());
                        }
                        getData();
                    }
                });
    }

    private void initWaitPay(final int id) {
        final List<String> cancelReasons = new ArrayList<>();
        cancelReasons.add("不想买了");
        cancelReasons.add("信息填写有误，重新拍");
        cancelReasons.add("卖家缺货");
        cancelReasons.add("其他原因");
        OptionsPickerView cancelReasonPickerView = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                cancelOrder(id, cancelReasons.get(options1));
            }
        }).setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(getResources().getColor(R.color.color_eee))
                .setCancelColor(getResources().getColor(R.color.color_6c))
                .setSubmitColor(getResources().getColor(R.color.color_1e1d1d))
                .setTextColorCenter(getResources().getColor(R.color.color_1e1d1d))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .build();
        cancelReasonPickerView.setPicker(cancelReasons);
        cancelReasonPickerView.show();
    }

    private void getData() {
        OrderListRequestEntity entity = new OrderListRequestEntity();
        entity.setPage(currentPage);
        entity.setStatus(currentListType);
        ApiManager.getOrderList(entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResult<CommonListPageEntity<OrderEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<CommonListPageEntity<OrderEntity>> commonListPageEntityBaseResult) {
                        setData(commonListPageEntityBaseResult.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setData(CommonListPageEntity<OrderEntity> data) {
        if (recyclerView.getAdapter() == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.init(layoutManager, new BaseQuickAdapter<OrderEntity, BaseViewHolder>(R.layout.item_order_list_layout, null) {
                @Override
                protected void convert(BaseViewHolder helper, final OrderEntity item) {
                    GoodsListBean goods = item.getGoods_list().get(0);
                    helper.addOnClickListener(R.id.button_1)
                            .addOnClickListener(R.id.pay_btn)
                            .setText(R.id.order_number, "订单号：" + item.getOrder_sn())
                            .setText(R.id.goods_name, goods.getGoods_name())
                            .setText(R.id.goods_money, "￥" + goods.getPrice())
                            .setText(R.id.goods_number, "X" + goods.getNumber())
                            .setText(R.id.order_number_total, "共" + item.getGoods_list().size() + "件商品")
                            .setText(R.id.order_money_total, "合计：￥" + item.getTotal());
                    String status = "";
                    switch (item.getStatus()) {
                        case 1:
                            status = "待付款";
                            helper.setGone(R.id.pay_btn, true);
                            helper.setGone(R.id.check_wl, false);
                            helper.setGone(R.id.confirm_take, false);
                            helper.setGone(R.id.button_1, true);
                            break;
                        case 2:
                            status = "待发货";
                            helper.setGone(R.id.pay_btn, false);
                            helper.setGone(R.id.check_wl, false);
                            helper.setGone(R.id.confirm_take, false);
                            helper.setGone(R.id.button_1, true);
                            break;
                        case 3:
                            status = "待收货";
                            helper.setGone(R.id.pay_btn, false);
                            helper.setGone(R.id.check_wl, true);
                            helper.setGone(R.id.confirm_take, true);
                            helper.setGone(R.id.button_1, false);
                            break;
                        case 4:
                            status = "已完成";
                            helper.setGone(R.id.pay_btn, false);
                            helper.setGone(R.id.check_wl, false);
                            helper.setGone(R.id.confirm_take, false);
                            helper.setGone(R.id.button_1, false);
                            break;
                        case 5:
                            status = "已取消";
                            break;
                        default:
                    }
                    helper.setText(R.id.state, status);
                    ImageView imageView = helper.getView(R.id.goods_img);
                    GlideUtils.loadImage(mContext, goods.getGoods_thumb(), imageView);
//                    if (currentListType == 2 || currentListType == 3 || currentListType == 4) {
//                        helper.setGone(R.id.pay_btn, false);
//                    }
//                    if (currentListType == 3) {
//                        helper.setGone(R.id.check_wl, true);
//                    }
                    helper.addOnClickListener(R.id.check_wl);
                    helper.addOnClickListener(R.id.confirm_take);

                }
            }).setEmptyView(getEmptyView())
                    .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            OrderListFragment.this.onRefresh();
                        }
                    })
                    .setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            onLoadMore();
                        }
                    })
                    .changeItemDecoration(new DividerSpaceItemDecoration(6))
                    .addOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                            OrderEntity entity = (OrderEntity) adapter.getData().get(position);
                            RouterUtils.getInstance().routerWithString(RouterPath.ACTIVITY_MINE_SHOP_PAY, "order_id", String.valueOf(entity.getId()));
                        }
                    })
                    .addOnItemChildClickListener(new OnItemChildClickListener() {
                        @Override
                        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            OrderEntity entity = (OrderEntity) adapter.getData().get(position);
                            GoodsListBean goods = entity.getGoods_list().get(0);
                            if (view.getId() == R.id.button_1) {
                                initWaitPay(entity.getId());
                            }
                            if (view.getId() == R.id.pay_btn) {
                                com.seven.lib_model.model.home.OrderEntity newOrder = new com.seven.lib_model.model.home.OrderEntity();
                                newOrder.setOrder_sn(entity.getOrder_sn());
                                newOrder.setSubject(goods.getGoods_name());
                                newOrder.setToken_price(entity.getToken_total());
                                newOrder.setTotal(Double.parseDouble(entity.getTotal()));
                                ARouter.getInstance().build(RouterPath.ACTIVITY_PAY)
                                        .withBoolean(Constants.BundleConfig.NORMAL, true)
                                        .withSerializable(Constants.BundleConfig.ENTITY, newOrder)
                                        .navigation();
                            }
                            if (view.getId() == R.id.check_wl) {
                                ARouter.getInstance().build(RouterPath.ACTIVITY_LOGISTICS)
                                        .withInt("orderId", entity.getId())
                                        .navigation();
                            }
                            if (view.getId() == R.id.confirm_take) {
                                presenter.confirmOrder(1, entity.getId());
//                                ConfirmOrderEntity entity1 = new ConfirmOrderEntity();
//                                entity1.setOrder_id(entity.getId());
//                                ApiManager.confirmOrder(entity1).subscribe(new CommonObserver<BaseResult>(){
//                                    @Override
//                                    public void onNext(BaseResult baseResult) {
//                                        if (baseResult.getCode() == 1){
//                                            ToastUtils.showToast(getActivity(),baseResult.getMessage());
//                                            getData();
//                                        }else {
//                                            ToastUtils.showToast(getActivity(),baseResult.getMessage());
//                                        }
//                                    }
//                                });
                            }

                        }
                    });
        }
        if (currentPage == 1) {
            recyclerView.getAdapter().setNewData(data.getItems());
            recyclerView.setRefreshing(false);
        } else {
            recyclerView.addDataList(data.getItems());
            if (data.getItems().size() == 0) {
                recyclerView.getAdapter().loadMoreEnd();
            } else
                recyclerView.getAdapter().loadMoreComplete();
        }

        if (data.getPagination().getTotal_page() == 1) {
            recyclerView.setEnableLoadMore(false);
        } else {
            recyclerView.setEnableLoadMore(true);
        }
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            ToastUtils.showToast(getActivity(), "操作成功");
            getData();
        }
    }

    private View getEmptyView() {
        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("暂无数据");
        return textView;
    }

    private void onRefresh() {
        currentPage = 1;
        getData();
    }

    private void onLoadMore() {
        currentPage++;
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        // getData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getData();
        }
    }
}
