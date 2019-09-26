package com.seven.lib_http.observer;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.seven.lib_http.function.HttpResultFunction;
import com.seven.lib_http.function.ServerResultCommonFunction;
import com.seven.lib_http.function.ServerResultFunction;
import com.seven.lib_http.retrofit.HttpResponse;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public class HttpRxObservable {

    /**
     * No management life cycle, easy to cause memory overflow
     */
    public static Observable getObservable(Observable<HttpResponse> apiObservable) {
        // showLog(request);
        Observable observable = apiObservable
                .map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }


    /**
     * introduction LifecycleProvider automatically manage the life cycle to avoid memory leaks
     * need extends RxActivity.../RxFragment...
     */
    public static Observable getObservable(Observable<HttpResponse> apiObservable, LifecycleProvider lifecycle) {
        //showLog(request);
        Observable observable;

        if (lifecycle != null) {
            //automatic management with life cycle.  eg:onCreate(start)->onStop(end)
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindToLifecycle())//It needs to be added at this location
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }

    /**
     * introduction LifecycleProvider<ActivityEvent> manual management of life cycle to avoid memory overflow
     * need extends RxActivity,RxAppCompatActivity,RxFragmentActivity
     */
    public static Observable getObservable(Observable<HttpResponse> apiObservable, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event) {
        // showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //Manual management removes the listening life cycle .eg:ActivityEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindUntilEvent(event))//It needs to be added at this location
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }


    /**
     * introduction LifecycleProvider<FragmentEvent> manual management of life cycle to avoid memory overflow
     * need extends RxFragment,RxDialogFragment
     *
     */
    public static Observable getObservable(Observable<HttpResponse> apiObservable, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event) {
        //  showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //Manual management removes the listening life cycle .eg:FragmentEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindUntilEvent(event))//It needs to be added at this location
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }

    /**
     * introduction LifecycleProvider automatically manage the life cycle to avoid memory leaks
     * need extends RxActivity.../RxFragment...
     */
    public static Observable getObservableCommon(Observable<HttpResponse> apiObservable) {
        // showLog(request);
        Observable observable = apiObservable
                .map(new ServerResultCommonFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private static void showLog(Map<String, Object> request) {
        if (request == null || request.size() == 0) {
            Logger.e("[http request]:");
        }
        Logger.e("[http request]:" + new Gson().toJson(request));
    }

}
