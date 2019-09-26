package com.seven.lib_common.listener;

import android.os.Bundle;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public interface LifeCycleListener {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
