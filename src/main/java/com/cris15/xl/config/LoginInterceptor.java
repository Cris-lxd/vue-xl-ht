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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


            PrintWriter out = null;
            JSONObject res = new JSONObject();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

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
                        res.put("success", false);
                        res.put("message","无token，请重新登陆");
                        out = response.getWriter();
                        out.append(res.toString());
                        request.removeAttribute("user");
                        return false;
                    }
                    String userId;
                    try{
                        userId = JWTUtil.getUserId(token);
                    }catch (JWTDecodeException ex){
                        res.put("success", false);
                        res.put("message","身份认证失败");
                        res.put("code",401);
                        out = response.getWriter();
                        out.append(res.toString());
                        request.removeAttribute("user");
                        return false;
                    }
                    int flag = JWTUtil.verify(token);
                    if(flag != 0){
                        res.put("success", false);
                        res.put("message","token校验失败");
                        out = response.getWriter();
                        out.append(res.toString());
                        request.removeAttribute("user");
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
}