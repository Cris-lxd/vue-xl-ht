package com.cris15.xl.config;

import com.cris15.xl.entity.User;
import com.cris15.xl.util.CurrentUser;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description：用户注解
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 16:45
 * Project: demotest
 **/
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出第二节中放入请求中的用户信息
        //User user = (User)webRequest.getAttribute("user", NativeWebRequest.SCOPE_REQUEST);
        User user = (User)webRequest.getAttribute("user",NativeWebRequest.SCOPE_SESSION);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException("user");
    }

}