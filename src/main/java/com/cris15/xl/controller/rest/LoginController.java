package com.cris15.xl.controller.rest;

import com.cris15.xl.controller.BaseController;
import com.cris15.xl.entity.User;
import com.cris15.xl.servie.LoginService;
import com.cris15.xl.util.JWTUtil;
import com.cris15.xl.util.MD5Util;
import com.cris15.xl.util.PassToken;
import com.cris15.xl.util.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/10:14
 * @Description:
 **/

@RestController
@RequestMapping("/admin")
@UserLoginToken
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PassToken
    @RequestMapping("/login")
    public Object login(@RequestParam("username") String username,@RequestParam("password") String password, HttpSession session){
        String md5 = MD5Util.string2Md5(password);
        User user = loginService.checkUser(username, md5);
        if(user != null){
            session.setAttribute("user",user);
            String token = JWTUtil.generateToken(user.getId());
            String id = JWTUtil.getUserId(token);
            Map map = new HashMap(2);
            map.put("user",user);
            map.put("token",token);
            session.setAttribute("user",user);
            session.setAttribute("token",token);
            return result(map);
        }else{
            return errorResult("用户不存在"+session.getAttribute("username"));
        }
    }

    @PassToken
    @RequestMapping("/registry")
    public Object registry(@RequestParam("username") String username,@RequestParam("password")String password){
        User user = loginService.selectuserByUsername(username);
        if(user != null){
            return errorResult("用户名重复");
        }
        String md5 = MD5Util.string2Md5(password);
        String id = UUID.randomUUID().toString().replace("-","");
        String id1 = id.replace(id.charAt(0), 'f');
        int i = loginService.addUser(id1, username, md5);
        if(i == 0){
            return errorResult("注册失败");
        }
        return result("注册成功");
    }
}