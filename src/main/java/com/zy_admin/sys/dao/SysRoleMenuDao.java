package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    void insertBatch(@Param("id") long id, @Param("menuIdList") int[] menuIdList);

    List<Integer> getMenuIdsByRoleId(String roleId);

    /**
     * 批量删除角色权限
     * @param idList 角色列表
     * @return 影响的行数
     */
    int deleteByIdList(@Param("idList")List<Integer> idList);
}

