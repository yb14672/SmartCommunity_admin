package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.dto.userDto;
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
     * 管理员修改用户
     * @param
     * @return
     */
    Result adminUpdateUser(userDto userDto);

    /**
     * 查询头像
     *
     * @param userId
     * @return
     */
    Result getAvatarById(String userId);

    /**
     * 登录
     *
     * @param sysUser
     * @return
     */
    Result login(SysUser sysUser);

    /**
     * 根据账号查询用户
     *
     * @param userName
     * @return
     */
    Result queryByName(String userName);

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    Result queryById(String id);

    /**
     * 根据用户ID获取用户所有信息
     *
     * @param userId
     * @return
     */
    Result personal(String userId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    Result updateUser(SysUser user);

    /**
     * 修改密码
     *
     * @param user 用户信息
     * @return 结果
     */
    Result resetPwd(SysUser user);

    /**
     * 新增用户
     *
     * @param sysUserDto
     * @return
     */
    Result insertUser(userDto sysUserDto);

    /**
     * 昵称查重
     *
     * @param type
     * @param sysUserDto
     * @return
     */
    Boolean checkNiceName(int type, userDto sysUserDto);

    /**
     * 电话查重
     * @param type
     * @param sysUserDto
     * @return
     */
    Boolean checkPhone(int type, userDto sysUserDto);

    /**
     * 邮箱查重
     * @param type
     * @param sysUserDto
     * @return
     */
    Boolean checkEmail(int type, userDto sysUserDto);
}

