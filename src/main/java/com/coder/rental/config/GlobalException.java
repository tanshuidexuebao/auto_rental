package com.coder.rental.config;

import com.coder.rental.security.CustomerAuthenticationException;
import com.coder.rental.utils.Result;
import com.coder.rental.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 处理Token验证异常（未授权）
     */
    @ExceptionHandler(value = CustomerAuthenticationException.class)
    public Result handleAuthenticationException(CustomerAuthenticationException e) {
        log.error("认证异常：{}", e.getMessage());
        return Result.fail()
                .setCode(ResultCode.UNAUTHORIZED)
                .setMessage(e.getMessage());
    }

    /**
     * 处理无权限访问异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限异常：{}", e.getMessage());
        return Result.fail()
                .setCode(ResultCode.UNAUTHORIZED_ERROR)
                .setMessage("权限不足，无权访问");
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.fail().setMessage(e.getMessage());
    }

    /**
     * 处理所有其他未知异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.fail().setMessage("服务器内部错误");
    }
}
