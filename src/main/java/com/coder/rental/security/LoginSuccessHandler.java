package com.coder.rental.security;

import com.alibaba.fastjson2.JSON;
import com.coder.rental.entity.User;
import com.coder.rental.utils.JwtUtils;
import com.coder.rental.utils.RedisUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器：生成JWT Token并返回给前端
 * 不直接返回用户完整信息，前端需要带Token请求其他接口
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();

        // 生成JWT Token
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId());
        payload.put("username", user.getUsername());
        String token = JwtUtils.createToken(payload);

        // 将Token存入Redis，过期时间与JWT一致
        String tokenKey = "token:" + token;
        redisUtils.set(tokenKey, token, JwtUtils.EXPIRE_TIME / 1000);

        // 构建返回结果：只返回token和基本信息，不返回完整用户对象
        AuthenticationResult result = new AuthenticationResult();
        result.setId(user.getId());
        result.setCode(200);
        result.setToken(token);
        result.setExpireTime(System.currentTimeMillis() + JwtUtils.EXPIRE_TIME);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSON.toJSONString(result).getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }
}
