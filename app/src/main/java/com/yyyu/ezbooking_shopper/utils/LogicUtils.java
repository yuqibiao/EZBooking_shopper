package com.yyyu.ezbooking_shopper.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yyyu.barbecue.ezbooking_base.bean.json.LoginJson;
import com.yyyu.barbecue.ezbooking_base.gobal.Constant;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MySPUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyStrUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyTimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 功能：业务逻辑相关的工具类
 * <p/>
 * Created by yyyu on 2016/8/8.
 */
public class LogicUtils {

    public static LogicUtils mInstance;

    private Context mContext;
    private Gson gson;

    private LogicUtils(Context context) {
        this.mContext = context;
        gson = new Gson();
    }

    public static LogicUtils getInstance(Context context) {
        synchronized (LogicUtils.class) {
            while (mInstance == null) {
                mInstance = new LogicUtils(context);
            }
        }
        return mInstance;
    }

    /**
     * 将 2016-09-10 13:11 转换成 09/10 周五 下午1:11这样
     * @param date
     * @param time
     * @return
     */
    public String converDateTime(String date , String time){

        String[] dateStrs = date.split("-");
        return ""+dateStrs[1]+"/"+dateStrs[2]+MyTimeUtils.getWeekStrByDate(date)+"  "+time;
    }

    /**
     * 得到时间信息 格式 8月27日 星期六
     *
     * @param  value  -1：前一天 /1：后一天
     */
    public String getDateTimeNWeek(int value){
        StringBuffer sb = new StringBuffer();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, value);
        int currentMonth = MyTimeUtils.getCurrentMonth(calendar);
        int currentDay = MyTimeUtils.getCurrentDay(calendar);
        int currentWeek = MyTimeUtils.getCurrentWeek(calendar);
        if(currentMonth<10){
            sb.append("0"+currentMonth);
        }else{
            sb.append(""+currentMonth);
        }
        sb.append("月");
        if(currentDay<10){
            sb.append("0"+currentDay);
        }else{
            sb.append(""+currentDay);
        }
        sb.append("日");
        sb.append(" 周"+ MyStrUtils.intToWeekNum(currentWeek));
        return sb.toString();
    }

    /**
     * 判断用户是否登录
     */
    public boolean isUserLogined(){
        return !TextUtils.isEmpty(getUserInfo());
    }

    /**
     * 得到用户的Id
     * @return
     */
    public int  getUserId(){
        LoginJson loginJson = gson.fromJson(getUserInfo() , LoginJson.class);
        return loginJson.RESULT_DATA.userId;
    }

    /**
     * 得到用户的信息Json串
     *
     * @return
     */
    public String getUserInfo() {
        return (String) MySPUtils.get(mContext, Constant.USER_INFO, "");
    }

}
