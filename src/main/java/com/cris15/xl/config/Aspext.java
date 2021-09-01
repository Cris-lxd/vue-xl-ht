package com.cris15.xl.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.Request;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * Author: Cris
 * Date: 2021/08/25
 * Time: 16:26
 * Project: demotest
 * Description：切面输出参数信息
 **/
@Aspect
@Component
public class Aspext {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * defined the class intercepted by the aspect
     * the parameters of annotation represent any package/any class/any method
     */
    @Pointcut("execution(* com.cris15.xl.controller.*..*(..))")
    public void log(){}

    @Before("log()")
    public void deBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String method = request.getMethod();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("-------------------NEW----before request---------------------");
        logger.info("Request {}",new RequestLog(requestURI,remoteAddr,classMethod,args));
        logger.info("-------------------------------Handler---------------------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
//        Map map = JSONObject.parseObject(JSONObject.toJSONString(result), Map.class);
        logger.info("--------------------------result-----------------------------");
        logger.info("result {}",result + "\n");
    }


    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {   //构造器
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {    //转化为字符串
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}