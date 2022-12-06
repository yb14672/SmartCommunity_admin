package com.zy_admin.security;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    RequestUtil requestUtil;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisService redisService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Result result = new Result();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String username =authentication.getName();
        SysUser sysUser = sysUserService.getByUsername(username);
        System.err.println(sysUser);

        String id = sysUser.getUserId()+"";
        String jwtToken = JwtUtil.getJwtToken(id, username);
        redisService.set(jwtToken,username);
        result.setData(jwtToken);
        result.setMeta(ResultTool.fail(ResultCode.USER_LOGIN_SUCCESS));

        outputStream.write(JSONUtil.toJsonStr(result).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
