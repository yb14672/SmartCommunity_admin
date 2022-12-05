package com.zy_admin.security;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.zy_admin.common.core.Result.Result;
import com.zy_admin.common.enums.ResultCode;
import com.zy_admin.util.ResultTool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Result result = new Result();
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        result.setMeta(ResultTool.fail(ResultCode.NO_PERMISSION));

        outputStream.write(JSONUtil.toJsonStr(result).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
