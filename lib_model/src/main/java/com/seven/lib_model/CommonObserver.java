package com.seven.lib_model;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xxxxxxH on 2019/4/26.
 */

public class CommonObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
