package com.coder.rental.security;

import com.alibaba.fastjson.JSON;
import com.coder.rental.utils.Result;
import com.coder.rental.utils.ResultCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*先创建返回对象：

Result.fail()

类似：

{
"success":false
}

然后：

.setCode(401)
.setMessage("用户名密码错误")

变成：

{
"success":false,
"code":401,
"message":"用户名密码错误"
}*/
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
     response.setContentType("application/json;charset=utf-8");
     ServletOutputStream outputStream = response.getOutputStream();
     int code= ResultCode.ERROR;
     String msg=null;
     if(exception instanceof LockedException){
         code=ResultCode.UNAUTHORIZED;
         msg="账户被锁定，请联系管理员";
     }else if(exception instanceof BadCredentialsException){
         code=ResultCode.UNAUTHORIZED;
         msg="用户名或密码错误";
     }else if(exception instanceof DisabledException){
         code=ResultCode.UNAUTHORIZED;
         msg="账户被禁用，请联系管理员";
     }else if(exception instanceof AccountExpiredException){
         code=ResultCode.UNAUTHORIZED_ERROR;
         msg="账户过期，请联系管理员";
     }else if(exception instanceof CredentialsExpiredException){
         code=ResultCode.UNAUTHORIZED_ERROR;
         msg="密码过期，请联系管理员";
     }else{
         code=ResultCode.ERROR;
         msg="登录失败";
     }outputStream.write(JSON.toJSONString(Result.fail().setCode(code).setMessage(msg))
                .getBytes("utf-8"));
     outputStream.flush();
     outputStream.close();

    }
}
