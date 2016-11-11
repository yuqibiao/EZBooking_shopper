package com.yyyu.ezbooking_shopper.ui.wdiget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yyyu.barbecue.ezbooking_base.utils.MyLog;

/**
 * 功能：
 * Created by yyyu on 2016/9/19.
 */
public class MyViewPager extends ViewPager{

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MyLog.e("viewpager======dispatchTouchEvent===");
        return super.dispatchTouchEvent(ev);
    }
}
