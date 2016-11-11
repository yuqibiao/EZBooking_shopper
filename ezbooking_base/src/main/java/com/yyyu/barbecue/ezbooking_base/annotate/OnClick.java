package com.yyyu.barbecue.ezbooking_base.annotate;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能：View点击事件的注解
 * Created by yyyu on 2016/6/24.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(ListenerType = View.OnClickListener.class , ListenerSetter = "setOnClickListener" , methodName = "onClick")
public @interface OnClick {
    /**
     * 控件的Id
     * @return
     */
    int[] value();
}
