package com.seven.lib_common.listener;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/3/23
 */

public interface UploadProgressListener {

    void onProgress(double percentage, String uploadKey);

    void onVSucceed(String response, String uploadKey);

    void onVFailure(String uploadKey);
}
