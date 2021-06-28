package com.cris15.xl.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/10:16
 * @Description:
 **/
public class BaseController {

    /**
     *  枚举返回结果
     */
    public enum Result{
        success,code,message,data
    }


    protected Map result(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put(Result.success.name(),true);
        result.put(Result.code.name(),"0");
        result.put(Result.message.name(), "成功");
        result.put(Result.data.name(),data);
        return result;
    }

    protected Map errorResult(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put(Result.success.name(),false);
        result.put(Result.code.name(),"1");
        result.put(Result.message.name(), "失败");
        result.put(Result.data.name(),data);
        return result;
    }

}