package com.cris15.xl.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/10:16
 * @Description:
 **/
public class BaseController {

    /**
     * 枚举返回结果
     */
    public enum Result{
        //success 成功或失败    code为状态码  0为成功1为失败    message返回信息   data返回数据
        success,code,message,data
    }


    protected Map result(Object data){
        Map<String,Object> result = new HashMap<>(4);
        result.put(Result.success.name(),true);
        result.put(Result.code.name(),"0");
        result.put(Result.message.name(), "请求成功");
        result.put(Result.data.name(),data);
        return result;
    }

    protected Map errorResult(Object data){
        Map<String,Object> result = new HashMap<>(4);
        result.put(Result.success.name(),false);
        result.put(Result.code.name(),"1");
        result.put(Result.message.name(), "失败");
        result.put(Result.data.name(),data);
        return result;
    }

    /**
     * 生成6位随机数
     * @return
     */
    public String getRandomCode(){
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        if(result.charAt(0) == '0'){
            StringBuilder sb = new StringBuilder(result);
            sb.replace(0,1,"5");
            result = sb.toString();
        }
        return result;
    }

}