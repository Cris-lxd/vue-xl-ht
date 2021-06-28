package com.cris15.xl.Controller.rest;

import com.cris15.xl.Controller.BaseController;
import com.cris15.xl.util.UserLoginToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/21/10:17
 * @Description:
 **/
@RestController
@RequestMapping("/index")

public class IndexController extends BaseController {
    @UserLoginToken
    @RequestMapping("/test")
    public Object test(HttpSession session){
        System.out.println("ja");
        return result("ja"+session.getAttribute("username"));
    }




}