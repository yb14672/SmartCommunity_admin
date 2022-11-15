package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
    /**
     * 根据ID列表获取对应的用户idList
     * @param roleIdList
     * @return
     */
    List<SysUserRole> getListByIds(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 根据用户ID删除所有角色
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID批量新增对应角色列表
     * @param userId
     * @param roleId
     * @return
     */
    int insertBatchByRoleId(@Param("userId") String userId,@Param("roleId") String roleId);
}

