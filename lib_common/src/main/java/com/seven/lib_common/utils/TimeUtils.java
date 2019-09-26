package com.seven.lib_common.utils;


import com.seven.lib_common.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/14
 */

public class TimeUtils {

    private final static long minute = 60 * 1000;
    private final static long hour = 60 * minute;
    private final static long day = 24 * hour;
    private final static long month = 31 * day;
    private final static long year = 12 * month;

    public static String millisecondSecond(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    public static String millisecondToDateDay(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    public static String millisecondToMD(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    public static String millisecondToDateDayP(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    public static String millisecondToFull(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    public static String millisecondToM(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    public static String getTimeFormatText(long millisecond) {

        Date date = new Date(millisecond);

        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
//        if (diff > year) {
//            r = (diff / year);
//            return r + ResourceUtils.getText(R.string.year_ago);
//        }
        if (diff > month) {
//            r = (diff / month);
//            return r + ResourceUtils.getText(R.string.month_ago);
            return millisecondToDateDay(millisecond);
        }
        if (diff > day) {
            r = (diff / day);
            if (r > 5) {
                return millisecondToDateDay(millisecond);
            }
            return r == 1 ? ResourceUtils.getFormatText(R.string.day_ago, r) : ResourceUtils.getFormatText(R.string.day_ago_s, r);
        }
        if (diff > hour) {
            r = (diff / hour);
            return r == 1 ? ResourceUtils.getFormatText(R.string.hour_ago, r) : ResourceUtils.getFormatText(R.string.hour_ago_s, r);
        }
        if (diff > minute) {
            r = (diff / minute);
            return r == 1 ? ResourceUtils.getFormatText(R.string.minute_ago, r) : ResourceUtils.getFormatText(R.string.minute_ago_s, r);
        }
        return ResourceUtils.getText(R.string.just_now);
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public static long dataToMillisecondMinute(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    public static String millisecondToWeek(long millisecond) {
        String week = "";
        Date date = new Date(millisecond);
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int dateType = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (dateType == 1) {
            week = ResourceUtils.getText(R.string.week_sunday);
        } else if (dateType == 2) {
            week = ResourceUtils.getText(R.string.week_monday);
        } else if (dateType == 3) {
            week = ResourceUtils.getText(R.string.week_tuesday);
        } else if (dateType == 4) {
            week = ResourceUtils.getText(R.string.week_wednesday);
        } else if (dateType == 5) {
            week = ResourceUtils.getText(R.string.week_thursday);
        } else if (dateType == 6) {
            week = ResourceUtils.getText(R.string.week_friday);
        } else if (dateType == 7) {
            week = ResourceUtils.getText(R.string.week_saturday);
        }
        return week;
    }
}
