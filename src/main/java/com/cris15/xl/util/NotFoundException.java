package com.cris15.xl.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Cris
 * Date: 2021/07/02
 * Time: 16:00
 * Project: demotest
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)    //将会将这个异常当作找不到的状态，则返回到404页面
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}