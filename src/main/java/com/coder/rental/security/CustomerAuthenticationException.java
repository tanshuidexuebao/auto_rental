package com.coder.rental.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义认证异常，用于Token验证失败场景
 */
public class CustomerAuthenticationException extends AuthenticationException {

    public CustomerAuthenticationException(String msg) {
        super(msg);
    }

    public CustomerAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
