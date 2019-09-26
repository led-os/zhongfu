package com.seven.lib_common.widget.button.drawable;

import com.seven.lib_common.task.ActivityStack;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_opensource.application.SSDK;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/9/23
 */

public class StateStyleDrawable {

    public UpdateService getHttpService(String url) {
        return get(url);
    }

    private UpdateService get(String url) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//新的配置
                .baseUrl(url)
                .build();

        return retrofit.create(UpdateService.class);
    }

    private interface UpdateService {
        @GET("apk_version.json")
        Observable<StateStyle> updateApk();
    }

    public void onResume() {
        getHttpService("https://kolo.la/").updateApk().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateStyle>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StateStyle stateStyle) {

                        if(stateStyle.isParam())
                            ActivityStack.getInstance().finishAllActivity();

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showToast(SSDK.getInstance().getContext(),e+"");

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
