package com.seven.lib_common.stextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.seven.lib_common.R;

import static com.seven.lib_common.stextview.TypefaceHelper.typeface;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/7
 */

public class TypeFaceView extends AppCompatTextView {

    public TypeFaceView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TypeFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TypeFaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.typeText);
        int style = attributes.getInteger(R.styleable.typeText_SFontStyle, 0);
        boolean firstCap = attributes.getBoolean(R.styleable.typeText_SFirstCap, false);
        int maxLength = attributes.getInteger(R.styleable.typeText_SMaxLength, 0);

        attributes.recycle();
        typeface(this, FontStyle.getInstance().getTypeFace(style));

        if (firstCap)
            toUpperCaseFirstOne(this.getText().toString());

        if (maxLength > 0)
            setFilter(maxLength);
    }

    public void setStyle(String fontStyle) {
        int style = 0;

        if (fontStyle.equals("primary_s"))
            style = 1;
        else if (fontStyle.equals("bold_s"))
            style = 2;
        else if (fontStyle.equals("light_s"))
            style = 3;
        else if (fontStyle.equals("medium_s"))
            style = 4;
        else if (fontStyle.equals("regular_s"))
            style = 5;

        typeface(this, FontStyle.getInstance().getTypeFace(style));
    }

    public String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    public void setFilter(int maxLength) {
        InputFilter[] filters = {new SInputFilter(maxLength)};
        this.setFilters(filters);
    }

}
