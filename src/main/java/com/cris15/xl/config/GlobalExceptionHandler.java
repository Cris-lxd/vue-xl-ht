package com.cris15.xl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        ex.printStackTrace();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();
        logger.error(requestURI + "————————————" + ex.getMessage());
    }
}