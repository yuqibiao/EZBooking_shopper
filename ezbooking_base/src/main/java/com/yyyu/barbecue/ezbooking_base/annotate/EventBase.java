package com.yyyu.barbecue.ezbooking_base.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能：事件注解的基类注解
 *             主要注入时间的类型、事件set方法、方法名
 * Created by yyyu on 2016/6/24.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    /**
     * 事件类型
     * @return
     */
    Class<?> ListenerType();

    /**
     * 事件的set方法名
     * @return
     */
    String ListenerSetter();

    /**
     * 方法名
     * @return
     */
    String methodName();

}
