package com.sj.common.utils.annotation;

import java.lang.annotation.*;

/**
 * Created by wa on 2016/2/29.
 */
@Target(ElementType.TYPE)//注解作用域
@Retention(RetentionPolicy.RUNTIME)//注解类型
@Documented//javadoc
public @interface Table {
    String name();//表名
    int colNum();//表头所占列数
}
