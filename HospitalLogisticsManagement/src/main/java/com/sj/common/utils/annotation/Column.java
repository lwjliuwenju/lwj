package com.sj.common.utils.annotation;

import java.lang.annotation.*;

/**
 * Created by wa on 2016/2/29.
 */
@Target(ElementType.METHOD)//注解作用域
@Retention(RetentionPolicy.RUNTIME)//注解类型
@Documented//javadoc
public @interface Column {
     String name();//列名
     int sort();//顺序
}
