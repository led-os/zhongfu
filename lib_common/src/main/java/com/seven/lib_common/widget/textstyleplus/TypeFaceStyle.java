package com.seven.lib_common.widget.textstyleplus;

import android.graphics.Typeface;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Typeface.NORMAL, Typeface.BOLD, Typeface.BOLD_ITALIC, Typeface.ITALIC})
@Retention(RetentionPolicy.SOURCE)
@interface TypeFaceStyle {
}