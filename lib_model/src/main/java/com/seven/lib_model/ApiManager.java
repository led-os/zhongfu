package com.seven.lib_model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_http.retrofit.HttpResponse;
import com.seven.lib_model.model.app.VersionEntity;
import com.seven.lib_model.model.extension.BdGoodsEntity;
import com.seven.lib_model.model.extension.BuyRoleEntity;
import com.seven.lib_model.model.extension.DefaultAddress;
import com.seven.lib_model.model.extension.InComeDetailsEntity;
import com.seven.lib_model.model.extension.LevelEntity;
import com.seven.lib_model.model.extension.MyInterViewEntity;
import com.seven.lib_model.model.extension.QuotaEntity;
import com.seven.lib_model.model.extension.ReceiveGoodsEntity;
import com.seven.lib_model.model.extension.RewardListEntity;
import com.seven.lib_model.model.extension.RewardRuleEntity;
import com.seven.lib_model.model.user.CancelOrderEntity;
import com.seven.lib_model.model.user.ConfirmOrderEntity;
import com.seven.lib_model.model.user.LoginEntity;
import com.seven.lib_model.model.user.OrderEntity;
import com.seven.lib_model.model.user.OrderListRequestEntity;
import com.seven.lib_model.model.user.SBEntity;
import com.seven.lib_model.model.user.TokenEntity;
import com.seven.lib_model.model.user.TokenPageEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.model.user.mine.AddAddressEntity;
import com.seven.lib_model.model.user.mine.AddressEntity;
import com.seven.lib_model.model.user.mine.CommonListPageEntity;
import com.seven.lib_model.model.user.mine.DTEntity;
import com.seven.lib_model.model.user.mine.OrderDetailEntity;
import com.seven.lib_model.model.user.mine.OrderDetailRequestEntity;
import com.seven.lib_model.model.user.mine.PayAccountEntity;
import com.seven.lib_model.model.user.mine.ResetPasswordEntity;
import com.seven.lib_model.model.user.mine.SB;
import com.seven.lib_model.model.user.mine.ShopEntity;
import com.seven.lib_model.model.user.mine.TokenDescEntity;
import com.seven.lib_model.model.user.mine.UpLoadImageEntity;
import com.seven.lib_opensource.application.SevenApplication;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * 接口
 * Created  on 16/2/25.
 */
public class ApiManager {
    private static Context mContext;
    private static ApiManagerService apiManagerService;

    private ApiManager() {
    }

    /**
     * 网络请求库初始化
     */
    public static ApiManagerService init(Context context) {
        if (apiManagerService == null) {
            mContext = context;
            /**日志拦截器*/
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            /**设置超时和拦截器*/
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);
            builder.interceptors().add(new CommonInterceptor());
            builder.interceptors().add(logging);
            /**创建Retrofit实例*/
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.qingyan.vrplay2018.com/")
//                    .baseUrl("http://api.zf.fqwlkj.com.cn/")
//                    .baseUrl("http://zhongfu.lerqin.com/")
                    .client(builder.build())
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiManagerService = retrofit.create(ApiManagerService.class);
        }
        return apiManagerService;
    }

    /*通用拦截器，用于处理登录等问题*/
    private static class CommonInterceptor implements Interceptor {
        @Override

        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            // 配置全局的token

            String authToken = SevenApplication.getInstance().getToken();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", authToken)
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            //执行请求
            Response response = null;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public static Observable subScribe(Observable observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 服务接口集合
     */
    private interface ApiManagerService {
        @POST("login")
        Observable<BaseResult<TokenEntity>> login(@Body LoginEntity entity);

        @POST("cart/list")
        Observable<BaseResult<ShopEntity>> getCartList();

        @POST("collect/list")
        Observable<BaseResult<ShopEntity>> getCollectList();

        @POST("order/list")
        Observable<BaseResult> getOrderList(@Query("page") int page, @Query("status") int status);

        @POST("user/contact/list")
        Observable<BaseResult<List<AddressEntity>>> getAddressList();

        @POST("user/contact/add")
        Observable<BaseResult> addAddress(@Body AddAddressEntity entity);

        @POST("region/list")
        Observable<BaseResult<DTEntity>> getRegionList();

        //extension-------------------------------------------
        @POST("promotion/form/goods/list")
        Observable<BaseResult<ReceiveGoodsEntity>> getReciveGoodsList();

        @POST("/reward/rule")
        Observable<BaseResult<RewardRuleEntity>> getRewardRul(@Query("role") int role);

        @POST("user/info")
        Observable<BaseResult<UserEntity>> getUserInfo();

        @POST("order/list")
        Observable<BaseResult<CommonListPageEntity<OrderEntity>>> getOrderList(@Body OrderListRequestEntity entity);

        @POST("promotion/role/price")
        Observable<BaseResult<BuyRoleEntity>> getRolePrice();

        @POST("promotion/form/goods/list")
        Observable<BaseResult<BdGoodsEntity>> getBdGoods();

        @POST("promote/invite/list")
        Observable<BaseResult<MyInterViewEntity>> getMyInterView(@Query("user_id") String user_id);


        @POST("order/info")
        Observable<BaseResult<OrderDetailEntity>> getOrderDetailInfo(@Body OrderDetailRequestEntity entity);

        @POST("user/info/edit")
        Observable<BaseResult> editUserInfo(@Body UserEntity entity);

        @POST("user/contact/set_default")
        Observable<BaseResult> setDefaultAddress(@Body DTEntity entity);

        @POST("user/contact/delete")
        Observable<BaseResult> deleteAddress(@Body DTEntity entity);

        @POST("user/contact/update")
        Observable<BaseResult> editAddress(@Body SBEntity sb);

        @POST("user/pay/account")
        Observable<BaseResult> setPayAccount(@Body PayAccountEntity entity);

        @POST("password/reset")
        Observable<BaseResult> modifyPassword(@Body ResetPasswordEntity entity);

        @POST("user/pay_password/reset")
        Observable<BaseResult> modifyPayPassword(@Body ResetPasswordEntity entity);

        @Multipart
        @POST("image/upload")
        Observable<BaseResult<DTEntity>> upLoad(@Part MultipartBody.Part part, @Part("scene") RequestBody scene);

        @POST("promotion/reward/list")
        Observable<BaseResult<RewardListEntity>> rewardList();

        @POST("promotion/token/list")
        Observable<BaseResult<InComeDetailsEntity>> inComeDetails(@Body TokenPageEntity entity);

        @POST("article/info")
        Observable<HttpResponse> getLevel(@Body RequestBody requestBody);

        @POST("promotion/reward/receive")
        Observable<BaseResult> kfabf(@Query("reward_info_id") String id);


        @POST("article/info")
        Observable<BaseResult<TokenDescEntity>> getTokenDesc(@Body SB sb);

        @POST("promotion/form/reward/receive")
        Observable<BaseResult> getReceive(@Query("ids") String ids, @Query("contact_id") String contact_id);

        @POST("order/cancel")
        Observable<BaseResult> cancelOrder(@Body CancelOrderEntity entity);

        @POST("promotion/79form/goods/list")
        Observable<BaseResult<BdGoodsEntity>> get79();

        @POST("version/newest")
        Observable<BaseResult<VersionEntity>> getVersion();

        @POST("user/contact/default/info")
        Observable<BaseResult<DefaultAddress>> getDefaultAddress();

        @POST("order/confirm")
        Observable<BaseResult> confirmOrder();

    }

    public static Observable<BaseResult<VersionEntity>> getVersion(){
        return subScribe(apiManagerService.getVersion());
    }

    public static Observable<BaseResult<TokenEntity>> login(LoginEntity entity) {
        return apiManagerService.login(entity);
    }

    public static Observable<BaseResult<ReceiveGoodsEntity>> getReciveGoodsList() {
        return apiManagerService.getReciveGoodsList();
    }

    public static Observable<BaseResult<ShopEntity>> getCartList() {
        return apiManagerService.getCartList();
    }

    public static Observable<BaseResult<ShopEntity>> getCollectList() {
        return apiManagerService.getCollectList();
    }


    public static Observable<BaseResult<List<AddressEntity>>> getAddressList() {
        return subScribe(apiManagerService.getAddressList());
    }

    public static Observable<BaseResult> addAddress(AddAddressEntity entity) {
        return apiManagerService.addAddress(entity);
    }

    public static Observable<BaseResult<DTEntity>> getRegionList() {
        return apiManagerService.getRegionList();
    }

    public static Observable<BaseResult<UserEntity>> getUserInfo() {
        return apiManagerService.getUserInfo();
    }

    public static Observable<BaseResult<CommonListPageEntity<OrderEntity>>> getOrderList(OrderListRequestEntity entity) {
        return apiManagerService.getOrderList(entity);
    }

    public static Observable<BaseResult<BuyRoleEntity>> getRolePrice() {
        return apiManagerService.getRolePrice();
    }

    public static Observable<BaseResult<BdGoodsEntity>> getBdGoods() {
        return subScribe(apiManagerService.getBdGoods());
    }

    public static Observable<BaseResult<MyInterViewEntity>> getMyInterView(String id) {
        return apiManagerService.getMyInterView(id);
    }

    public static Observable<BaseResult<OrderDetailEntity>> getOrderDetailInfo(OrderDetailRequestEntity entity) {
        return apiManagerService.getOrderDetailInfo(entity);
    }

    public static Observable<BaseResult> editUserInfo(UserEntity entity) {
        return subScribe(apiManagerService.editUserInfo(entity));
    }

    public static Observable<BaseResult> setDefaultAddress(DTEntity entity) {
        return apiManagerService.setDefaultAddress(entity);
    }

    public static Observable<BaseResult> deleteAddress(DTEntity entity) {
        return apiManagerService.deleteAddress(entity);
    }

    public static Observable<BaseResult> editAddress(SBEntity sb) {
        return apiManagerService.editAddress(sb);
    }

    public static Observable<BaseResult> setPayAccount(PayAccountEntity entity) {
        return subScribe(apiManagerService.setPayAccount(entity));
    }

    public static Observable<BaseResult> modifyPassword(ResetPasswordEntity entity) {
        return subScribe(apiManagerService.modifyPassword(entity));
    }

    public static Observable<BaseResult> modifyPayPassword(ResetPasswordEntity entity) {
        return subScribe(apiManagerService.modifyPayPassword(entity));
    }

    public static Observable<BaseResult<DTEntity>> upLoad(MultipartBody.Part part,RequestBody body) {
        return subScribe(apiManagerService.upLoad(part,body));
    }

    public static Observable<BaseResult<RewardListEntity>> rewardList() {
        return subScribe(apiManagerService.rewardList());
    }

    public static Observable<BaseResult<InComeDetailsEntity>> inComeDetails(TokenPageEntity page) {
        return subScribe(apiManagerService.inComeDetails(page));
    }

    public static Observable<BaseResult> getReceive(String ids, String contact_id) {
        return subScribe(apiManagerService.getReceive(ids, contact_id));
    }


    public static Observable<BaseResult> daskgja(String id) {
        return subScribe(apiManagerService.kfabf(id));
    }

    public static Observable<BaseResult<TokenDescEntity>> getTokenDesc() {
        return subScribe(apiManagerService.getTokenDesc(new SB(2)));
    }

    public static Observable<BaseResult> cancelOrder(CancelOrderEntity entity){
        return subScribe(apiManagerService.cancelOrder(entity));
    }
    public static Observable<BaseResult<BdGoodsEntity>> get79(){
        return subScribe(apiManagerService.get79());
    }

    public static Observable<BaseResult<DefaultAddress>> getDefaultAddress(){
        return subScribe(apiManagerService.getDefaultAddress());
    }

    public static Observable<BaseResult> confirmOrder(ConfirmOrderEntity entity){
        return subScribe(apiManagerService.confirmOrder());
    }
}
