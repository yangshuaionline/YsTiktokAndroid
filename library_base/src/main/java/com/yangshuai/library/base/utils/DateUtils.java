/*
 *
 */
package com.yangshuai.library.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.interfaces.OnTimeSelectCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author hcp
 * @version 1.0
 * @see org.apache.commons.lang3.time.DateUtils
 *
 * <p>日期工具类
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 1周的天数
     */
    private static final int DAYS_IN_A_WEEK = 7;

    /**
     * 1季的月数
     */
    private static final int MONTHS_IN_A_SEASON = 3;

    /**
     * 不允许实例化
     */
    private DateUtils() {
    }


    /**
     * 根据日期获取时刻
     *
     * @param date
     * @return
     */
    public static String getMomentByDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    /**
     * @return 今天起始秒
     * @return
     */
    public static String getFirstMillsOfToday() {
        Date date = getFirstSecondOfToday();
        return date.getTime()+"";
    }

    /**
     * @return 今天起始秒
     */
    public static Date getFirstSecondOfToday() {
        return getFirstSecondOfTheDayReturnCalendar(null).getTime();
    }

    /**
     * @return 今天结束秒
     */
    public static Date getLastSecondOfToday() {
        // 今天起始秒
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(null);
        // 加1天
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * @return 当前时间时钟零秒
     */
    public static long getFirstOfHourLong() {
        Calendar calendar = Calendar.getInstance();
        // 当前时间的时、分、秒、毫秒
//        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
//        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar.getTime().getTime();
    }

    /**
     * @return 当前时间时钟某分钟的时间戳
     */
    public static long getFirstOfHourLong(int toMinute) {
        Calendar calendar = Calendar.getInstance();
        // 当前时间的时、分、秒、毫秒
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.MINUTE, toMinute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar.getTime().getTime();
    }

    /**
     * @return 当天时间时钟某分钟的时间戳
     */
    public static long getFirstOfHourOfDayLong(int toMinute) {
        Calendar calendar = Calendar.getInstance();
        // 当前时间的时、分、秒、毫秒
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.HOUR_OF_DAY, -hour);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.MINUTE, toMinute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar.getTime().getTime();
    }

    /**
     * @return 当前时间下一个时钟某分钟的时间戳
     */
    public static long getFirstOfNextHourLong(int toMinute) {
        Calendar calendar = Calendar.getInstance();
        // 当前时间的时、分、秒、毫秒
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.MINUTE, toMinute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar.getTime().getTime();
    }

    /**
     * @return 本周起始秒
     */
    public static Date getFirstSecondOfThisWeek() {
        return getFirstSecondOfThisWeekReturnCalendar().getTime();
    }

    /**
     * @return 本周结束秒
     */
    public static Date getLastSecondOfThisWeek() {
        // 本周起始秒
        Calendar calendar = getFirstSecondOfThisWeekReturnCalendar();
        // 加1周
        calendar.add(Calendar.DAY_OF_WEEK, DAYS_IN_A_WEEK);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * @return 30天后的结束秒
     */
    public static Date getLastSecondOf30Day() {
        Date now = new Date();
        // 计算某一月份的最大天数  
        Calendar cal = Calendar.getInstance();
        // Date转化为Calendar  
        cal.setTime(now);
        // 一月后的1天前 
        cal.add(java.util.Calendar.DAY_OF_MONTH, 30);
        return cal.getTime();
    }


    /**
     * <p>认为周一为本周第1天
     *
     * @return 本周起始秒
     */
    private static Calendar getFirstSecondOfThisWeekReturnCalendar() {
        // 今天起始秒
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(null);
        // 减去周
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, -dayOfWeek + 2);
        return calendar;
    }

    /**
     * @return 本月起始秒
     */
    public static Date getFirstSecondOfThisMonth() {
        return getFirstSecondOfThisMonthReturnCalendar().getTime();
    }

    /**
     * @return 本月结束秒
     */
    public static Date getLastSecondOfThisMonth() {
        // 本月起始秒
        Calendar calendar = getFirstSecondOfThisMonthReturnCalendar();
        // 加1月
        calendar.add(Calendar.MONTH, 1);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 根据日期获取月份的头一秒
     *
     * @param date
     * @return
     */
    public static Date getFirstofThisMonthTime(Date date) {
        return getFirstSecondOfThisMonthReturnCalendar(date).getTime();

    }

    /**
     * @return 根据日期的月结束秒
     */
    public static Date getLastSecondOfThisMonthTime(Date date) {
        // 本月起始秒
        Calendar calendar = getFirstSecondOfThisMonthReturnCalendar(date);
        // 加1月
        calendar.add(Calendar.MONTH, 1);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * @return 本月起始秒
     */
    private static Calendar getFirstSecondOfThisMonthReturnCalendar() {
        // 今天起始秒
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(null);
        // 起始本月的天
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfMonth + 1);
        return calendar;
    }

    /**
     * @return 本月起始秒
     */
    private static Calendar getFirstSecondOfThisMonthReturnCalendar(Date date) {
        // 今天起始秒
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(date);
        // 起始本月的天
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfMonth + 1);
        return calendar;
    }

    /**
     * @return 本季起始秒
     */
    public static Date getFirstSecondOfThisSeason() {
        return getLastSecondOfThisSeasonReturnCalendar().getTime();
    }

    /**
     * @return 本季结束秒
     */
    public static Date getLastSecondOfThisSeason() {
        // 本季起始秒
        Calendar calendar = getLastSecondOfThisSeasonReturnCalendar();
        // 加1季
        calendar.add(Calendar.MONTH, MONTHS_IN_A_SEASON);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * @return 本年结束秒
     */
    private static Calendar getLastSecondOfThisSeasonReturnCalendar() {
        // 今天起始秒
        Calendar calendar = getFirstSecondOfThisMonthReturnCalendar();
        // 起始本月的天
        int month = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.MONTH, month % MONTHS_IN_A_SEASON);
        return calendar;
    }

    /**
     * @return 本年起始秒
     */
    public static Date getFirstSecondOfThisYear() {
        return getFirstSecondOfThisYearReturnCalendar().getTime();
    }

    /**
     * @return 本年结束秒
     */
    public static Date getLastSecondOfThisYear() {
        // 本年结束秒
        Calendar calendar = getFirstSecondOfThisYearReturnCalendar();
        // 加1年
        calendar.add(Calendar.YEAR, 1);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * @return 本年结束秒
     */
    private static Calendar getFirstSecondOfThisYearReturnCalendar() {
        // 今天起始秒
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(null);
        // 起始本月的天
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.add(Calendar.DAY_OF_YEAR, -dayOfYear + 1);
        return calendar;
    }

    /**
     * @return 获取该天的起始豪秒
     */
    public static long getFirstSecondOfTheDay(Date day) {
        return getFirstSecondOfTheDayReturnCalendar(day).getTimeInMillis();
    }

    /**
     * @return 获取该天的结束秒
     */
    public static Date getLastSecondOfTheDay(Date day) {
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(day);
        // 加1天
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * @param day
     * @return 获取昨天的起始秒
     */
    public static Date getStartYearesTheDay(Date day) {
        return getYearsSecondOfTheDayReturnCalendar(day).getTime();
    }

    /**
     * @param day
     * @return 昨天最后一秒
     */
    public static Date getLastYearesTheDay(Date day) {
        Calendar calendar = getYearsSecondOfTheDayReturnCalendar(day);
        // 加1天
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // 减1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 如果day == null，则默认为今天
     *
     * @return 获取该天的起始秒
     */
    private static Calendar getFirstSecondOfTheDayReturnCalendar(Date day) {
        Calendar calendar = Calendar.getInstance();
        if (day != null) {
            calendar.setTime(day);

        }
        // 当前时间的时、分、秒、毫秒
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar;
    }

    /**
     * @return 获取昨天的起始秒
     */
    private static Calendar getYearsSecondOfTheDayReturnCalendar(Date day) {
        Calendar calendar = Calendar.getInstance();
        if (day != null) {
            calendar.setTime(day);
            calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动

        }

        // 当前时间的时、分、秒、毫秒
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar;
    }


    /**
     * 获取前一天的日期
     *
     * @param day
     * @return
     */
    public static Calendar getTomorrowDay(Date day, int dayCount) {
        Calendar calendar = Calendar.getInstance();
        if (day != null) {
            calendar.setTime(day);
            calendar.add(calendar.DATE, -dayCount);//

        }
        return calendar;
    }


    /**
     * 1 当天内、2 7天内、更早
     *
     * @param time
     * @return
     */
    public static int getDateSectionByStr(String time) {
        long timeValue = Long.parseLong(time);
        if (isToday(timeValue)) return 1;
        else if (is7Day(timeValue)) return 2;
        else return 3;
    }


    /**
     * 判断选择的日期是否是本周
     *
     * @param time
     * @return
     */
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }


    /**
     * 当天00:00:00 以前7日
     *
     * @param time
     * @return
     */
    public static boolean is7Day(long time) {
        long todayTime = getFirstSecondOfTheDay(new Date());
        if ((todayTime - time) <= 7 * 24 * 60 * 60 * 1000) return true;
        else return false;
    }


    /**
     * 判断选择的日期是否是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }


    /**
     * 判断选择的日期是否是本月
     *
     * @param time
     * @return
     */
    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }


    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }


    /**
     * 获取七天内的数据，当天日期往前推
     *
     * @param day
     * @return
     */
    public static Calendar getWeekSecondOfTheDayReturnCalendar(Date day, int count) {
        Calendar calendar = Calendar.getInstance();
        if (day != null) {
            calendar.setTime(day);
            calendar.add(calendar.DATE, count);//把日期往后增加一天.整数往后推,负数往前移动

        }
        // 当前时间的时、分、秒、毫秒
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar;
    }

    /**
     * @return 本周起始秒
     */
    public static Date getWeekFristSecondOfThis(Date date, int count) {
        return getWeekSecondOfTheDayReturnCalendar(date, count).getTime();
    }


    /**
     * @param day
     * @return 获取月份的每一天的起始秒
     */
    public static Date getStartMonthEveryTheDay(Date day, int i) {
        return getMonthCountOfTheDayReturnCalendar(day, i).getTime();
    }


    /**
     * 获取月份的每一天的第一秒钟
     *
     * @param day
     * @param i
     * @return
     */
    public static Calendar getMonthCountOfTheDayReturnCalendar(Date day, int i) {
        Calendar calendar = Calendar.getInstance();
        if (day != null) {
            calendar.setTime(day);
            calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动

        }
        // 当前时间的时、分、秒、毫秒
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        // 减去时、分、秒、毫秒
        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.SECOND, -second);
        calendar.add(Calendar.MILLISECOND, -millisecond);
        return calendar;
    }


    /*
     * 获取当前时间
     */
    public static Date getCurrentDate() {
        return new Date();
    }


    /**
     * 获取当前时间年
     *
     * @return
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前时间月
     *
     * @return
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前时间日
     *
     * @return
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 获取当前时间时
     *
     * @return
     */
    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    /**
     * 获取上一年的1月1日
     *
     * @return
     */
    public static Date getLastYearDate() {
        int year = getCurrentYear() - 1 - 1900;
        return new Date(year, 1 - 1, 1);
    }

    /**
     * 获取当前时间过去12月
     *
     * @return
     */
    public static Date getLastMonth12() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11);
        return calendar.getTime();
    }

    /**
     * 获取下一年的12月31日
     *
     * @return
     */
    public static Date getNextYearDate() {
        int year = getCurrentYear() + 1 - 1900;
        return new Date(year, 12 - 1, 31);
    }

    /**
     * 2018年的1月1日
     *
     * @return
     */
    public static Date getDate2018() {
        return new Date(2018 - 1900, 1 - 1, 1);
    }


    /**
     * 根据日期获取当前月的天数
     *
     * @param startDate
     * @return
     */
    public static int getMaxDayCount(Date startDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.setTime(startDate); //放入你的日期
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maximum;
    }

    public static Date format(String stringDate, String moudle) {
        SimpleDateFormat sdf = new SimpleDateFormat(moudle);
        Date date = null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     * jll
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     * jll
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }


    /**
     * 根據日期獲得星期
     *
     * @param sDate yyyy-MM-dd
     * @authorＱＣ班長
     * @return當前日期是星期幾
     */
    public static String getFullDateWeekTime(String sDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sDate);
            SimpleDateFormat format = new SimpleDateFormat("EEEE");
            return format.format(date);
        } catch (Exception ex) {
            System.out.println("TimeUtil getFullDateWeekTime Error:" + ex.getMessage());
            return "";
        }
    }

    /**
     * 时间选择器 精确到月份
     *
     * @param activity
     * @param onTimeSelectListener
     */
    public static TimePickerView showMonthPickerDate(Activity activity, Date startTime, Date endTime
            , OnTimeSelectCallback onTimeSelectListener) {

        TimePickerView pickerViewTime;
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        pickerViewTime = new TimePickerBuilder(activity, (date, view) -> {
            onTimeSelectListener.onTimeSelect(date, view);
        })
                .setLayoutRes(R.layout.base_picker_custom, v -> {
                    // 获取自定义布局里面的控件
                    View vok = v.findViewById(R.id.rl_picker_ok); // 确定
                    vok.setOnClickListener(v1 -> {
                        onTimeSelectListener.clickConfirmButton();
                    });
                })
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                // 设置滚轮文字大小
                .setContentTextSize(16)
                // 设置选中文字颜色
                .setTextColorCenter(ContextCompat.getColor(activity, R.color.theme))
                // 设置未选中文字颜色
                .setTextColorOut(ContextCompat.getColor(activity, R.color.light_grey))
                // 解决PickerView被虚拟按键遮挡的问题
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();

        pickerViewTime.show();

        return pickerViewTime;
    }

    /**
     * 时间选择器 精确到月份日期
     *
     * @param activity
     * @param onTimeSelectListener
     */
    public static TimePickerView showDayPickerDate(Activity activity, Date startTime, Date endTime
            , OnTimeSelectCallback onTimeSelectListener) {

        TimePickerView pickerViewTime;
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        pickerViewTime = new TimePickerBuilder(activity, (date, view) -> {
            onTimeSelectListener.onTimeSelect(date, view);
        })
                .setLayoutRes(R.layout.base_picker_custom, v -> {
                    // 获取自定义布局里面的控件
                    View vok = v.findViewById(R.id.rl_picker_ok); // 确定
                    vok.setOnClickListener(v1 -> {
                        onTimeSelectListener.clickConfirmButton();
                    });
                })
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                // 设置滚轮文字大小
                .setContentTextSize(16)
                // 设置选中文字颜色
                .setTextColorCenter(ContextCompat.getColor(activity, R.color.theme))
                // 设置未选中文字颜色
                .setTextColorOut(ContextCompat.getColor(activity, R.color.light_grey))
                // 解决PickerView被虚拟按键遮挡的问题
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();

        pickerViewTime.show();

        return pickerViewTime;
    }

    /**
     * 时间选择器 精确到时间
     *
     * @param activity
     * @param onTimeSelectListener
     */
    public static TimePickerView showTimePicker(Activity activity, Date startTime, Date endTime
            , OnTimeSelectCallback onTimeSelectListener) {

        TimePickerView pickerViewTime;
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        pickerViewTime = new TimePickerBuilder(activity, (date, view) -> {
            onTimeSelectListener.onTimeSelect(date, view);
        })
                .setLayoutRes(R.layout.base_picker_custom, v -> {

                    // 获取自定义布局里面的控件
                    TextView tvTitle = v.findViewById(R.id.tv_picker_title); // 标题文本
                    View vok = v.findViewById(R.id.rl_picker_ok); // 确定
                    vok.setOnClickListener(v1 -> {

                        onTimeSelectListener.clickConfirmButton();

//						pickerViewTime.returnData();
//						pickerViewTime.dismiss();
                    });
                })
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                // 设置滚轮文字大小
                .setContentTextSize(16)
                // 设置选中文字颜色
                .setTextColorCenter(ContextCompat.getColor(activity, R.color.theme))
                // 设置未选中文字颜色
                .setTextColorOut(ContextCompat.getColor(activity, R.color.light_grey))
                // 解决PickerView被虚拟按键遮挡的问题
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();

        pickerViewTime.show();

        return pickerViewTime;
    }


    private static List<String> days = new ArrayList<>(); // 日期
    private static List<String> currentDayTimes = new ArrayList<>(); // 当天时间
    private static List<String> allDayTimes = new ArrayList<>(); // 当天所有时间
    private static List<String> selectDayTimes;// 所选中的时间集合

    private static com.yangshuai.library.base.view.pickerview.TimePickerView pickerViewTime = null;

    /**
     * 时间选择器 精确到时间
     *
     * @param activity
     * @param onTimeSelectListener
     */
    public static void showTimePicker2(Activity activity, int dayLong, OnTimeSelectCallback onTimeSelectListener) {

        try {

            initTimePickerData(dayLong);

            pickerViewTime = new com.yangshuai.library.base.view.pickerview.TimePickerBuilder(activity,
                    (options1, options2, v) -> {
                        // 拼时间戳 eg:2020-02-17 18:15
                        String dayStr = days.get(options1);
                        String month = dayStr.substring(0, dayStr.indexOf("月"));
                        String day = dayStr.substring(dayStr.indexOf("月") + 1, dayStr.indexOf("日"));
                        String time = selectDayTimes.get(options2);
                        String year = getCurrentMonth() > Integer.parseInt(month) ? (getCurrentYear() + 1 + ""): getCurrentYear() + "";
                        String timeStr = year + "-" + month + "-" + day + " " + time;
                        long timeLong = TimeUtil.dateToLong(timeStr, "yyyy-MM-dd HH:mm");
                        onTimeSelectListener.onTimeSelect(timeLong, v);

                    })
                    .setOptionsSelectChangeListener(((options1, options2) -> {
                        if (options1 == 0) {
                            pickerViewTime.setPickerTime(currentDayTimes); // 设置数据源
                            selectDayTimes = currentDayTimes;
                        } else {
                            pickerViewTime.setPickerTime(allDayTimes);
                            selectDayTimes = allDayTimes;
                        }
                    }))
                    //.setLabels("", "") // 设置每项对应的标签
                    .setTitleText("选择直播时间")
                    // 设置滚轮文字大小
                    .setContentTextSize(16)
                    // 设置选中文字颜色
                    .setTextColorCenter(ContextCompat.getColor(activity, R.color.theme))
                    // 设置未选中文字颜色
                    .setTextColorOut(ContextCompat.getColor(activity, R.color.light_grey))
                    .setDecorView((ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content))
                    .build();
            pickerViewTime.setPicker(days, currentDayTimes); // 设置数据源
            pickerViewTime.setSelectOptions(0, 0); // 设置默认选中位置
            pickerViewTime.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initTimePickerData(int dayLong) {

        currentDayTimes.clear();
        allDayTimes.clear();
        days.clear();
        selectDayTimes = null;

        long startTime = 0;

        // 某天的月份日期 星期
        for (int i = 0; i < dayLong; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, i);
            String dateStr = (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日" + getWeekDay(cal.get(Calendar.DAY_OF_WEEK));
            days.add(dateStr);
        }

        // 获取当前时间 时间戳
        Calendar cal = Calendar.getInstance();

        long current = cal.getTimeInMillis();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        // 当前时间小时内的15 分钟
        long a = getFirstOfHourLong(15);
        // 当前时间小时内的30 分钟
        long b = getFirstOfHourLong(30);
        // 当前时间小时内的45 分钟
        long c = getFirstOfHourLong(45);
        // 下个小时的 0 分钟
        long d = getFirstOfHourLong(60);

        if (current < a) {
            startTime = a;
//            currentDayTimes.add(TimeUtil.longToDate(a, "HH:mm"));
            currentDayTimes.add(TimeUtil.longToDate(b, "HH:mm"));
            currentDayTimes.add(TimeUtil.longToDate(c, "HH:mm"));
//            currentDayTimes.add(TimeUtil.longToDate(d, "HH:mm"));
        } else if (current > a && current < b) {
            startTime = b;
//            currentDayTimes.add(TimeUtil.longToDate(b, "HH:mm"));
            currentDayTimes.add(TimeUtil.longToDate(c, "HH:mm"));
//            currentDayTimes.add(TimeUtil.longToDate(d, "HH:mm"));
        } else if (current > b && current < c) {
            startTime = c;
//            currentDayTimes.add(TimeUtil.longToDate(c, "HH:mm"));
//            currentDayTimes.add(TimeUtil.longToDate(d, "HH:mm"));
        } else if (current > c && current < d) {
            startTime = d;
//            times.add(TimeUtil.longToDate(d,"HH:mm"));
        }

        int currentSize = (23 - currentHour) * 4;

        for (int i = 0; i < currentSize; i++) {
            if (i == 0 && startTime == d) continue;
            long e = getFirstOfNextHourLong(15 * i);
            currentDayTimes.add(TimeUtil.longToDate(e, "HH:mm"));
        }

        for (int i = 0; i < 96; i++) {
            long e = getFirstOfHourOfDayLong(15 * i);
            allDayTimes.add(TimeUtil.longToDate(e, "HH:mm"));
        }

        selectDayTimes = currentDayTimes;
    }


    private static String getWeekDay(int day) {
        switch (day) {
            case 1:
                return "  星期日";
            case 2:
                return "  星期一";
            case 3:
                return "  星期二";
            case 4:
                return "  星期三";
            case 5:
                return "  星期四";
            case 6:
                return "  星期五";
            case 7:
                return "  星期六";
        }
        return "";
    }





    // 获得本周一0点时间

//    public static Date getTimesWeekmorning() {
//        Calendar c = Calendar.getInstance();
//        c.setFirstDayOfWeek(Calendar.SUNDAY);
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//
//        return c.getTime();
//    }
//
//    // 获得本周日24点时间
//    public static Date getTimesWeeknight() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(getTimesWeekmorning());
//        cal.add(Calendar.DAY_OF_WEEK, 7);
//        return cal.getTime();
//    }


    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }

    /** 获得上周第一天:yyyy-MM-dd  HH:mm:ss */
    @SuppressLint("WrongConstant")
    public static Date getWeekStartDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE,-7);


        Date lastSecondOfTheDay = new Date(getFirstSecondOfTheDay(calendar.getTime()));

//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.DAY_OF_WEEK,2);
//        c.add(Calendar.DATE, -7);
        return lastSecondOfTheDay;
    }

    /** 获得上周最后一天:yyyy-MM-dd  HH:mm:ss */
    @SuppressLint("WrongConstant")
    public static Date getWeekEndDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE,-1);

        return getLastSecondOfTheDay(calendar.getTime());
    }

    /**
     * 获取某个月份的上个月起始
     * @return
     */
    public static long getLastMonthStartDayByStamp(String stamp) {
        Date date = new Date(Long.parseLong(stamp));
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某个月份的下个月起始
     * @return
     */
    public static long getNextMonthStartDayByStamp(String stamp) {
        Date date = new Date(Long.parseLong(stamp));
        Calendar calendar = getFirstSecondOfTheDayReturnCalendar(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTimeInMillis();
    }

    /** 获得上月第一天:yyyy-MM-dd  HH:mm:ss */
    public static Date getMonthStartDay() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);

        Date lastSecondOfTheDay = new Date(getFirstSecondOfTheDay(calendar1.getTime()));
        return lastSecondOfTheDay;
    }
    /** 获得上月最后一天:yyyy-MM-dd  HH:mm:ss */
    public static Date getEndDayOfLastMonth() {
        return new Date(getFirstSecondOfThisMonth().getTime()-1000);
    }

    /**
     * 本周开始时间
     * @return
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }

        cal.add(Calendar.DATE, 2 - dayofweek);
        Date lastSecondOfTheDay = new Date(getFirstSecondOfTheDay(cal.getTime()));


        return lastSecondOfTheDay;
    }


    // 获取上周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return getLastSecondOfTheDay(cal.getTime());
    }
    /**
     * 获取某天一年前的时间
     *
     */

    public static Date getLsatYearDayTime(Date date){
        //过去一年
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(android.icu.util.Calendar.YEAR, -1);
        Date data1 = c.getTime();
        return data1;
    }

    /**
     * 获取今天最后一刻的日期
     *
     * @return
     */
    public static Date getEndDateOfToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取今天开始时刻的日期
     *
     * @return
     */
    public static Date getStartDateOfToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static String format(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
