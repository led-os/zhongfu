package com.seven.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import java.util.Locale;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/25
 */

public class LocaleUtils {

    public static final Locale LOCALE_CHINESE = Locale.CHINESE;
    public static final Locale LOCALE_ENGLISH = Locale.ENGLISH;
    /**
     * Russian
     */
    public static final Locale LOCALE_RUSSIAN = new Locale("ru");
    /**
     * save filename of SharedPreferences
     */
    private static final String LOCALE_FILE = "LOCALE_FILE";
    /**
     * save key of Locale
     */
    private static final String LOCALE_KEY = "LOCALE_KEY";

    /**
     * the user locale of SharedPreferences
     *
     * @param context
     * @return
     */
    public static Locale getUserLocale(Context context) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_FILE, Context.MODE_PRIVATE);
        String localeJson = spLocale.getString(LOCALE_KEY, "");
        return jsonToLocale(localeJson);
    }

    /**
     * get current locale
     *
     * @param context
     * @return
     */
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0 Multilingual Settings get the top language
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    public static String getLanguage() {
        return Locale.getDefault().toString();
    }

    /**
     * Save locale of the user Settings
     *
     * @param context    Context
     * @param userLocale Locale
     */
    public static void saveUserLocale(Context context, Locale userLocale) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = spLocale.edit();
        String localeJson = localeToJson(userLocale);
        spEditor.putString(LOCALE_KEY, localeJson);
        spEditor.apply();
    }

    /**
     * Locale convert json
     *
     * @param userLocale UserLocale
     * @return json String
     */
    private static String localeToJson(Locale userLocale) {
        Gson gson = new Gson();
        return gson.toJson(userLocale);
    }

    /**
     * json convert Locale
     *
     * @param localeJson LocaleJson
     * @return Locale
     */
    private static Locale jsonToLocale(String localeJson) {
        Gson gson = new Gson();
        return gson.fromJson(localeJson, Locale.class);
    }

    /**
     * update Locale
     *
     * @param context    Context
     * @param userLocale New User Locale
     */
    public static void updateLocale(Context context, Locale userLocale) {
        if (needUpdateLocale(context, userLocale)) {
            Configuration configuration = context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(userLocale);
            } else {
                configuration.locale = userLocale;
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(configuration, displayMetrics);
            saveUserLocale(context, userLocale);
        }
    }

    /**
     * Make sure you don't need an update
     *
     * @param context    Context
     * @param userLocale New User Locale
     * @return true / false
     */
    public static boolean needUpdateLocale(Context context, Locale userLocale) {
        return userLocale != null && !getCurrentLocale(context).getLanguage().startsWith(userLocale.getLanguage());
    }

}
