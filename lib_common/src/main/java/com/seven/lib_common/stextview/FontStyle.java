package com.seven.lib_common.stextview;

import android.graphics.Typeface;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/7
 */

public class FontStyle {

    private TypefaceCollection mSystemDefaultTypeface;
    private TypefaceCollection mPrimaryTypeface;
    private TypefaceCollection mBoldTypeface;
    private TypefaceCollection mLightTypeface;
    private TypefaceCollection mMediumTypeface;
    private TypefaceCollection mRegularTypeface;

    public static FontStyle fontStyle;

    private FontStyle() {
        typeFace();
    }

    public static FontStyle getInstance() {

        if (fontStyle == null) {

            synchronized (FontStyle.class) {

                fontStyle = new FontStyle();

            }

        }

        return fontStyle;
    }

    private void typeFace() {

        TypefaceHelper.init(new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(STextViewSDK.getInstance().context.getAssets(), "fonts/Regular.ttf"))
                .create());

        mSystemDefaultTypeface = TypefaceCollection.createSystemDefault();

        // Multiple custom typefaces support
        mPrimaryTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(STextViewSDK.getInstance().context.getAssets(), "fonts/Primary.ttf"))
                .create();

        // Multiple custom typefaces support
        mBoldTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(STextViewSDK.getInstance().context.getAssets(), "fonts/Bold.ttf"))
                .create();

        // Multiple custom typefaces support
        mLightTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(STextViewSDK.getInstance().context.getAssets(), "fonts/Light.ttf"))
                .create();

        // Multiple custom typefaces support
        mMediumTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(STextViewSDK.getInstance().context.getAssets(), "fonts/Medium.ttf"))
                .create();

        // Multiple custom typefaces support
        mRegularTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(STextViewSDK.getInstance().context.getAssets(), "fonts/Regular.ttf"))
                .create();

    }

    public TypefaceCollection getSystemDefaultTypeface() {
        return mSystemDefaultTypeface;
    }

    public TypefaceCollection getDINKoloTypeface() {
        return mPrimaryTypeface;
    }

    public TypefaceCollection getUbuntuBTypeface() {
        return mBoldTypeface;
    }

    public TypefaceCollection getUbuntuLTypeface() {
        return mLightTypeface;
    }

    public TypefaceCollection getUbuntuMTypeface() {
        return mMediumTypeface;
    }

    public TypefaceCollection getUbuntuRTypeface() {
        return mRegularTypeface;
    }

    public TypefaceCollection getTypeFace(int type){

        switch (type){

            case 0:
                return mSystemDefaultTypeface;
            case 1:
                return mPrimaryTypeface;
            case 2:
                return mBoldTypeface;
            case 3:
                return mLightTypeface;
            case 4:
                return mMediumTypeface;
            case 5:
                return mRegularTypeface;
        }

        return mSystemDefaultTypeface;
    }

}
