package com.zy_admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy_admin.sys.entity.SysRoleMenu;
import com.zy_admin.util.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色和菜单关联表(SysRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色新增修改时的菜单树
     * @param id
     * @return
     */
    Result getMenuIdsByRoleId(String id);

    /**
     * 单个删除和批量删除角色
     * @param idList
     * @return
     */
    int deleteByIdList(List<Integer> idList);
}

