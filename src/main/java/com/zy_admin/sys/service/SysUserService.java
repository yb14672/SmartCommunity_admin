package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysUser;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 登录
     * @param user
     * @return
     */
    SysUser login(SysUser user);

    SysUser getUserById(String id);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    /**
     * 重置用户密码
     *
     * @param user 用户
     * @return 结果
     */
    int resetPwd(SysUser user);
}

