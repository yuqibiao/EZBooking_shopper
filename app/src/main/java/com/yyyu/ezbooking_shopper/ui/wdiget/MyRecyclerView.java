package com.yyyu.ezbooking_shopper.ui.wdiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yyyu.barbecue.ezbooking_base.utils.MyLog;

/**
 * 功能：解决 RecyclerView和SwipeBackLayout的滑动冲突
 *
 * TODO：
 *
 *
 * Created by yyyu on 2016/9/20.
 */
public class MyRecyclerView extends RecyclerView{
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:{
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                    getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_UP:{
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
        }
        return super.onTouchEvent(e);
    }
}
