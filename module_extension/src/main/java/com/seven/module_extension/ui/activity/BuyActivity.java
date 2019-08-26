package com.seven.module_extension.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.BdGoodsEntity;
import com.seven.lib_model.model.extension.DefaultAddress;
import com.seven.lib_model.model.extension.GoodsItemEntity;
import com.seven.lib_model.model.home.OrderEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.model.user.mine.AddressEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.BuyBdAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterPath.ACTIVITY_BUY_BD)
public class BuyActivity extends BaseTitleActivity {
    @Autowired(name = Constants.BundleConfig.EVENT_CODE)
    int code = 0;

    @BindView(R2.id.me_buy_bd_rv)
    RecyclerView meBuyBdRv;
    @BindView(R2.id.me_buy_price)
    TypeFaceView me_buy_price;
    @BindView(R2.id.me_buy_bd_address1)
    TypeFaceView meBuyBdAddress1;
    @BindView(R2.id.me_buy_bd_address2)
    TypeFaceView meBuyBdAddress2;
    @BindView(R2.id.me_buy_bd_ll)
    LinearLayout meBuyBdLl;
    @BindView(R2.id.me_buy_bd_btn)
    TypeFaceView meBuyBdBtn;
    private BuyBdAdapter adapter;
    private String shopIds = "";
    ExActivityPresenter presenter;
    UserEntity entity;
    private OrderEntity orderEntity;
    AddressEntity addressEntity;
    List<BdGoodsEntity> list = new ArrayList<>();
    private int type = 0;
    private int addressId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_buy_bd;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.me_buy_bd_ll) {
            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_ADDRESS)
                    .withInt(Constants.BundleConfig.EVENT_CODE, 159357)
                    .navigation();
        }
        if (view.getId() == R.id.me_buy_bd_btn) {
            if (!me_buy_price.getText().toString().contains("0.00")) {
                if (me_buy_price.getText().toString().contains("79")) {
                    type = 1;
                } else {
                    type = 2;
                }
                presenter.getOrder(1, addressId, type);
            } else {
                ToastUtils.showToast(mContext, "请选择商品");
            }
        }
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) return;
            orderEntity = (OrderEntity) object;
            if (orderEntity != null) {
                ARouter.getInstance().build(RouterPath.ACTIVITY_PAY)
                        .withBoolean(Constants.BundleConfig.NORMAL, false)
                        .withSerializable(Constants.BundleConfig.ENTITY, orderEntity)
                        .navigation();
            }
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        statusBar = StatusBar.LIGHT;
        EventBus.getDefault().register(this);
        setTitleText(R.string.me_buy_bd_title);
        presenter = new ExActivityPresenter(this, this);
        String userInfo = SharedData.getInstance().getUserInfo();
        entity = new Gson().fromJson(userInfo, UserEntity.class);
        meBuyBdLl.setOnClickListener(this);
        meBuyBdBtn.setOnClickListener(this);
        ApiManager.getBdGoods().subscribe(new CommonObserver<BaseResult<BdGoodsEntity>>() {
            @Override
            public void onNext(BaseResult<BdGoodsEntity> bdGoodsEntityBaseResult) {
                BdGoodsEntity entity = new BdGoodsEntity();
                entity = bdGoodsEntityBaseResult.getData();
                list.add(entity);
//                list.addAll(bdGoodsEntityBaseResult.getData().getGoods_list());
                ApiManager.get79().subscribe(new CommonObserver<BaseResult<BdGoodsEntity>>() {
                    @Override
                    public void onNext(BaseResult<BdGoodsEntity> bdGoodsEntityBaseResult) {
                        BdGoodsEntity entity1 = new BdGoodsEntity();
                        entity1 = bdGoodsEntityBaseResult.getData();
                        list.add(entity1);
//                        list.addAll(bdGoodsEntityBaseResult.getData().getGoods_list());
                        initRv(list);
                    }
                });
            }
        });
        ApiManager.getDefaultAddress().subscribe(new CommonObserver<BaseResult<DefaultAddress>>() {
            @Override
            public void onNext(BaseResult<DefaultAddress> defaultAddressBaseResult) {
                addressId = defaultAddressBaseResult.getData().getId();
                String name = defaultAddressBaseResult.getData().getContact_name();
                String phone = defaultAddressBaseResult.getData().getContact_phone();
                String province = defaultAddressBaseResult.getData().getProvince_name();
                String city = defaultAddressBaseResult.getData().getCity_name();
                String district = defaultAddressBaseResult.getData().getDistrict_name();
                String address = defaultAddressBaseResult.getData().getAddress();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(district) || TextUtils.isEmpty(address)){
                    meBuyBdAddress1.setText("新增地址");
                    meBuyBdAddress2.setText("当前未设置收获地址");
                }else {
                    meBuyBdAddress1.setText(name + " " + phone);
                    meBuyBdAddress2.setText(province + city + district + address);
                }
            }
        });
    }

    private void initRv(final List<BdGoodsEntity> list) {
        adapter = new BuyBdAdapter(R.layout.me_item_buybd, list);
        meBuyBdRv.setLayoutManager(new LinearLayoutManager(mContext));
        meBuyBdRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<BdGoodsEntity> list1 = adapter.getData();
                BdGoodsEntity entity = list1.get(position);
                for (BdGoodsEntity item : list1) {
                    if (entity.getPrice() == item.getPrice()) {
                        item.setSelected(true);
                        me_buy_price.setText("总价：" + new DecimalFormat("#.00").format(entity.getPrice()));
                    } else {
                        item.setSelected(false);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {

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

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.getWhat() == Constants.EventConfig.BUY_BD) {
            addressEntity = (AddressEntity) ((ObjectsEvent) event).getObjects()[0];
            addressId = addressEntity.getId();
            meBuyBdAddress1.setText(addressEntity.getContact_name() + " " + addressEntity.getContact_phone());
            meBuyBdAddress2.setText(addressEntity.getProvince_name() + addressEntity.getCity_name() + addressEntity.getDistrict_name() + addressEntity.getAddress());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
