package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务接口
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 检查修改后的菜单是否和子集一致
     * @param menu 修改后的菜单呢数据
     * @return true--父类是子集
     */
    Boolean checkNewParentId(SysMenu menu);
    /**
     * 获取所有菜单
     * @return 所有菜单的结果集
     */
    Result getAllMenu(String id);
    /**
     * 菜单的条件搜索
     * @param menu 查询的菜单对象
     * @return
     */
    Result queryAllMenu(SysMenu menu);
    /**
     * 新增数据
     * @param menu 新增菜单对象
     * @return 新增菜单结果集
     */
    Result insertMenu(SysMenu menu);
    /**
     * 修改数据
     * @param menu 修改的菜单信息
     * @return 根据id修改菜单结果集
     */
    Result updateMenu(SysMenu menu);
    /**
     * 批量删除菜单
     * @param idList 菜单id数组
     * @return 逻辑删除菜单结果集
     */
    Result deleteByIdList(List<Long> idList);
    /**
     * 根据id删除菜单
     * @param id 菜单id
     * @return 根据id查出的菜单结果集
     */
    Result deteleById(Long id);

    /**
     * 校验菜单名称是否唯一
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkMenuNameUnique(int type, SysMenu menu);
    /**
     * 校验路由是否唯一
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkPathUnique(int type, SysMenu menu);
    /**
     * 获取菜单树
     * @return 菜单树的结果集
     */
    Result getMenuTrees();
    /**
     * 校验权限标识是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkComponentUnique(int type, SysMenu menu);

    /**
     * 校验组件路径是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkPermsUnique(int type, SysMenu menu);
}

