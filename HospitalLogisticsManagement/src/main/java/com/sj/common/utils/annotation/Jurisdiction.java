package com.sj.common.utils.annotation;

import java.lang.annotation.*;

/**
 * Created by wa on 2016/2/29.
 */
@Target(ElementType.METHOD)//注解作用域
@Retention(RetentionPolicy.RUNTIME)//注解类型
@Documented//javadoc
public @interface Jurisdiction {
    String name() default "";//菜单名称
    int level() default 1;//菜单级别
    boolean resources() default false;//资源是否为开放
    String url() default "";
}
