package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.Result;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录
     * @param sysUser
     * @return
     */
    Result login(SysUser sysUser);

}

