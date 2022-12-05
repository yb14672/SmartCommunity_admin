package com.zy_admin.security;

import cn.hutool.json.JSONUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;

import com.zy_admin.util.ResultTool;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * @author 14208
 */
@Component
public class LoginFailurHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result result = new Result();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        if (e instanceof BadCredentialsException){
            result.setMeta(ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));
        }



        outputStream.write(JSONUtil.toJsonStr(result).getBytes());
        outputStream.flush();
        outputStream.close();

    }
}

