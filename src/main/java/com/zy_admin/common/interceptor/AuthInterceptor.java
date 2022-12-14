package com.zy_admin.common.interceptor;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.zy_admin.sys.service.RedisService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import com.zy_admin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author admin
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader("token");
        if (token == null || StringUtil.isEmpty(token)) {
            token = request.getHeader("Authorization");
        }
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        //验证token的有效性
        PrintWriter writer = null;
        try {
            if (!JwtUtil.checkToken(token)) {
                writer = response.getWriter();
                writer.print(JSON.toJSONString(new Result(null, ResultTool.fail(ResultCode.USER_TOKEN_INVALID))));
                return false;
            }
            if (StringUtils.isEmpty(token)) {
                writer = response.getWriter();
                writer.print(JSON.toJSONString(new Result(null, ResultTool.fail(ResultCode.USER_LOGIN_EXPIRED))));
                return false;
            }
            Object loginStatus = redisService.get(token);
            if (ObjectUtil.isNull(loginStatus)) {
                writer = response.getWriter();
                writer.print(JSON.toJSONString(new Result(null, ResultTool.fail(ResultCode.USER_LOGIN_EXPIRED))));
                return false;
            }
            redisService.update(token);
            return true;
        } finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
