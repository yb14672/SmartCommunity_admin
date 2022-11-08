package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.util.Result;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 返回菜单树型结构
     * @return
     */
    Result getAllMenu(String id);

    /**
     * 菜单管理获取列表
     * @param menu
     * @return
     */
    Result queryAllMenu(SysMenu menu);

    /**
     * 菜单的添加
     * @param menu
     * @return
     */
    Result insertMenu(SysMenu menu);

    /**
     * 菜单的修改
     * @param menu
     * @return
     */
    Result updateMenu(SysMenu menu);

    /**
     * 批量删除
     * @param idList
     * @return
     */
    Result deleteByIdList(List<Long> idList);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    Result deteleById(Serializable id);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkMenuNameUnique(int type,SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkPathUnique(int type,SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkComponentUnique(int type,SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    Boolean checkPermsUnique(int type,SysMenu menu);

    /**
     * 查询菜单树
     * @return
     */
    Result getMenuTrees();
}

