package com.example.common_base.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    // ==格式到年==
    /**
     * 日期格式，年份，例如：2004，2008
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";


    // ==格式到年月 ==
    /**
     * 日期格式，年份和月份，例如：200707，200808
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 日期格式，年份和月份，例如：200707，2008-08
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";


    // ==格式到年月日==
    /**
     * 日期格式，年月日，例如：050630，080808
     */
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08
     */
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

    /**
     * 日期格式，年月日，例如：20050630，20080808
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式，年月日，例如：2016.10.05
     */
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

    /**
     * 日期格式，年月日，例如：2016年10月05日
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDD = "yyyy年MM月dd日";


    // ==格式到年月日 时分 ==

    /**
     * 日期格式，年月日时分，例如：200506301210，200808081210
     */
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

    /**
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

    /**
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式，年月日时分，例如：2000.12.30 12:00
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_2 = "yyyy.MM.dd HH:mm";


    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    /**
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";


    // ==格式到年月日 时分秒 毫秒==
    /**
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123，20080808200808456
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";


    // ==特殊格式==
    /**
     * 日期格式，月日时分，例如：10-05 12:00
     */
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";

    private DateUtils() {
    }

    /**
     * 输入日期获取对应的日期
     *
     * @param sdfTo    获取的日期类型，Date对象，long 毫秒值，String 字符串日期
     * @param defValue 输入要转换的日期，Date对象，long 毫秒值，String 字符串日期
     * @return 要求获取的类型的日期
     */
    public static Object getDate(String sdfTo, Date defValue) {
        return getDate(sdfTo, null, defValue);
    }

    /**
     * 输入日期获取对应的日期
     *
     * @param sdfTo    获取的日期类型，Date对象，long 毫秒值，String 字符串日期
     * @param defValue 输入要转换的日期，Date对象，long 毫秒值，String 字符串日期
     * @return 要求获取的类型的日期
     */
    public static Object getDate(String sdfTo, long defValue) {
        return getDate(sdfTo, null, defValue);
    }

    /**
     * 输入日期获取对应的日期
     *
     * @param sdfTo    获取的日期类型，Date对象，long 毫秒值，String 字符串日期
     * @param defValue 输入要转换的日期，Date对象，long 毫秒值，String 字符串日期
     * @return 要求获取的类型的日期
     */
    public static String getDateStr(String sdfTo, long defValue) {
        //这样会排除后端不返回值造成的1970问题，但会遗漏掉1970/1/1 08:00:00，但没有确切的值可用，这样代价最小
        if (defValue == 0) return "";
        return getDate(sdfTo, null, defValue).toString();
    }

    /**
     * 输入日期获取对应的日期
     *
     * @param sdfTo    获取的日期类型，Date对象，long 毫秒值，String 字符串日期
     * @param sdfFrom  输入要转换的日期类型，Date对象，long 毫秒值，String 字符串日期
     * @param defValue 输入要转换的日期，Date对象，long 毫秒值，String 字符串日期
     * @return 要求获取的类型的日期
     */
    public static Object getDate(String sdfTo, String sdfFrom, Object defValue) {
        String type = defValue.getClass().getSimpleName();
        Date date;
        if ("Integer".equals(type) || "Long".equals(type)) {
            date = new Date((Long) defValue);
        } else if ("Date".equals(type)) {
            date = (Date) defValue;
        } else if ("String".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat(sdfFrom);
            try {
                date = sdf.parse((String) defValue);
            } catch (ParseException e) {
                throw new RuntimeException("the date type parse exception!");
            }

        } else {
            throw new RuntimeException("the date type illgel!");
        }
        if (DateType.DATE.equals(sdfTo)) {
            return date;
        } else if (DateType.DATETIME.equals(sdfTo)) {
            return date.getTime();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(sdfTo);
            return sdf.format(date);
        }

    }

    /**
     * 获取当前毫秒值
     */
    public static long getCurrentTimeMillis() {
        return new Date().getTime();
    }

    /**
     * 获取当前分的毫秒值忽略秒值 yyyy-mm-dd HH:mm
     */
    public static long getCurrentMinuteTimeMillis() {
        return (long) getDate(DateType.DATETIME, (Date) getDate(DateType.DATE, DateType.sdf_yyyy_MM_dd_HH_mm, new Date()));
    }

    /**
     * 比较两个日期的大小（-2转换异常0相等1大于-1小于）
     */
    public static int compareDate(Date dateF, Date dateS) {
        try {
            if (dateF.getTime() > dateS.getTime()) {
                return 1;
            } else if (dateF.getTime() < dateS.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return -2;
        }

    }

    /**
     * 获得指定年和月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        /** 把日期设置为当月第一天 */
        a.set(Calendar.DATE, 1);
        /** 日期回滚一天，也就是最后一天 */
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获得指定天在当月的天索引
     */
    public static int getDayOfMonth(Date date) {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.setTime(date);
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }


    /**
     * 获取当天是星期几
     */
    public static String getDateToWeek(Date date) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "星期" + mWay;
    }


    /**
     * 获取今天的日期
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当天的年份
     *
     * @return
     */

    public static String getTodayYear() {
        String today = getToday();
        String year = today.substring(0, 4);
        return year;
    }

    /**
     * 获取当前日期的月数的位置
     *
     * @return
     */
    public static String getTodayMonth() {
        String today = getToday();
        String month = today.substring(5, 7);
        return month;
    }

    /**
     * 获取当前日期的天数的日子
     *
     * @return
     */
    public static String getTodayDay() {
        String today = getToday();
        String day = today.substring(8, 10);
        return day;
    }

    /**
     * 获取当前小时
     */
    public static String getTodayHour() {
        String today = getToday();
        String hour = today.substring(12, 14);
        return hour;
    }

    /**
     * 获取当前分钟
     */
    public static String getTodayMinute() {
        String today = getToday();
        String minute = today.substring(15, 17);
        return minute;
    }

    /**
     * 获取当前秒
     */
    public static String getTodaySecond() {
        String today = getToday();
        String second = today.substring(18, 20);
        return second;
    }

    public static class DateType {

        /**
         * 输出类型 Date
         */
        public static String DATE = "DATE";
        /**
         * 输出类型 Datetime
         */
        public static String DATETIME = "DATETIME";
        /**
         * 输出、出入类型
         */
        public static String sdf_yyyyMMddHHmmss = "yyyyMMddHHmmss";
        public static String sdf_yyyyc_MMc_ddc_HH_mm_ss = "yyyy年MM月dd日 HH:mm:ss";
        public static String sdf_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
        public static String sdf_yyyyc_MMc_ddc_HH_mm = "yyyy年MM月dd日 HH:mm";
        public static String sdf_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
        public static String sdf_yyyyc_MMc_ddc = "yyyy年MM月dd日";
        public static String sdf_yyyy_MM_dd = "yyyy-MM-dd";
        public static String sdf_HH_mm_ss = "HH:mm:ss";
        public static String sdf_HH_mm = "HH:mm";
        public static String sdf_yyyy = "yyyy";
        public static String sdf_MM = "MM";
        public static String sdf_dd = "dd";
        public static String sdf_HH = "HH";
        public static String sdf_mm = "mm";
        public static String sdf_ss = "ss";
    }
}
