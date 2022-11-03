package com.zy_admin.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy_admin.sys.dao.SysUserDao;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public Result login(SysUser sysUser) {
        SysUser user = sysUserDao.login(sysUser);
        String jwtToken = "";
        if (user != null){
            jwtToken =  JwtUtils.getJwtToken(user.getUserId() + "", user.getNickName());
            if ("1".equals(user.getStatus()))
            {
                return new Result(jwtToken, ResultTool.fail(ResultCode.USER_ACCOUNT_LOCKED));
            }

            return new Result(jwtToken,ResultTool.success(ResultCode.SUCCESS));
        }
        return new Result(jwtToken,ResultTool.fail(ResultCode.USER_WRONG_ACCOUNT_OR_PASSWORD));

    }
}

