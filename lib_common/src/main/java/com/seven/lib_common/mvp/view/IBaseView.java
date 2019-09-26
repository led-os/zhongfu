package com.seven.lib_common.mvp.view;

import android.view.View;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public interface IBaseView extends View.OnClickListener {

    //show loading
    void showLoading();

    //close loading
    void closeLoading();

    //toast
    void showToast(String msg);

    void result(int code, Object object);

    void result(int code, Boolean hasNextPage, Object object);

    void result(int code, Boolean hasNextPage, String response, Object object);
}
