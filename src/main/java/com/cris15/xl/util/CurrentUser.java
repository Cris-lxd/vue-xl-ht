package com.cris15.xl.util;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 16:44
 * Project: demotest
 **/


import java.lang.annotation.ElementType;
@Target(ElementType.PARAMETER)          // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)     // 运行时有效
public @interface CurrentUser {

}