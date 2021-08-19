package com.cris15.xl.controller.rest;

import com.cris15.xl.controller.BaseController;
import com.cris15.xl.entity.User;
import com.cris15.xl.servie.LoginService;
import com.cris15.xl.servie.UserService;
import com.cris15.xl.util.*;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @param request
     * @return
     */
    @PassToken
    @RequestMapping("/login")
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, HttpServletRequest request){
        String md5 = MD5Utils.string2Md5(password);
        User user = loginService.checkUser(username, md5);
        if(user != null){
            session.setAttribute("user",user);
            String token = JWTUtils.generateToken(user.getId());
            String id = JWTUtils.getUserId(token);
            Map map = new HashMap(2);
            map.put("user",user);
            map.put("token",token);
            session.setAttribute("user",user);
            session.setAttribute("token",token);
            return result(map);
        }else{
            return result("用户不存在"+session.getAttribute("username"));
        }
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param phoneNumber
     * @return
     */
    @PassToken
    @RequestMapping("/registry")
    public  Object registry(@RequestParam("username") String username,@RequestParam("password")String password,@RequestParam("phoneNumber")String phoneNumber){
        if(StringUtils.isEmpty(username)){
            return result("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return result("密码不能为空");
        }
        if(StringUtils.isEmpty(phoneNumber)){
            return result("手机号不能为空");
        }
        String result = null;
        User user = loginService.selectuserByUsername(username);
        if(user != null){
            return result("用户名重复");
        }
        String md5 = MD5Utils.string2Md5(password);
        String id = UUID.randomUUID().toString().replace("-","");
        String id1 = id.replace(id.charAt(0), 'f');
        int i = loginService.addUser(id1, username, md5,phoneNumber);
        if(i == 0){
            return result("注册失败");
        }
        return result("注册成功");
    }

    /**
     * 获取短信验证码
     * @param phoneNumber
     * @return
     */
    @PassToken
    @RequestMapping("/getVerifiedCode")
    public Object getVerifiedCode(String phoneNumber){
        if(StringUtils.isEmpty(phoneNumber)){
            return result("手机号不能为空");
        }
        String verifiedCode = getRandomCode();
        String[] phoneNumbers = new String[]{phoneNumber};
        //发送短信
        SmsMultiSenderResult result = HttpUtils.sendMessage(phoneNumbers, "1", verifiedCode);
        if(result.toString().contains("\"result\":0")){
            return result(verifiedCode);
        }else{
            return result("请检查手机号是否正确,或稍后再试");
        }
    }


    /**
     * 忘记密码
     * @param username
     * @param phoneNumber
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PassToken
    @RequestMapping("/findPassword")
    public Object findPassword(String username,String phoneNumber,String oldPassword,String newPassword){
        if(StringUtils.isEmpty(newPassword)){
            return result("密码不能为空");
        }
        List<User> list = userService.getuserByNameAndPhone(username, phoneNumber,MD5Utils.string2Md5(oldPassword));
        if(list.size() == 1){
            if(list.get(0).getUsername().equals(username)){
                int i = userService.updateUserPwd(username, list.get(0).getId(), newPassword);
                if(i == 1){
                    return result("密码修改成功，请重新登陆");
                }else{
                    return result("密码更新失败");
                }
            }
        }else{
            return result("用户名与手机号不符或原密码错误，请检查");
        }
        return null;
    }
}