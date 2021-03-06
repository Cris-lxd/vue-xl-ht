package com.cris15.xl.util;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String dateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
        String res = sdf.format(date);
        return res;
    }
}