package com.seven.lib_model.http;

import com.seven.lib_http.retrofit.HttpResponse;
import com.seven.lib_model.HttpSDK;
import com.seven.lib_model.model.user.LoginEntity;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class RequestHelper {

    private static RequestHelper requestHelper;

    private RetrofitService appService;
    private RetrofitService storeService;

    private RequestHelper() {

        appService = getApi(HttpSDK.getInstance().getConfig().getAppUrl());
        storeService = getApi(HttpSDK.getInstance().getConfig().getStoreUrl());

    }

    public static RequestHelper getInstance() {

        if (requestHelper == null)
            requestHelper = new RequestHelper();

        return requestHelper;
    }

    private RetrofitService getApi(String key) {

        return RetrofitHelper.getInstance().getApi(key, new AppInterceptor());
    }

    private <K> Observable<K> addSchedulers(Observable<K> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public RequestBody requestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json"), json);
    }

    //
//    public Observable<UploadEntity> uploadFile(@NonNull File file) {
//        return getApi().upload(file);
//    }
//
    public Observable<HttpResponse> sms(String json) {
        return appService.sms(requestBody(json));
    }

    public Observable<HttpResponse> update() {
        return appService.update();
    }

    public Observable<HttpResponse> payPassword(String json) {
        return appService.payPassword(requestBody(json));
    }

    public Observable<HttpResponse> pushId(String json) {
        return appService.pushId(requestBody(json));
    }

    public Observable<HttpResponse> deletePushId() {
        return appService.deletePushId();
    }

    public Observable<HttpResponse> messageNotRead() {
        return appService.messageNotRead();
    }

    public Observable<HttpResponse> messageRead(String json) {
        return appService.messageRead(requestBody(json));
    }

    public Observable<HttpResponse> messageList(String json) {
        return appService.messageList(requestBody(json));
    }

    public Observable<HttpResponse> upload(String path, String scene) {

        File file = new File(path);

//        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("scene", scene)
//                .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file))
//                .build();

        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // 添加描述
        String descriptionString = scene;
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("imageData"), descriptionString);

        return appService.upload(body, description);
    }

    /* module user */
    public Observable<HttpResponse> register(String json) {
        return appService.register(requestBody(json));
    }

    public Observable<HttpResponse> login(String json) {
        return appService.login(requestBody(json));
    }

    public Observable<HttpResponse> password(String json) {
        return appService.password(requestBody(json));
    }

    /* module home */
    public Observable<HttpResponse> banner() {
        return appService.banner();
    }

    public Observable<HttpResponse> entrance() {
        return appService.entrance();
    }

    public Observable<HttpResponse> commodityRecommendList(String json) {
        return appService.commodityRecommendList(requestBody(json));
    }

    public Observable<HttpResponse> commodityDetails(String json) {
        return appService.commodityDetails(requestBody(json));
    }

    public Observable<HttpResponse> cartTotal() {
        return appService.cartTotal();
    }

    public Observable<HttpResponse> cartAdd(String json) {
        return appService.cartAdd(requestBody(json));
    }

    public Observable<HttpResponse> orderPayment(String json) {
        return appService.orderPayment(requestBody(json));
    }

    public Observable<HttpResponse> orderAdd(String json) {
        return appService.orderAdd(requestBody(json));
    }

    public Observable<HttpResponse> contactDefault() {
        return appService.contactDefault();
    }

    public Observable<HttpResponse> orderPay(String json) {
        return appService.orderPay(requestBody(json));
    }

    public Observable<HttpResponse> collect(String json) {
        return appService.collect(requestBody(json));
    }

    public Observable<HttpResponse> commodityList(String json) {
        return appService.commodityList(requestBody(json));
    }

    /* module model */
    public Observable<HttpResponse> looperMessage() {
        return appService.looperMessage();
    }

    public Observable<HttpResponse> businessList(String json) {
        return appService.businessList(requestBody(json));
    }

    public Observable<HttpResponse> businessTotal() {
        return appService.businessTotal();
    }

    public Observable<HttpResponse> business(String json) {
        return appService.business(requestBody(json));
    }

    public Observable<HttpResponse> businessOrderList(String json) {
        return appService.businessOrderList(requestBody(json));
    }

    public Observable<HttpResponse> businessInfo(String json) {
        return appService.businessInfo(requestBody(json));
    }

    public Observable<HttpResponse> businessOrderInfo(String json) {
        return appService.businessOrderInfo(requestBody(json));
    }

    public Observable<HttpResponse> businessAccept(String json) {
        return appService.businessAccept(requestBody(json));
    }

    public Observable<HttpResponse> businessProof(String json) {
        return appService.businessProof(requestBody(json));
    }

    public Observable<HttpResponse> businessConfirm(String json) {
        return appService.businessConfirm(requestBody(json));
    }

    public Observable<HttpResponse> businessCancel(String json) {
        return appService.businessCancel(requestBody(json));
    }

    //extension
    public Observable<HttpResponse> rewardrule(String json) {
        return appService.rewardrule(requestBody(json));
    }

    public Observable<HttpResponse> inviteList(String json) {
        return appService.inviteList(requestBody(json));
    }

    public Observable<HttpResponse> inComeDetails(String json) {
        return appService.inComeDetails(requestBody(json));
    }

    public Observable<HttpResponse> incomeCommodityList() {
        return appService.incomeCommodityList();
    }
    public Observable<HttpResponse> level(String json){return appService.getLevel(requestBody(json));}

    public Observable<HttpResponse> binding(String json){return appService.binding(requestBody(json));}

    public Observable<HttpResponse> getBindList(String json){return appService.getBindList(requestBody(json));}

    public Observable<HttpResponse> getReceive(String json){return appService.getReceive(requestBody(json));}

    public Observable<HttpResponse> rewardLsit(String json){return appService.rewardList(requestBody(json));}

    public Observable<HttpResponse> rewardInfo(String json){return appService.rewardInfo(requestBody(json));}

    public Observable<HttpResponse> getReward(String json){return appService.getReward(requestBody(json));}

    public Observable<HttpResponse> getQuota(String json){return  appService.getQuota(requestBody(json));}

    public Observable<HttpResponse> getOrder(String json){return appService.getOrder(requestBody(json));}

    public Observable<HttpResponse> buyRole(String json){return appService.buyRole(requestBody(json));}

    public Observable<HttpResponse> editAddress(String json){return appService.editAddress(requestBody(json));}

    public Observable<HttpResponse> getUserInfo(){return appService.getUserInfo();}

    public Observable<HttpResponse> confirmOrder(String json){return appService.confirmOrder(requestBody(json));}
}
