package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysUser;

/**
 * 用户信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 根据账号查询用户
     * @param userName
     * @return
     */
    SysUser queryByName(String userName);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    SysUser getUserById(String id);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);
}

