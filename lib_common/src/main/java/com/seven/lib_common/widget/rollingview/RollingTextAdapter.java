package com.seven.lib_common.widget.rollingview;

import android.content.Context;
import android.view.View;

public abstract class RollingTextAdapter {
    public abstract int getCount();
    public abstract View getView(Context context, View contentView, int position);
}
