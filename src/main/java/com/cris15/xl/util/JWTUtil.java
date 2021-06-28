package com.cris15.xl.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 14:36
 * Project: demotest
 **/
public class JWTUtil {
    // 有效时长毫秒数
    //private static final long EXPIRE_TIME = 30 * 60 * 1000;

    // token 密钥
    private static final String TOKEN_SECRET = "testsecret";

    public static String generateToken(String userId)
    {
        try
        {
            //Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<String, Object>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            String token = JWT.create()
                    .withHeader(header) // header
                    .withClaim("userId", userId)
                    //.withExpiresAt(date) // 过期时间
                    .sign(algorithm); // 签名
            return token;
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static int verify(String token)
    {
        int flag;
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWT.require(algorithm).build().verify(token);
            flag = 0;// 校验通过
        }
        catch (TokenExpiredException e)
        {
            e.printStackTrace();
            flag = 1;// token过期
        }
        catch (JWTVerificationException e)
        {
            e.printStackTrace();
            flag = 2;// 校验失败
        }
        return flag;
    }


    public static String getUserId(String token)
    {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asString();
    }


}