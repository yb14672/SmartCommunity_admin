package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysMenu;
import com.zy_admin.util.Result;

/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 返回菜单树型结构--用于左侧菜单列表
     * @return
     */
    Result getAllMenu();

    /**
     * 获取所有菜单--用于菜单管理
     * @return
     */
    Result getMenu();

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public Result insertMenu(SysMenu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public Result updateMenu(SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @param type 判断是添加(0)还是修改(1)
     * @return true--是唯一，反之
     */
    public Boolean checkMenuNameUnique(int type,SysMenu menu);
}

