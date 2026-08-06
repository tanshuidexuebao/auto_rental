package com.coder.rental.security;

import com.alibaba.fastjson.JSON;
import com.coder.rental.utils.Result;
import com.coder.rental.utils.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*匿名用户无权访问*/
@Component
public class CustomerAnonymousEntryPoint implements
        AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String results= JSON.toJSONString(Result.fail()
                .setCode(ResultCode.UNAUTHORIZED).setMessage("匿名用户无权访问"));
        outputStream.write(results.getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }
}
