package com.cris15.xl.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 14:30
 * Project: demotest
 **/

/**
 * 是否需要跳过该方法的注解（不验证token）
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}