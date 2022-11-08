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

    void insertBatch(@Param("id") long id, @Param("menuIdList") List<Integer> menuIdList);
}

