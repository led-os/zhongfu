package com.seven.lib_common.widget.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 */
public class LoadingVideoView extends VideoView {
    public LoadingVideoView(Context context) {
        super(context);
    }

    public LoadingVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else{

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
