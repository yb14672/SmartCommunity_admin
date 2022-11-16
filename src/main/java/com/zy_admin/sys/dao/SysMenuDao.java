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
     * 根据菜单id获取其所有子集
     * @param menuId 要查询的菜单id
     * @return 该菜单的子集id列表
     */
    List<Long> getChildrenById(String menuId);

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

    /**
     * 检查组件路径是否唯一
     * @param menu
     * @return
     */
    SysMenu checkComponentUnique(SysMenu menu);

    /**
     * 检查权限标识是否唯一
     * @param menu
     * @return
     */
    SysMenu checkPermsUnique(SysMenu menu);

    /**
     * 检查当前菜单是否有子类
     * @param menuId
     * @return
     */
    Integer hasChildByMenuId(Long menuId);

    /**
     * 获取所有菜单--用于做树
     * @return
     */
    List<MenuTree> getMenuTree();
}

