package com.yyyu.barbecue.ezbooking_base.gobal;

import android.os.Environment;

import java.io.File;

/**
 * 功能：全局变量
 *
 * @author yyyu
 * @date 2016/8/6
 */
public class Constant {

    /**
     * 本项目文件存储的根目录
     */
    public static String filePath;

    /**
     * SP中用户信息 Key
     */
    public  static final String USER_INFO="user_info";


    static {
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EZBooking/";
        File savePath = new File(filePath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
    }



}
