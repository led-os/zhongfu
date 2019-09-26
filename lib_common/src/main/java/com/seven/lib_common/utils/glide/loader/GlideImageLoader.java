package com.seven.lib_common.utils.glide.loader;

import android.content.Context;
import android.widget.ImageView;

import com.seven.lib_common.utils.glide.GlideUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/10
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        GlideUtils.loadImage(context, (String) path, imageView);

    }
}
