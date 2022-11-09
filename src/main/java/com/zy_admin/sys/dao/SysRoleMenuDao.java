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

    /**
     * 批量新增
     * @param id
     * @param menuIdList
     */
    void insertBatch(@Param("id") long id, @Param("menuIdList") int[] menuIdList);

    /**
     * 根据角色获取权限菜单
     * @param roleId
     * @return
     */
    List<Integer> getMenuIdsByRoleId(String roleId);

    /**
     * 单个和批量删除
     * @param idList
     * @return
     */
    int deleteByIdList(@Param("idList") List<Integer> idList);
}

