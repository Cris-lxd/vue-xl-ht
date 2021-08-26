package com.cris15.xl.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cris15.xl.entity.User;
import com.cris15.xl.util.JWTUtils;
import com.cris15.xl.util.PassToken;
import com.cris15.xl.util.UserLoginToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/14:04
 * @Description: 进入方法前进行处理
 **/
public class LoginInterceptor implements HandlerInterceptor {

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
                    //check if userid equals(userId in session)
                    try{
                        userId = JWTUtils.getUserId(token);
                        User user = (User) request.getSession().getAttribute("user");
                        String realuserId = user.getId();
                        if(!userId.equals(realuserId)){
                            writeResponse(false,"token校验失败",request,response);
                            return false;
                        }
                    }catch(NullPointerException ex){
                        writeResponse(false,"用户未登录",request,response);
                        return false;
                    }
                    catch (JWTDecodeException ex){
                        writeResponse(false,"身份认证失败",request,response);
                        return false;
                    }
//                    int flag = JWTUtils.verify(token);
//                    if(flag != 0){
//                        writeResponse(false,"token校验失败",request,response);
//                        return false;
//                    }
                    String token1 = (String) request.getSession().getAttribute("token");
                    if(token1 == null){
                        writeResponse(false,"该用户不存在token信息",request,response);
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
    public static void writeResponse(Boolean code,String message,HttpServletRequest request,
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