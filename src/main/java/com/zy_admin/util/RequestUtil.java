package com.zy_admin.util;

import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Request中数据的工具类
 * @author yb14672
 * Time:2022/11/10 - 10:41
 **/
@Component
public class RequestUtil {
    @Autowired
    private  SysUserService sysUserService;

    public  SysUser getUser(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Result result = sysUserService.queryById(id);
        SysUser user = (SysUser) result.getData();
        return user;
    }
}
