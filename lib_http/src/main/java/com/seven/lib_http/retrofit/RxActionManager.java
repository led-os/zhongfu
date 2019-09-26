package com.seven.lib_http.retrofit;

import io.reactivex.disposables.Disposable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public interface RxActionManager<T> {
    /**
     * add
     *
     * @param tag
     * @param disposable
     */
    void add(T tag, Disposable disposable);

    /**
     * remove
     *
     * @param tag
     */
    void remove(T tag);

    /**
     * cancel
     *
     * @param tag
     */
    void cancel(T tag);

}