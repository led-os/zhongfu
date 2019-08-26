package com.seven.module_user.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.model.user.RegisterEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.user.UserActivityPresenterNew;
import com.seven.lib_model.user.UserPresenter;
import com.seven.lib_opensource.event.Event;
import com.seven.lib_opensource.event.MessageEvent;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.Variable;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.lib_router.router.RouterUtils;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.activity.login.LoginActivity;
import com.seven.module_user.ui.fragment.order.UserOrderListActivity;
import com.seven.module_user.ui.fragment.setting.UserSettingActivity;
import com.seven.module_user.ui.fragment.token.UserTokenActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/26
 */
@Route(path = RouterPath.FRAGMENT_USER)
public class UserFragment extends BaseFragment {
    @BindView(R2.id.wait_pay)
    TextView waitPay;
    @BindView(R2.id.wait_send)
    TextView waitSend;
    @BindView(R2.id.shop_received)
    TextView shopReceived;
    @BindView(R2.id.user_name)
    TextView userName;
    @BindView(R2.id.user_photo)
    ImageView userPhoto;
    @BindView(R2.id.shop_cart)
    TextView shop_cart;
    @BindView(R2.id.vip_lv)
    ImageView vipLv;
    @BindView(R2.id.read_iv)
    ImageView readIv;
    @BindView(R2.id.message_layout)
    LinearLayout messageLayout;
    UserPresenter presenter;
    UserEntity userEntity;

    @Override
    public int getContentViewId() {
        return R.layout.mu_fragment_user;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        // getUserInfo();
        EventBus.getDefault().register(this);
       // presenter = new UserPresenter(this,this);
    }


    private void getUserInfo() {
       // presenter.getUserInfo(1);
        ApiManager.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserEntity> userEntityBaseResult) {
                        Gson gson = new Gson();
                        String userString = gson.toJson(userEntityBaseResult.getData());
                        if (userString != null && !userString.equals("null")) {
                            SharedData.getInstance().setUserInfo(userString);
                            setData(userEntityBaseResult.getData());
                            Variable.getInstance().setUserId(userEntityBaseResult.getData().getId());
                            Variable.getInstance().setTokenCount(TextUtils.isEmpty(userEntityBaseResult.getData().getToken_number_total()) ? 0 : Double.parseDouble(userEntityBaseResult.getData().getToken_number_total()));
                            EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.USER_DATA_CHANGE, "change"));
                        } else {
                            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_LOGIN);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("xxxxxxH",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void setData(UserEntity data) {
        userName.setText(data.getUsername() != null && !data.getUsername().equals("") ? data.getUsername() : "昵称暂未设置");
        Drawable drawable = getResources().getDrawable(R.drawable.lv_0);
        switch (data.getRole()) {
            case 0:
                drawable = getResources().getDrawable(R.drawable.lv_0);
                break;
            case 1:
                drawable = getResources().getDrawable(R.drawable.lv_1);
                break;
            case 2:
                drawable = getResources().getDrawable(R.drawable.lv_2);
                break;
            case 3:
                drawable = getResources().getDrawable(R.drawable.lv_3);
                break;
            case 4:
                drawable = getResources().getDrawable(R.drawable.lv_4);
                break;
            default:
        }
        vipLv.setImageDrawable(drawable);
        vipLv.setVisibility(View.VISIBLE);
        GlideUtils.loadCircleImage(getActivity(), data.getAvatar(), userPhoto);
    }

    @Override
    public void onClick(View v) {
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

    @OnClick(R2.id.shop_cart)
    void goShopCar() {
        if (!SharedData.getInstance().getToken().isEmpty())
            RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_SHOPPING_CART);
        else startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R2.id.user_info_layout)
    void setUserInfo() {
        if (!SharedData.getInstance().getToken().isEmpty())
            startActivity(new Intent(getActivity(), EditUserInfoActivity.class));
        else startActivity(new Intent(getActivity(), LoginActivity.class));
        //RouterUtils.getInstance().routerWithFade(RouterPath.FRAGMENT_USER_EDIT_UESR_INFO, SSDK.getInstance().getContext());
    }

    @OnClick(R2.id.my_setting)
    void setting() {
        if (!SharedData.getInstance().getToken().isEmpty())
            startActivity(new Intent(getActivity(), UserSettingActivity.class));
        else startActivity(new Intent(getActivity(), LoginActivity.class));
        //RouterUtils.getInstance().routerWithFade(RouterPath.FRAGMENT_USER_EDIT_UESR_INFO, SSDK.getInstance().getContext());
    }

    @OnClick(R2.id.my_shoucang)
    void storeUp() {
        if (!SharedData.getInstance().getToken().isEmpty())
            startActivity(new Intent(getActivity(), CollectionActivity.class));
        else startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R2.id.my_address)
    void address() {
        if (!SharedData.getInstance().getToken().isEmpty())
            startActivity(new Intent(getActivity(), UserAddressActivity.class));
        else startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R2.id.logout)
    void logout() {
        EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.LOGOUT));
        SharedData.getInstance().setUserInfo("");
        SharedData.getInstance().setToken("");
        Variable.getInstance().setUserId(0);
        Variable.getInstance().setTokenCount(0);
        Variable.getInstance().setToken("");
        userName.setText("请登录");
        vipLv.setVisibility(View.GONE);
        GlideUtils.loadCircleImage(getActivity(), "", userPhoto);
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R2.id.my_clear_cache)
    void clearCache() {
        ToastUtils.showToast(getActivity(), "清除成功");
    }

    @OnClick(R2.id.all_order)
    void allOrder() {
        if (!SharedData.getInstance().getToken().isEmpty())
            startActivity(new Intent(getActivity(), UserOrderListActivity.class));
        else startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R2.id.my_token)
    void token() {
        if (!SharedData.getInstance().getToken().isEmpty())
            startActivity(new Intent(getActivity(), UserTokenActivity.class));
        else startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick({R2.id.wait_pay, R2.id.wait_send, R2.id.shop_received})
    void shop(View view) {
        if (!SharedData.getInstance().getToken().isEmpty()) {
            Intent intent = new Intent(getActivity(), UserOrderListActivity.class);
            if (view == waitPay) {
                intent.putExtra("type", 1);
            } else if (view == waitSend) {
                intent.putExtra("type", 2);
            } else if (view == shopReceived) {
                intent.putExtra("type", 3);
            }
            startActivity(intent);
        } else startActivity(new Intent(getActivity(), LoginActivity.class));

    }

    @OnClick(R2.id.message_layout)
    void messageLayout(){
        RouterUtils.getInstance().routerNormal(RouterPath.ACTIVITY_MESSAGE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SharedData.getInstance().getToken().isEmpty()){
            getUserInfo();
            readIv.setVisibility(Variable.getInstance().isRead()?View.VISIBLE:View.GONE);
            EventBus.getDefault().post(new MessageEvent(Constants.EventConfig.MESSAGE_READ,""));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !SharedData.getInstance().getToken().isEmpty()) {
            getUserInfo();
            readIv.setVisibility(Variable.getInstance().isRead()?View.VISIBLE:View.GONE);
            EventBus.getDefault().post(new MessageEvent(Constants.EventConfig.MESSAGE_READ,""));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getWhat()) {

            case Constants.EventConfig.LOGIN:
            case Constants.EventConfig.REGISTER:
                getUserInfo();
                RegisterEntity registerEntity = (RegisterEntity) ((ObjectsEvent) event).getObjects()[0];

                if (registerEntity == null) return;

                break;

            case Constants.EventConfig.MESSAGE_READ_POINT:
                readIv.setVisibility(Variable.getInstance().isRead()?View.VISIBLE:View.GONE);
                break;

        }
    }

    private void git(){}
    private void git2(){}
}
