package com.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

//因为这段代码要在不同的地方用，所以要将方法和……设置为静态的，这样在加载初始化时这些东西就已经定下来，属于类而不属于对象！
public class JwtUtil {
    private static String KEY="ikun";//加密密钥
    //生成token
    public static String genToken(Map<String,Object> claims){
        return JWT.create()
                .withClaim("claims",claims)//token的负载
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*6))
                .sign(Algorithm.HMAC256(KEY));
    }
    //接收验证token并返回业务数据
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build().verify(token).getClaim("claims")
                .asMap();
    }
}
