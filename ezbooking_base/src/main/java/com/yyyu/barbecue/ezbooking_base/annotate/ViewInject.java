package com.yyyu.barbecue.ezbooking_base.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能：View 的注入
 * Created by yyyu on 2016/6/24.
 */

@Target(ElementType.FIELD) //---表示该注解可以用于什么地方
@Retention(RetentionPolicy.RUNTIME) //---表示需要在什么级别保存该注解信息

public @interface ViewInject {

    /**
     * View 的 id
     */
    int value() default -1;
}
