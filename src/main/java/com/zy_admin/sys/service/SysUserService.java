package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询头像
     * @param userId
     * @return
     */
    Result getAvatarById(String userId);

    /**
     * 登录
     * @param sysUser
     * @return
     */
    Result login(SysUser sysUser);

    /**
     * 根据账号查询用户
     * @param userName
     * @return
     */
    Result queryByName(String userName);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    Result queryById(String id);

    /**
     * 根据用户ID获取用户所有信息
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
     * 根据用户ID获取其信息和对应的角色
     * @param userId
     * @return
     */
    Result authRole(Long userId);

    /**
     * 根据用户ID修改其对应的角色列表
     * @param userId
     * @param roleIdList
     * @return
     */
    Result insertAuthRole(Integer userId, List<Long> roleIdList) throws Exception;


    /**
     * 查询所有用户
     * @param pageable
     * @param sysUser
     * @return
     */
    Result selectUsers(Pageable pageable, SysUser sysUser, String startTime, String endTime);
}

