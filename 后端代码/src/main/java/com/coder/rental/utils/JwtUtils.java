package com.coder.rental.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    /*JWT密钥*/
   public static final String SECRET_KEY = "123456";
   /*JWT过期时间*/
    public static final Long EXPIRE_TIME = 30*60*1000L;
/*
* 生成token*/
    public static String createToken(Map<String,Object> payload){
        DateTime now= DateTime.now();
        DateTime newtime=new DateTime(now.getTime()+EXPIRE_TIME);
        /*设置签发时间*/
        payload.put(JWTPayload.ISSUED_AT,now);
        /*设置过期时间*/
        payload.put(JWTPayload.EXPIRES_AT,newtime);
        //设置生效时间，默认和签发时间一致
        payload.put(JWTPayload.NOT_BEFORE,now);

        return JWTUtil.createToken(payload,SECRET_KEY.getBytes());
    }
    /*
    * 解析token*/
    public static JWTPayload parseToken(String token){

        JWT jwt=JWTUtil.parseToken(token);
        if(!jwt.setKey(SECRET_KEY.getBytes()).verify()){
            throw new RuntimeException("token异常");
        }
        if(!jwt.validate(0)){
            throw new RuntimeException("token过期");
        }
        return jwt.getPayload();
    }

    public static void main(String[] args) {
/*        Map<String,Object> payload=new HashMap<>();
        payload.put("userId",1);
        payload.put("userName","admin");
        System.out.println(createToken(payload));*/
/*
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3ODU3MjY5MDIsInVzZXJOYW1lIjoiYWRtaW4iLCJleHAiOjE3ODU3Mjg3MDIsInVzZXJJZCI6MSwiaWF0IjoxNzg1NzI2OTAyfQ.jBUJhscFAGVsWrpdwlBY1C-hpJjmfjhwd3SXiVEX3Kw";
        JWTPayload jwtPayload = parseToken(token);
         System.out.println(jwtPayload);*/
    }
}
