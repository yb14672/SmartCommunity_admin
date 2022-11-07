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
     * 返回菜单树型结构--用于左侧菜单列表
     *
     * @return
     */
    List<MenuTree> getAllMenu();

    /**
     * 获取所有菜单--用于菜单管理
     *
     * @return
     */
    List<MenuTree> getMenu();

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public SysMenu insertMenu(SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);


    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenu menu);
}

