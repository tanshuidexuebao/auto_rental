package com.coder.rental.security;

import com.alibaba.fastjson.JSON;
import com.coder.rental.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*用户无权访问*/
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
       response.setContentType("application/json;charset=utf-8");
       ServletOutputStream outputStream = response.getOutputStream();
       String results= JSON.toJSONString(Result.fail().setMessage("权限不足"));
       outputStream.write(results.getBytes("utf-8"));
       outputStream.flush();
       outputStream.close();
    }
}
