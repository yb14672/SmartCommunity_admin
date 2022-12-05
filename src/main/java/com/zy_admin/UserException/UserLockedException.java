package com.zy_admin.UserException;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 14208
 */
public class UserLockedException extends AuthenticationException {
    public UserLockedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserLockedException(String msg) {
        super(msg);
    }
}
