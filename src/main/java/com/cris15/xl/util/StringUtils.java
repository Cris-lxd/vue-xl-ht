package com.cris15.xl.util;

/**
 * Author: Cris
 * Date: 2021/08/19
 * Time: 11:36
 * Project: demotest
 * Description：
 **/
public class StringUtils {

    /**
     * 判断string是否为空
     * @param str
     * @return
     */
    public static Boolean isEmpty(String str){
        if(str == null || str == ""){
            return true;
        }
        return false;
    }
}