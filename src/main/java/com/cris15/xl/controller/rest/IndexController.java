package com.cris15.xl.controller.rest;

import com.cris15.xl.controller.BaseController;
import com.cris15.xl.util.UserLoginToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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