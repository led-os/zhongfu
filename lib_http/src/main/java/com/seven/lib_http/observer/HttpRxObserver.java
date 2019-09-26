package com.seven.lib_http.observer;


import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.seven.lib_http.exception.ApiException;
import com.seven.lib_http.exception.ExceptionEngine;
import com.seven.lib_http.retrofit.HttpRequestListener;
import com.seven.lib_http.retrofit.RxActionManagerImpl;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * apply Retrofit Observer
 * 1.Override onSubscribe,add request id
 * 2.Override onError,Encapsulates the error/exception handling and removes the request
 * 3.Override onNext,Removal Pending
 * 4.Override cancel,Cancel Pending
 *
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public abstract class HttpRxObserver<T> implements Observer<T>, HttpRequestListener {

    private String mTag;//request tag

    public HttpRxObserver() {
    }

    public HttpRxObserver(String tag) {
        this.mTag = tag;
    }

    @Override
    public void onError(Throwable e) {
        RxActionManagerImpl.getInstance().remove(mTag);
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull T t) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().remove(mTag);
        }
        onSuccess(t);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().add(mTag, d);
        }
        onStart(d);
    }

    @Override
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            Logger.i("-------------");
            RxActionManagerImpl.getInstance().cancel(mTag);
        }
    }

    /**
     * Whether it has been processed or not
     */
    public boolean isDisposed() {
        if (TextUtils.isEmpty(mTag)) {
            return true;
        }
        return RxActionManagerImpl.getInstance().isDisposed(mTag);
    }

    protected abstract void onStart(Disposable d);

    /**
     * Error/abnormal callbacks
     *
     */
    protected abstract void onError(ApiException e);

    /**
     * Success callback
     *
     */
    protected abstract void onSuccess(T response);

}
