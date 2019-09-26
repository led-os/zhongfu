package com.seven.lib_http.retrofit;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.seven.lib_opensource.application.SSDK;
import com.seven.lib_opensource.application.SevenApplication;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public abstract class RetrofitApi<T> {
    private T api;
    private Class<T> clazz;
    private Converter.Factory converterFactory;
    private CallAdapter.Factory rxJavaCallAdapterFactory;
    public static final int CONNECT_TIME_OUT = 60;
    public static final int READ_TIME_OUT = 60;
    public static final int WRITE_TIME_OUT = 60;

    private T storeApi;

    @SuppressWarnings("unchecked")
    protected RetrofitApi() {
        converterFactory = GsonConverterFactory.create();
        rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private OkHttpClient okHttpClient(Interceptor interceptor) {
        //open Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (SSDK.getInstance().isLoggerDebug())
                    Logger.i("okHttp:" + message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    public T getApi(String key, Interceptor interceptor) {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient(interceptor))
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            api = retrofit.create(clazz);
        }
        if (storeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient(interceptor))
                    .baseUrl(getStoreUrl())
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            storeApi = retrofit.create(clazz);
        }
        return key.equals(getDefaultKey()) ? api : storeApi;
    }

    protected abstract String getBaseUrl();

    protected abstract Context getContext();

    protected abstract String getStoreUrl();

    protected abstract String getDefaultKey();

}
