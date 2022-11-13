package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.common.Pageable;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.entity.SysUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 查询头像
     * @param userId
     * @return
     */
    String getAvatarById(String userId);

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
    SysUser queryById(String id);

    /**
     * 根据用户ID获取用户所有信息
     * @param userId
     * @return
     */
    SysUserDto personal(String userId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    @Select("select * from sys_user where user_name=#{userName} and password=#{password}")
    SysUser login(SysUser sysUser);

    /**
     * 查询所有
     * @param sysUser
     * @param pageable
     * @param startTime
     * @param endTime
     * @return
     */
    List<SysUser> selectUsers(SysUser sysUser, Pageable pageable, String startTime, String endTime);

    /**
     * 统计用户总量
     * @param sysUser
     * @param startTime
     * @param endTime
     * @return
     */
    Long UserCount(SysUser sysUser, String startTime, String endTime);
}

