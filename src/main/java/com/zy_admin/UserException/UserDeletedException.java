package com.zy_admin.UserException;

import org.springframework.security.core.AuthenticationException;

public class UserDeletedException extends AuthenticationException {
    public UserDeletedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserDeletedException(String msg) {
        super(msg);
    }
}
