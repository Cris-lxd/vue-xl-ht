package com.cris15.xl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Author: Cris
 * Date: 2021/08/20
 * Time: 9:36
 * Project: demotest
 * Description： 全局处理类
 **/
@ControllerAdvice
public class GlobalExceptionHandler extends Exception{

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void getException(Exception ex){
        logger.error(ex.getMessage());
    }
}