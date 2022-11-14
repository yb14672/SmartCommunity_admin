package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.SysUserDto;
import com.zy_admin.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 勾选用户获取excel
     * @param userIds
     * @return
     */
    List<SysUser> queryUserById(@Param("list") ArrayList<Integer> userIds);

    /**
     * 所有用户获取excel
     * @return
     */
    List<SysUser> getUserLists();

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

    /**
     * 登录
     * @param sysUser
     * @return
     */
    @Select("select * from sys_user where user_name=#{userName} and password=#{password}")
    SysUser login(SysUser sysUser);
}

