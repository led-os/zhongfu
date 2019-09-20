package com.seven.module_user.ui.fragment.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.home.OrderEntity;
import com.seven.lib_model.model.user.CancelOrderEntity;
import com.seven.lib_model.model.user.mine.GoodsListBean;
import com.seven.lib_model.model.user.mine.OrderDetailEntity;
import com.seven.lib_model.model.user.mine.OrderDetailRequestEntity;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.view.BaseRecyclerView;

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
@Route(path = RouterPath.ACTIVITY_MINE_SHOP_PAY)
public class UserOrderDetailActivity extends BaseTitleActivity {
    @Autowired(name = "order_id")
    String orderId;
    @BindView(R2.id.address_icon)
    ImageView addressIcon;
    @BindView(R2.id.name_and_phone)
    TextView nameAndPhone;
    @BindView(R2.id.address_tx)
    TextView addressTx;
    @BindView(R2.id.list_view)
    BaseRecyclerView listView;
    @BindView(R2.id.express_fee)
    TextView expressFee;
    @BindView(R2.id.order_money)
    TextView orderMoney;
    @BindView(R2.id.pay_money)
    TextView payMoney;
    @BindView(R2.id.order_number)
    TextView orderNumber;
    @BindView(R2.id.order_time)
    TextView orderTime;
    @BindView(R2.id.order_state)
    TextView orderState;
    @BindView(R2.id.total_money)
    TextView totalMoney;
    @BindView(R2.id.cancel_order)
    TextView cancelOrder;
    @BindView(R2.id.pay_order)
    TextView payOrder;
    @BindView(R2.id.order_details_ll)
    LinearLayout order_details_ll;
    OrderEntity orderEntity;

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
    protected int getLayoutId() {
        return R.layout.mu_activity_order_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_order_detail);
        payOrder.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        if (intent == null) {
            intent = getIntent();
            orderId = intent.getStringExtra("order_id");
            if (!TextUtils.isEmpty(orderId)) {
                getData();
            }
        }
    }

    private void getData() {
        OrderDetailRequestEntity entity = new OrderDetailRequestEntity(Integer.valueOf(orderId));
        ApiManager.getOrderDetailInfo(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<OrderDetailEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<OrderDetailEntity> orderDetailEntityBaseResult) {
                        setData(orderDetailEntityBaseResult.getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setData(OrderDetailEntity data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.init(layoutManager, new BaseQuickAdapter<GoodsListBean, BaseViewHolder>(R.layout.item_order_detail_goods, data.getGoods_list()) {

            @Override
            protected void convert(BaseViewHolder helper, GoodsListBean item) {
                ImageView imageView = helper.getView(R.id.goods_img);
                helper.setText(R.id.goods_name, item.getGoods_name())
                        .setText(R.id.goods_number, "X" + item.getNumber())
                        .setText(R.id.goods_money, "￥" + item.getPrice());
                GlideUtils.loadImage(mContext, item.getGoods_thumb(), imageView, true);
            }
        }, false)
                .removeItemDecoration();
        String status = "";
        switch (data.getStatus()) {
            case 1:
                status = "待付款";
                order_details_ll.setVisibility(View.VISIBLE);
                break;
            case 2:
                status = "待发货";
                order_details_ll.setVisibility(View.GONE);
                break;
            case 3:
                status = "待收货";
                order_details_ll.setVisibility(View.GONE);
                break;
            case 4:
                status = "已完成";
                order_details_ll.setVisibility(View.GONE);
                break;
            case 5:
                status = "已取消";
                order_details_ll.setVisibility(View.GONE);
                break;
            default:
        }
        addressTx.setText(data.getAddress());
        nameAndPhone.setText(data.getContact_name()+"  "+data.getContact_phone());
        orderNumber.setText(data.getOrder_sn());
        payMoney.setText("￥"+data.getTotal());
        orderMoney.setText("￥"+data.getTotal());
        totalMoney.setText("待支付：￥"+data.getTotal());
        orderTime.setText(data.getCreated_at());
        orderState.setText(status);
        orderEntity = new OrderEntity();
        orderEntity.setOrder_sn(data.getOrder_sn());
        orderEntity.setTotal(Double.parseDouble(data.getTotal()));
        orderEntity.setToken_price(data.getToken_total());
        orderEntity.setSubject(data.getGoods_list().get(0).getGoods_name());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.pay_order){
            ARouter.getInstance().build(RouterPath.ACTIVITY_PAY)
                    .withBoolean(Constants.BundleConfig.NORMAL, true)
                    .withSerializable(Constants.BundleConfig.ENTITY,orderEntity)
                    .navigation();
        }else if (view.getId() == R.id.cancel_order){
            initWaitPay();
        }
    }

    private void initWaitPay() {
        final List<String> cancelReasons = new ArrayList<>();
        cancelReasons.add("不想买了");
        cancelReasons.add("信息填写有误，重新拍");
        cancelReasons.add("卖家缺货");
        cancelReasons.add("其他原因");
        OptionsPickerView cancelReasonPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                cancelOrder(Integer.parseInt(orderId),cancelReasons.get(options1));
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

    private void cancelOrder(int id,String comment){
        CancelOrderEntity entity = new CancelOrderEntity();
        entity.setComment(comment);
        entity.setOrder_id(id);
        ApiManager.cancelOrder(entity)
                .subscribe(new CommonObserver<BaseResult>(){
                    @Override
                    public void onNext(BaseResult baseResult) {
                       finish();
                    }
                });
    }
}
