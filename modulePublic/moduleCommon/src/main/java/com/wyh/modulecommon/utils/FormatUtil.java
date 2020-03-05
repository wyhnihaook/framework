package com.wyh.modulecommon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by js on 2016/5/22.
 */
public class FormatUtil {

    private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static SimpleDateFormat format3 = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static SimpleDateFormat format4 = new SimpleDateFormat("MM/dd", Locale.CHINA);
    public static SimpleDateFormat format5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat format6 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
    private static SimpleDateFormat format7 = new SimpleDateFormat("MM月dd日", Locale.CHINA);
    private static SimpleDateFormat format8 = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    private static SimpleDateFormat format9 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat format10 = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
    private static SimpleDateFormat format11 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.getDefault());

    /**
     * 格式化时间 yyyy:MM:dd hh:mm
     * @param time
     * @return
     */
    public static String formatDateTimeYMDHI(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format1.format(calendar.getTime());
    }

    /**
     * 格式化时间 yyyy:MM:dd
     * @param time
     * @return
     */
    public static String formatDateTimeYMD(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format2.format(calendar.getTime());
    }

    /**
     * 格式化时间 hh:mm
     * @param time
     * @return
     */
    public static String formatDateTimeHI(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format3.format(calendar.getTime());
    }

    /**
     * 格式化时间 mm/dd
     * @param time
     * @return
     */
    public static String formatDateTimeMD(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format4.format(calendar.getTime());
    }

    /**
     * 格式化时间 yyyy:mm:dd hh:ii:ss
     * @param time
     * @return
     */
    public static String formatDateTimeYMDHIS(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format5.format(calendar.getTime());
    }

    /**
     * 解析日期时间为时间戳
     * @param dateStr
     * @return
     */
    public static long formatDateTime(String dateStr) {
        long time = 0;
        try {
            time = format11.parse(dateStr).getTime();
            return time;
        } catch (Exception e) {
        }
        try {
            time = format6.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 解析日期时间为时间戳
     * @param dateStr
     * @return
     */
    public static long formatDateTime2(String dateStr) {
        long time = 0;
        try {
            time = format5.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 格式化时间 mm月dd日
     * @param time
     * @return
     */
    public static String formatDateTimeMD2(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format7.format(calendar.getTime());
    }

    /**
     * 格式化时间 yyyyMMdd
     * @param time
     * @return
     */
    public static String formatDateTimeYMD2(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format8.format(calendar.getTime());
    }

    /**
     * 格式化时间 yyyy/mm/dd hh:ii:ss
     * @param time
     * @return
     */
    public static String formatDateTimeYMDHIS2(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format9.format(calendar.getTime());
    }

    /**
     * 格式化时间 yyyy:MM:dd hh:mm
     * @param time
     * @return
     */
    public static String formatDateTimeYMD3(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format10.format(calendar.getTime());
    }

}
