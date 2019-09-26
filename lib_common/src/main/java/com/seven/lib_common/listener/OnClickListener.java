package com.seven.lib_common.listener;

import android.view.View;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/24
 */

public interface OnClickListener {

    void onCancel(View v, Object... objects);

    void onClick(View v, Object... objects);

    void dismiss();
}
