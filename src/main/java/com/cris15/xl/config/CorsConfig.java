package com.cris15.xl.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Description: 跨域配置
 * Author: Cris_liuxd
 * Date: 2021/06/25
 * Time: 15:26
 * Project: demotest
 **/
@Configuration
public class CorsConfig {
    @Value("${app.allowed-cross-orign}")
    private String resourceWebSite;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1 设置访问源地址
        corsConfiguration.setAllowedOrigins(Arrays.asList(resourceWebSite.split(",")));
        // 2 设置访问源请求头
        corsConfiguration.addAllowedHeader("*");
        //3 设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");
        //4 是否允许用户发送、处理 cookie
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //5 对接口配置跨域设置
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}