package com.cris15.xl.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 14:29
 * Project: demotest
 **/

/**
 *  加注解代表需要登陆才能进入的方法
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
}