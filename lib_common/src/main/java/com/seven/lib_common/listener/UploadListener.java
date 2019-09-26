package com.seven.lib_common.listener;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/12/13
 */

public interface UploadListener {

    void onSucceed(String response, String uploadKey);

    void onFailure(String uploadKey);

}
