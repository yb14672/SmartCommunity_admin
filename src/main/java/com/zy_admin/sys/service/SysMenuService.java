package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.MenuTree;
import com.zy_admin.sys.entity.SysMenu;

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
    List<MenuTree> getAllMenu();
}

