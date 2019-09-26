package com.seven.lib_common.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.seven.lib_common.R;
import com.seven.lib_common.utils.ScreenUtils;

import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/3/14
 */

public class GlideUtils {

    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, true);
    }

    public static void loadImageB(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, false);
    }

    public static void loadImage(Context context, String url, ImageView imageView, boolean isWhite) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(isWhite ? R.drawable.image_default : R.drawable.common_shade)
                .error(isWhite ? R.drawable.image_default : R.drawable.common_shade)
                .into(imageView);
    }

    public static void loadRoundImage(Context context, String url, int width, int height, int round, ImageView imageView) {
        loadRoundImage(context, url, width, height, round, imageView, true);
    }

    public static void loadRoundImageB(Context context, String url, int width, int height, int round, ImageView imageView) {
        loadRoundImage(context, url, width, height, round, imageView, false);
    }

    public static void loadRoundImage(Context context, String url, int width, int height, int round, ImageView imageView, boolean isWhite) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(isWhite ? R.drawable.image_default : R.drawable.common_shade)
                .error(isWhite ? R.drawable.image_default : R.drawable.common_shade)
                .override(width, height)
                .transform(new CenterCrop(), new RoundedCorners(round))
                .into(imageView);

    }

    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);

    }

    /*---*/
    public static void loadAvatarBImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.default_rect)
                .error(R.drawable.default_rect)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);

    }

    public static void loadImageBitmapB(Context context, Bitmap bitmap, ImageView imageView) {
        loadImageBitmap(context, bitmap, imageView, false);
    }

    public static void loadImageBitmap(Context context, Bitmap bitmap, ImageView imageView, boolean isWhite) {
        Glide.with(context)
                .load(bitmap)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(isWhite ? R.drawable.image_default_max : R.drawable.common_shade)
                .error(isWhite ? R.drawable.image_default_max : R.drawable.common_shade)
                .into(imageView);

    }

    public static void loadImageWhite(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.image_default_white)
                .error(R.drawable.image_default_white)
                .into(imageView);
    }
}
