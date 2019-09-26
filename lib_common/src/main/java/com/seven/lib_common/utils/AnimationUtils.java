package com.seven.lib_common.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/2
 */

public class AnimationUtils {


    public static void translation(View view, String propertyName, int values, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, values);
        animator.setDuration(duration);
        animator.start();
    }

    public static void rotation(View view, String propertyName, float start, float end, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.setDuration(duration);
        animator.start();
    }

    public static void rotation(View view, String propertyName, float start, float end, int count, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.setDuration(duration);
        animator.setRepeatCount(count);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public static void rotation(View view, String propertyName, float start, float end, long duration,
                                TimeInterpolator interpolator, TimeInterpolator interpolator2) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.setInterpolator(interpolator);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.setInterpolator(interpolator2);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).after(animator2);
        animSet.setDuration(duration);
//        if (listener != null)
//            animSet.addListener(listener);
        animSet.start();
    }

    public static void translation(View view, String propertyName, int from, int to, long duration, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, from, to);
        animator.setDuration(duration);
        if (listener != null)
            animator.addListener(listener);
        animator.start();
    }

    public static void alpha(View view, String propertyName, float from, float to, long duration, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, from, to);
        animator.setDuration(duration);
        if (listener != null)
            animator.addListener(listener);
        animator.start();
    }

    public static void animator(View view, long duration, Animator.AnimatorListener listener, String[] propertyName, float... value) {

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, propertyName[0], value[0], value[1]);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, propertyName[1], value[2], value[3]);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, propertyName[2], value[2], value[3]);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(alpha).with(scaleX).with(scaleY);
        animSet.setDuration(duration);
        if (listener != null)
            animSet.addListener(listener);
        animSet.start();

    }
}
