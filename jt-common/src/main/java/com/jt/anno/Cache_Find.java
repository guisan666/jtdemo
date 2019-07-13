package com.jt.anno;

import com.jt.enu.KEY_ENUM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个查询的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)   //注解的作用范围
public @interface Cache_Find {
    String key() default "";   //接收用户key值
    KEY_ENUM keyType() default KEY_ENUM.AUTO;  //定义key类型
    int secondes() default 0;  //定义key到的超时时间  永不失败
}
