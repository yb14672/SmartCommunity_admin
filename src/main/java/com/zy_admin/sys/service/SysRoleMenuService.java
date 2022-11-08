package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.util.Result;

import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    Result insertRoleMenuByRoleId(List<SysRoleMenu> sysRoleMenus, int id);

}

