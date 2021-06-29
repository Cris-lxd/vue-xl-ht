package com.cris15.xl.controller;

import java.util.HashMap;
import java.util.Map;

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
        result.put(Result.message.name(), "成功");
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

}