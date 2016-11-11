package com.yyyu.barbecue.ezbooking_base.utils;

import java.util.Calendar;

/**
 * 功能：时间日期相关的工具类
 * <p/>
 * Created by yyyu on 2016/7/29.
 */
public class MyTimeUtils {


    /**
     * 去掉首位0
     */
    public static int removeZerotoNum(String str) {
        if (str.startsWith("0")) {
            str = str.substring(1, str.length());
        }
        return Integer.parseInt(str);
    }

    /**
     * 补全十位数（针对时、分）
     *
     * @param i
     * @return
     */
    public static String complDigit(int i) {
        String result = i + "";
        if (i < 10) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * 根据年月得到当前月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayNum(int year, int month) {
        int day;
        boolean isLeap;
        switch (year % 4) {
            case 0:
                isLeap = true;
                break;
            default:
                isLeap = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = isLeap ? 28 : 29;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }


    /**
     *
     * @param date
     * @return 返回格式为 周五
     */
    public static String  getWeekStrByDate(String date){

        return "周"+MyStrUtils.intToWeekNum(getWeekNumByDate(date));
    }

    /**
     * 根据日期得到星期几
     *
     * @param date 格式为 2016-09-17这样
     * @return
     */
    public static int getWeekNumByDate(String date) {
        String[] strs = date.split("-");
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].startsWith("0")) {
                strs[i] = strs[i].substring(1, strs[i].length());
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(strs[0])
                , Integer.parseInt(strs[1]) - 1
                , Integer.parseInt(strs[2]));
        boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 得到当前的星期
     *
     * @return
     */
    public static int getCurrentWeek(Calendar calendar) {
        boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 得到当前日
     *
     * @return
     */
    public static int getCurrentDay(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前月
     *
     * @return
     */
    public static int getCurrentMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

}
