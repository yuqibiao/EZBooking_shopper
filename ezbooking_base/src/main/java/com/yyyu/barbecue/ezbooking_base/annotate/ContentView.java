package com.yyyu.barbecue.ezbooking_base.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能：Activity布局的注入
 * Created by yyyu on 2016/6/24.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {

    /**
     * 布局的id
     */
    int value();

}
