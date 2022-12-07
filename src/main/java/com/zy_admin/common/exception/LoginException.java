package com.zy_admin.common.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @author lvwei
 */
public class LoginException extends InternalAuthenticationServiceException {

    public LoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginException(String msg) {
        super(msg);
    }
}