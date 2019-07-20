package com.jt.anno;

import com.jt.enu.KEY_ENUM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 修改缓存
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache_update {
    String key() default "";
    KEY_ENUM keyType() default  KEY_ENUM.AUTO;
    int secondes() default 0;
}
