package com.seven.lib_common.widget.button;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.seven.lib_common.R;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.widget.button.drawable.TouchEffectDrawable;
import com.seven.lib_common.widget.button.effect.EffectFactory;
import com.seven.lib_common.widget.factory.ClipFilletFactory;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/31
 */

public class MaterialButton extends AppCompatButton implements TouchEffectDrawable.PerformClicker,
        TouchEffectDrawable.PerformLongClicker{

    private TouchEffectDrawable mTouchDrawable;


    public MaterialButton(Context context) {
        this(context, null);
    }

    public MaterialButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.gButtonStyle);
    }

    public MaterialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.style.Genius_Widget_Button);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public MaterialButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(attrs, defStyleAttr, defStyleRes);
//    }
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MaterialButton.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MaterialButton.class.getName());
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null)
            return;

        final Context context = getContext();
        final Resources resources = getResources();

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.Button, defStyleAttr, defStyleRes);

        String fontFile = a.getString(R.styleable.Button_gFont);
        int touchEffect = a.getInt(R.styleable.Button_gTouchEffect, EffectFactory.TOUCH_EFFECT_NONE);
        int touchColor = a.getColor(R.styleable.Button_gTouchColor, Ui.TOUCH_PRESS_COLOR);

        // Load clip touch corner radius
        int touchRadius = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadius, ScreenUtils.dip2px(context,4));
        int touchRadiusTL = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusTL, touchRadius);
        int touchRadiusTR = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusTR, touchRadius);
        int touchRadiusBL = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusBL, touchRadius);
        int touchRadiusBR = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusBR, touchRadius);
        float[] radius = new float[]{touchRadiusTL, touchRadiusTL, touchRadiusTR, touchRadiusTR,
                touchRadiusBR, touchRadiusBR, touchRadiusBL, touchRadiusBL};
        ClipFilletFactory touchFactory = new ClipFilletFactory(radius);
        float touchDurationRate = a.getFloat(R.styleable.Button_gTouchDurationRate, 1.0f);

        // Load intercept event type, the default is intercept click event
        int interceptEvent = a.getInt(R.styleable.Button_gInterceptEvent, 0x0001);

        a.recycle();

        // Initial  TouchEffectDrawable
        if (touchEffect != 0) {
            TouchEffectDrawable touchEffectDrawable = new TouchEffectDrawable();
            touchEffectDrawable.setColor(touchColor);
            touchEffectDrawable.setEffect(EffectFactory.creator(touchEffect));
            touchEffectDrawable.setEnterDuration(touchDurationRate);
            touchEffectDrawable.setExitDuration(touchDurationRate);
            touchEffectDrawable.setInterceptEvent(interceptEvent);
            // Check for IDE preview render to set Touch factory
            if (!this.isInEditMode()) {
                touchEffectDrawable.setClipFactory(touchFactory);
            }
            setTouchDrawable(touchEffectDrawable);
        }

        // Check for IDE preview render to set Font
        if (!this.isInEditMode()) {
            if (fontFile != null && fontFile.length() > 0) {
                Typeface typeface = Ui.getFont(getContext(), fontFile);
                if (typeface != null) setTypeface(typeface);
            }
        }
    }
    /**
     * In this, you can set TouchEffectDrawable,
     * to init TouchEffectDrawable.
     * <p>
     * If you not need touch effect,
     * you should set NULL.
     * <p>
     * But, if need it,
     *
     * @param touchDrawable TouchEffectDrawable
     */
    public void setTouchDrawable(TouchEffectDrawable touchDrawable) {
        if (mTouchDrawable != touchDrawable) {
            if (mTouchDrawable != null) {
                mTouchDrawable.setCallback(null);
            }
            if (touchDrawable != null) {
                touchDrawable.setCallback(this);
                // We must set layer type is View.LAYER_TYPE_SOFTWARE,
                // to support Canvas.clipPath()
                // on Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                if (getLayerType() != View.LAYER_TYPE_SOFTWARE)
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            mTouchDrawable = touchDrawable;
        }
    }


    @Override
    public void setLayerType(int layerType, Paint paint) {
        // In this, to support Canvas.clipPath(),
        // must set layerType is View.LAYER_TYPE_SOFTWARE
        // on your need touch draw
        if (mTouchDrawable != null)
            layerType = View.LAYER_TYPE_SOFTWARE;
        super.setLayerType(layerType, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TouchEffectDrawable drawable = mTouchDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected boolean verifyDrawable(Drawable who) {
        Drawable drawable = mTouchDrawable;
        return (drawable != null && who == drawable) || super.verifyDrawable(who);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        final boolean ret = super.onTouchEvent(event);

        // send to touch drawable
        final TouchEffectDrawable d = mTouchDrawable;
        if (ret && d != null && isEnabled()) {
            d.onTouch(event);
        }

        return ret;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        final TouchEffectDrawable d = mTouchDrawable;
        if (d != null) {
            d.draw(canvas);
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean performClick() {
        final TouchEffectDrawable d = mTouchDrawable;

        if (d != null) {
            return d.performClick(this) && super.performClick();
        } else
            return super.performClick();
    }

    @Override
    public boolean performLongClick() {
        final TouchEffectDrawable d = mTouchDrawable;

        if (d != null) {
            return d.performLongClick(this) && super.performLongClick();
        } else
            return super.performLongClick();
    }

    @Override
    public void postPerformClick() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                performClick();
            }
        };

        if (!this.post(runnable)) {
            performClick();
        }
    }

    @Override
    public void postPerformLongClick() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                performLongClick();
            }
        };

        if (!this.post(runnable)) {
            performLongClick();
        }
    }
}
