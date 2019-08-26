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
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.extension.BdGoodsEntity;
import com.seven.lib_model.model.extension.BindEntity;
import com.seven.lib_model.model.extension.DefaultAddress;
import com.seven.lib_model.model.extension.GoodsItemEntity;
import com.seven.lib_model.model.extension.RewardLsitItemEntity;
import com.seven.lib_model.model.user.mine.AddressEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.BuyBdAdapter;
import com.seven.module_extension.ui.adapter.ReciveBdAdapter;
import com.seven.module_extension.ui.dialog.ReceiveDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.completable.CompletableToObservable;

/**
 * Created by xxxxxxH on 2019/5/12.
 */
@Route(path = RouterPath.ACTIVITY_BD_LIST)
public class ReceiveBDActivity extends BaseTitleActivity {
    @Autowired(name = "id")
    int id=0;
    @BindView(R2.id.me_buy_bd_address1)
    TypeFaceView meBuyBdAddress1;
    @BindView(R2.id.me_buy_bd_address2)
    TypeFaceView meBuyBdAddress2;
    @BindView(R2.id.me_buy_bd_ll)
    LinearLayout meBuyBdLl;
    @BindView(R2.id.me_receive_bd_rv)
    RecyclerView meReceiveBdRv;
    @BindView(R2.id.me_buy_bd_btn)
    TypeFaceView meBuyBdBtn;

    private ReciveBdAdapter adapter;
    private ExActivityPresenter presenter;
    private List<GoodsItemEntity> rewardLsit;
    private int contactId = 0;
    private String ids = "";
    private BindEntity entity;
    private ReceiveDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_receivebd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.me_receive_good);
        EventBus.getDefault().register(this);
        meBuyBdLl.setOnClickListener(this);
        meBuyBdBtn.setOnClickListener(this);
    }

    private void request(String id){
        presenter.rewardList(1,id);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.me_buy_bd_ll) {
            ARouter.getInstance()
                    .build(RouterPath.ACTIVITY_ADDRESS)
                    .withInt(Constants.BundleConfig.EVENT_CODE, 110110)
                    .navigation();
        }else if (view.getId() == R.id.me_buy_bd_btn){
            if (contactId == 0){
                ToastUtils.showToast(mContext,"请选择地址");
            }else {

                presenter.getReceive(2,String.valueOf(id),String.valueOf(contactId));

            }
        }
    }
    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.getWhat() == 250) {
            AddressEntity entity = (AddressEntity) ((ObjectsEvent) event).getObjects()[0];
            contactId = entity.getId();
            meBuyBdAddress1.setText(entity.getContact_name() + " " + entity.getContact_phone());
            meBuyBdAddress2.setText(entity.getProvince_name() + entity.getCity_name() + entity.getDistrict_name() + entity.getAddress());
        }
    }
    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        if (intent ==null)intent =getIntent();
        id = intent.getIntExtra("id",-1);
        presenter = new ExActivityPresenter(this,this);
        setRv();
        request(String.valueOf(id));
        ApiManager.getDefaultAddress().subscribe(new CommonObserver<BaseResult<DefaultAddress>>() {
            @Override
            public void onNext(BaseResult<DefaultAddress> defaultAddressBaseResult) {
                contactId = defaultAddressBaseResult.getData().getId();
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

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1){
            if (object == null)return;
            rewardLsit = new ArrayList<>();
            rewardLsit = (List<GoodsItemEntity>) object;
            adapter.setNewData(rewardLsit);
        }
        if (code == 2){
            showDialog();
        }
    }

    private void showDialog(){
        if (dialog == null){
            dialog = new ReceiveDialog(ReceiveBDActivity.this, R.style.Dialog, new OnClickListener() {
                @Override
                public void onCancel(View v, Object... objects) {

                }

                @Override
                public void onClick(View v, Object... objects) {
                    String str = (String) objects[0];
                    if (str.equals("go")) {
                        RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MINE_ORDER);
                        ReceiveBDActivity.this.finish();
                    }else {
                        ReceiveBDActivity.this.finish();
                    }
                }

                @Override
                public void dismiss() {

                }
            });
        }
        if (!dialog.isShowing())
            dialog.show();
    }

    private void setRv() {
        adapter = new ReciveBdAdapter(R.layout.me_item_buybd, rewardLsit);
        meReceiveBdRv.setLayoutManager(new LinearLayoutManager(mContext));
        meReceiveBdRv.setAdapter(adapter);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
