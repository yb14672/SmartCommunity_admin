package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysUser;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    @Select("select * from sys_user where user_name=#{userName} and password=#{password}")
    SysUser login(SysUser sysUser);

}

