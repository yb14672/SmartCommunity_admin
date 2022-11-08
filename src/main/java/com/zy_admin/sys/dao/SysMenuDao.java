package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.entity.MenuTree;
import com.zy_admin.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    /**
     * 获取导航菜单列表
     * @return
     */
    List<MenuTree> getAllMenu(@Param("userId") String userId, @Param("roleId") Long roleId);

    /**
     * 查询所有菜单
     * @param menu
     * @return
     */
    List<MenuTree> queryAllMenu(SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单
     * @return 结果
     */
    SysMenu checkMenuNameUnique(SysMenu menu);

    /**
     * 校验路由是否唯一
     *
     * @param menu 路由
     * @return 结果
     */
    SysMenu checkPathUnique(SysMenu menu);

    SysMenu checkComponentUnique(SysMenu menu);

    SysMenu checkPermsUnique(SysMenu menu);

    List<MenuTree> getMenuTree();
}

