package com.yangshuai.library.base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author hcp
 * @date 2019/4/12
 */
public class TimeUtil {

    private static SimpleDateFormat sDateFormat;

    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";


    /**
     * 时间戳转日期
     *
     * @param time    时间戳
     * @param pattern 转换后的格式，例如(yyyy-MM-dd HH:mm:ss)
     */
    @SuppressLint("SimpleDateFormat")
    public static String longToDate(long time, String pattern) {
        if (time == 0) {
            return "空";
        } else {
            if (String.valueOf(time).length() == 10) {
                time = time * 1000L;
            }
        }
        sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(time));
    }

    /**
     * 时间戳转时间
     *
     * @param time 时间戳
     */
    public static String longToTime(long time) {
        return longToDate(time, "HH:mm");
    }

    /**
     * 时间戳转日期 10月11日
     *
     * @param time 时间戳
     */
    public static String longToDateCh(long time) {
        String date = longToDate(time, "MM-dd");
        String m = date.substring(0, date.indexOf("-"));
        String d = date.substring(date.indexOf("-") + 1);
        return m + "月" + d + "日";
    }

    /**
     * 时间戳转日期
     *
     * @param time 时间戳
     */
    public static String longToDate(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        return longToDate(Long.parseLong(time), "yyyy-MM-dd HH:mm");
    }

    /**
     * 时间戳转日期
     *
     * @param time 时间戳
     */
    public static String longToDate(long time) {
        return longToDate(time, "yyyy-MM-dd HH:mm");
    }

    /**
     * 13位时间戳转日期 可自定义需要的格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param time 时间戳
     * @return 自定义格式时间字符
     */
    public static String timeStampDate(String time, String pattern) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Long timeLong = Long.parseLong(time);
        //要转换的时间格式
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            if (date != null) {
                return sdf.format(date);
            } else {
                return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 13位时间戳转年月日
     *
     * @param time 1556177448120
     * @return 2019-04-25
     */
    public static String timeStamp2Date(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        return timeStamp2Date(Long.parseLong(time));
    }

    /**
     * 13位时间戳转年月日
     *
     * @param time 1556177448120
     * @return 2019-04-25
     */
    public static String timeStamp2Date(long time) {
        //要转换的时间格式
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            return sdf.format(calendar.getTime());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将日期转换为13位时间戳 例：2019-04-25 15:50:30
     */
    public static String dateToStamp(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String res = "";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(time);
            if (date != null) {
                long ts = date.getTime();
                res = String.valueOf(ts);
            }
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 将日期转换为13位时间戳 例：2019-04-25 15:50:30
     */
    public static String dateToStamp(String time, String pattern) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String res = "";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = simpleDateFormat.parse(time);
            if (date != null) {
                long ts = date.getTime();
                res = String.valueOf(ts);
            }
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }


    /**
     * 年月日转时间戳
     *
     * @param time 时间字符串
     * @return 根据time转换的时间戳
     */
    public static long yearMonthDayToLong(String time) {
        return dateToLong(time, "yyyy-MM-dd");
    }

    /**
     * 剩余时间 08:59:28
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        time = time / 1000;
        String strHour = "" + (time / 3600);
        String strMinute = "" + time % 3600 / 60;
        String strSecond = "" + time % 3600 % 60;
        strHour = strHour.length() < 2 ? "0" + strHour : strHour;
        strMinute = strMinute.length() < 2 ? "0" + strMinute : strMinute;
        strSecond = strSecond.length() < 2 ? "0" + strSecond : strSecond;

        return strHour + ":" + strMinute + ":" + strSecond;
    }

    /**
     * 日期转时间戳
     *
     * @param time    时间字符串
     * @param pattern 格式，例如(yyyy-mm-dd)
     * @return 根据time转换的时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static long dateToLong(String time, String pattern) {
        try {
            if (TextUtils.isEmpty(time)) {
                return 0;
            }
            Calendar calendar = Calendar.getInstance();
            sDateFormat = new SimpleDateFormat(pattern);
            sDateFormat.setLenient(false);
            Date date = sDateFormat.parse(time);
            if (date != null) {
                calendar.setTime(date);
            }
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 获取系统当前时间，格式可选择
     *
     * @param pattern 时间格式，例如(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentTime(String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            return "";
        }
        Date date = new Date();
        sDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return sDateFormat.format(date);
    }

    public static String getTimeStr(long time, String pattern) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(time);
    }

    /**
     * 转时间
     * @param time
     * @return
     */
    public static String getFullTimeStr(long time) {
        return getTimeStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 转时间
     * @param time
     * @return
     */
    public static String getTimeStr(long time) {
        return getTimeStr(time, "yyyy-MM-dd HH:mm");
    }

    /**
     * 转日期
     * @param time
     * @return
     */
    public static String getDateStr(long time) {
        return getTimeStr(time, "yyyy-MM-dd");
    }

    /**
     * 获取多少天以后的日期
     *
     * @param time    开始时间时间戳
     * @param day     多少天
     * @param pattern 时间格式，例如(yyyy-MM-dd HH:mm:ss)
     */
    public static String getExpectData(long time, int day, String pattern) {
        long lData = day * (24 * 3600 * 1000L);
        long resultData = time + lData;
        return getTimeStr(resultData, pattern);
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return 计算后的日期
     */
    public static String getOldDate(int distanceDay) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (endDate == null) {
            return "";
        }
        return dft.format(endDate);
    }

    /**
     * 获取两个日期相差的天数
     */
    public static long getDifferDay(long endTime, long startTime) {
        if (startTime == 0 || endTime == 0) {
            return 0;
        }
        long time = endTime - startTime;
        return time / (24 * 3600 * 1000L);
    }

    /**
     * 格式化时间
     *
     * @param date 日期
     * @return 格式化日期字符
     */
    public static String formatTime(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 比较两个时间的大小,如果开始时间大于结束时间，则返回true
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return boolean
     */
    public static boolean compareTimes(String startTime, String endTime) {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            long start = dateToLong(startTime, "yyyy-MM-dd");
            long end = dateToLong(endTime, "yyyy-MM-dd");
            return start > end;
        }
        return false;
    }

    /**
     * 获取日期差·1
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return 日期差
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        return Math.abs(yearInterval * 12 + monthInterval);
    }

    /**
     * 根据分钟数获取时分
     *
     * @param minNum 分钟数
     * @return 12:08
     */
    public static String getStrByMinNum(String minNum) {
        int time = Integer.parseInt(minNum);
        int hour = time / 60;
        int min = time % 60;
        if (hour == 0) {
            return min + "分";
        }
        String hourStr = String.valueOf(hour);
        String minStr = String.valueOf(min);
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        if (min < 10) {
            minStr = "0" + minStr;
        }
        return hourStr + "时" + minStr + "分";
    }

}
