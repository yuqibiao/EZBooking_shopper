package com.yyyu.ezbooking_shopper;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;

import java.io.File;

/**
 * 功能：全局参数
 *
 * @author yyyu
 * @date 2016/5/17
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public Context aContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.aContext = getApplicationContext();
        SDKInitializer.initialize(aContext);
    }
}
