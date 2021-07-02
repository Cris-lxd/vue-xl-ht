package com.cris15.xl.config;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cris15.xl.entity.User;
import com.cris15.xl.servie.UserService;
import com.cris15.xl.util.JWTUtil;
import com.cris15.xl.util.PassToken;
import com.cris15.xl.util.UserLoginToken;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/14:04
 * @Description: 进入方法前进行处理
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {    //重写他的预处理方法




            String token = request.getHeader("token");
            if(!(handler instanceof HandlerMethod)){
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //检查是否有passToken注解，有的话直接跳过
        Boolean b =  method.isAnnotationPresent(PassToken.class);
            if(method.isAnnotationPresent(PassToken.class)){
                PassToken passToken = method.getAnnotation(PassToken.class);
                if(passToken.required()){
                    return true;
                }
            }
            //检查有userLogin注解，进而判断是否有token
            if(method.isAnnotationPresent(UserLoginToken.class)){
                UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
                if(userLoginToken.required()){
                    if(token == null){
                        writeResponse(false,"无token，请重新登陆",request,response);
                        return false;
                    }
                    String userId;
                    try{
                        userId = JWTUtil.getUserId(token);
                    }catch (JWTDecodeException ex){
                        writeResponse(false,"身份认证失败",request,response);
                        return false;
                    }
                    int flag = JWTUtil.verify(token);
                    if(flag != 0){
                        writeResponse(false,"token校验失败",request,response);
                        return false;
                    }
                    String token1 = (String) request.getSession().getAttribute("token");
                    if(token1 == null){
                        writeResponse(false,"用户未登录",request,response);
                        return false;
                    }
                    Boolean tokenStatus = token1.equals(token) == true ? true : false;
                    if(tokenStatus == false){
                        writeResponse(false,"token不一致,用戶信息已过期，请重新登陆",request,response);
                        return false;
                    }
                    return true;
                }

            }
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    /**
     * 返回错误信息
     * @param code
     * @param message
     * @param request
     * @param response
     * @throws IOException
     */
    public void writeResponse(Boolean code,String message,HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        request.removeAttribute("user");
        request.removeAttribute("token");
        res.put("success", code);
        res.put("code",401);
        res.put("message",message);
        out = response.getWriter();
        out.append(res.toString());
    }
}