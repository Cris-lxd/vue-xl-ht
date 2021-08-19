package com.cris15.xl.util;
import java.lang.annotation.*;
/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 16:44
 * Project: demotest
 **/

@Target(ElementType.PARAMETER)          // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)     // 运行时有效
@Documented
public @interface CurrentUser {
    /**
     * 当前用户在session对象中的key，"user"  //  session.setAttribute("user",one);
     */
    String value() default "user" ;

}