package com.cris15.xl.controller.rest;

import com.cris15.xl.controller.BaseController;
import com.cris15.xl.entity.User;
import com.cris15.xl.servie.LoginService;
import com.cris15.xl.util.JWTUtils;
import com.cris15.xl.util.MD5Utils;
import com.cris15.xl.util.PassToken;
import com.cris15.xl.util.UserLoginToken;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
            return errorResult("用户不存在"+session.getAttribute("username"));
        }
    }

    @PassToken
    @RequestMapping("/registry")
    public  Object registry(@RequestParam("username") String username,@RequestParam("password")String password){
        User user = loginService.selectuserByUsername(username);
        if(user != null){
            return errorResult("用户名重复");
        }
        String md5 = MD5Utils.string2Md5(password);
        String id = UUID.randomUUID().toString().replace("-","");
        String id1 = id.replace(id.charAt(0), 'f');
        int i = loginService.addUser(id1, username, md5);
        if(i == 0){
            return errorResult("注册失败");
        }
        return result("注册成功");
    }

    /**
     * 获取短信验证码
     * @return
     */
    @PassToken
    @RequestMapping("/getVerifiedCode")
    public Object getVerifiedCode(String phoneNumber){
        // 短信应用 SDK AppID
        int appid = 1400561028; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "599ad28a0643e7f07136d6fbf69938ae";
        // 需要发送短信的手机号码
        String[] phoneNumbers = {phoneNumber};
        // 短信模板 ID，需要在短信应用中申请
        int templateId = 1085146; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
        // 签名
        String smsSign = "Cris个人博客"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
        String verifiedCode = getRandomCode();
        String[] params = {verifiedCode, "1"};
        SmsMultiSender msender = new SmsMultiSender(appid, appkey);
        SmsMultiSenderResult result;
        try {
            result = msender.sendWithParam("86", phoneNumbers, templateId, params, smsSign, "", "");

        } catch (HTTPException e) {
            //HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            //JSON解析错误
            e.printStackTrace();
        } catch (IOException e) {
            //网络IO错误
            e.printStackTrace();
        }
        return result(verifiedCode);
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