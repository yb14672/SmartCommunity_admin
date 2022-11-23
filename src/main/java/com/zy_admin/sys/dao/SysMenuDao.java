package com.zy_admin.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy_admin.sys.dto.MenuTree;
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
     * 得到所有菜单信息
     * @param userId 用户id
     * @param roleId 角色id
     * @return 菜单树集合
     */
    List<MenuTree> getAllMenu(@Param("userId") String userId, @Param("roleId") Long roleId);
    /**
     * 查询所有菜单
     * @param menu 菜单
     * @return 所有菜单集合
     */
    List<MenuTree> queryAllMenu(SysMenu menu);
    /**
     * 检查菜单名称是否唯一
     * @param menu 菜单
     * @return 菜单对象
     */
    SysMenu checkMenuNameUnique(SysMenu menu);
    /**
     * 校验路由是否唯一
     * @param menu 路由
     * @return 菜单对象
     */
    SysMenu checkPathUnique(SysMenu menu);

    /**
     * 检查组件路径是否唯一
     * @param menu 菜单
     * @return 菜单对象
     */
    SysMenu checkComponentUnique(SysMenu menu);

    /**
     * 检查权限标识是否唯一
     * @param menu 菜单
     * @return 菜单对象
     */
    SysMenu checkPermsUnique(SysMenu menu);
    /**
     * 检查当前菜单是否有子类
     * @param menuId 菜单id
     * @return  子类条数
     */
    Integer hasChildByMenuId(Long menuId);
    /**
     * 把菜单树
     * 获取所有菜单--用于做树
     * @return 菜单树集合
     */
    List<MenuTree> getMenuTree();
}

